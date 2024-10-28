package com.taguz91.api_serena.service;

import com.taguz91.api_serena.api.aws.BucketName;
import com.taguz91.api_serena.models.Register;
import com.taguz91.api_serena.models.RegisterStudent;
import com.taguz91.api_serena.models.Student;
import com.taguz91.api_serena.models.StudentEmotion;
import com.taguz91.api_serena.repository.RegisterRepository;
import com.taguz91.api_serena.repository.RegisterStudentRepository;
import com.taguz91.api_serena.repository.StudentEmotionRepository;
import com.taguz91.api_serena.repository.StudentRepository;
import com.taguz91.api_serena.service.contracts.CreateStudentRegister;
import com.taguz91.api_serena.service.contracts.FileStoreService;
import com.taguz91.api_serena.utils.NanoCombCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

@Service
public class CreateStudentRegisterImp implements CreateStudentRegister {
    // to test now whe can select a random emotion
    // this is going to  be used from aws lambda
    //HAPPY | SAD | ANGRY | CONFUSED | DISGUSTED | SURPRISED | CALM | UNKNOWN | FEAR
    final List<String> emotions = List.of("HAPPY", "SAD", "ANGRY", "CONFUSED", "DISGUSTED", "SURPRISED", "CALM", "UNKNOWN", "FEAR");

    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private RegisterStudentRepository registerStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RegisterRepository registerRepository;
    @Autowired
    private StudentEmotionRepository studentEmotionRepository;

    public RegisterStudent create(MultipartFile photo, String idRegister, Student student) {
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

        registerStudent.setStatus(register.getStatus());
        registerStudent.setEmotion(getEmotion());
        registerStudent.setRegister(register);

        registerStudent.setStudent(
                student == null ? this.getStudent(register, s3key) : student
        );

        RegisterStudent newRegisterStudent = registerStudentRepository.save(registerStudent);

        if (!register.getStatus().equals("inscription")) {
            saveEmotionDetails(newRegisterStudent, newRegisterStudent.getEmotion());
        }

        return newRegisterStudent;
    }

    private String getEmotion() {
        int rnd = new Random().nextInt(emotions.size());

        return emotions.get(rnd);
    }

    private void saveEmotionDetails(
            RegisterStudent registerStudent,
            String emotion
    ) {
        Stream<StudentEmotion> studentEmotions =  emotions.stream().map((e) -> {
            StudentEmotion studentEmotion = new StudentEmotion();
            studentEmotion.setId((new NanoCombCreator()).create().toString());
            studentEmotion.setRegisterStudent(registerStudent);
            studentEmotion.setEmotion(emotion);

            int lower = new Random().nextInt(100);

            if (!e.equals(emotion)) {
                int rnd = new Random().nextInt(lower > 50 ? 25 : 12);
                double percentage = ((double) rnd) / 100;
                studentEmotion.setPercentage(percentage);
            } else {
                int high = lower > 50 ? 96 : 91;
                int low = lower > 50 ? 84 : 76;
                int rnd = new Random().nextInt(high - low) + low;
                double percentage = ((double) rnd) / 100;
                studentEmotion.setPercentage(percentage);
            }

            return studentEmotion;
        });

        studentEmotionRepository.saveAll(studentEmotions.toList());
    }

    private Student getStudent(Register register, String s3KeyTarget) {
        // find student using the current photo and the list of students
        Register registerInscription = registerRepository.findInscriptionByClassroom(
                register.getClassroom().getId()
        ).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "No existe el registro"));

        List<RegisterStudent> registerStudents = registerStudentRepository.findByIdRegister(
                registerInscription.getId()
        );

        List<Map<String, String>> studentsPhotos = registerStudents.stream().map((registerStudent) -> {
            Map<String, String> option = new HashMap<>();
            option.put("photo", registerStudent.getPhoto());
            option.put("idStudent", registerStudent.getStudent().getId());

            return option;
        }).toList();

        Map<String, Object> request = new HashMap<>();
        request.put("s3Target", s3KeyTarget);
        request.put("photos", studentsPhotos);

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

    public RegisterStudent duplicate(String idStudent, String idRegister) {
        Student student = studentRepository.findById(idStudent).orElseThrow(() -> new HttpClientErrorException(
                HttpStatus.NOT_FOUND,
                "No existe el estudiante"
        ));

        RegisterStudent registerStudent = registerStudentRepository.findByLastByIdStudent(idStudent)
                .orElseThrow(() -> new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "No existe el registro del estudiante"
                ));

        RegisterStudent duplicateRegisterStudent = new RegisterStudent();
        duplicateRegisterStudent.setId((new NanoCombCreator()).create().toString());

        duplicateRegisterStudent.setRegister((new Register()).setId(idRegister));

        duplicateRegisterStudent.setStudent(student);
        duplicateRegisterStudent.setEmotion(registerStudent.getEmotion());
        duplicateRegisterStudent.setPhoto(registerStudent.getPhoto());
        duplicateRegisterStudent.setStatus(registerStudent.getStatus());

        return registerStudentRepository.save(registerStudent);
    }
}
