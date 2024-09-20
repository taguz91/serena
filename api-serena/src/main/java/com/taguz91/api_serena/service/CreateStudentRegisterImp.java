package com.taguz91.api_serena.service;

import com.taguz91.api_serena.api.aws.BucketName;
import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.repository.RegisterRepository;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.StudentRepository;
import com.taguz91.api_serena.service.contracts.CreateStudentRegister;
import com.taguz91.api_serena.service.contracts.FileStoreService;
import com.taguz91.api_serena.utils.NanoCombCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;

@Service
public class CreateStudentRegisterImp implements CreateStudentRegister {

    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private RegisterStudentRepository registerStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RegisterRepository registerRepository;

    public RegisterStudent create(MultipartFile photo, String idRegister) {
        RegisterStudent registerStudent = new RegisterStudent();
        registerStudent.setId((new NanoCombCreator()).create().toString());

        Register register = registerRepository.findById(idRegister)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe el registro"));

        fileStoreService.setPrefixFolder(
                register.getClassroom().getAcademicPeriod().getId()
                + "/" + register.getClassroom().getId() + "/"
        );
        String s3key = fileStoreService.save(photo, BucketName.SYNC_FILES);

        registerStudent.setPhoto(s3key);

        registerStudent.setEmotion(getEmotion());
        registerStudent.setRegister(register);

        registerStudent.setStudent(
                this.getStudent()
        );

        return registerStudentRepository.save(registerStudent);
    }

    private String getEmotion() {
        // to test now whe can select a random emotion
        // this is going to  be used from aws lambda
        //HAPPY | SAD | ANGRY | CONFUSED | DISGUSTED | SURPRISED | CALM | UNKNOWN | FEAR
        List<String> emotions = List.of("HAPPY", "SAD", "ANGRY", "CONFUSED", "DISGUSTED", "SURPRISED", "CALM", "UNKNOWN", "FEAR");
        int rnd = new Random().nextInt(emotions.size());

        return emotions.get(rnd);
    }

    private Student getStudent() {
        return this.createStudent();
    }

    private Student createStudent() {
        Student student = new Student();
        student.setId((new NanoCombCreator()).create().toString());
        student.setIdentification("");
        student.setName("");
        student.setGender("");

        return studentRepository.save(student);
    }
}