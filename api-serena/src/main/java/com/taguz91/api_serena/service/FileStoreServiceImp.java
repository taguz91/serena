package com.taguz91.api_serena.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.taguz91.api_serena.api.aws.BucketName;
import com.taguz91.api_serena.service.contracts.FileStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStoreServiceImp implements FileStoreService {

    @Autowired
    private AmazonS3 amazonS3;

    private  String folders = "";

    public void setPrefixFolder(String folders) {
        this.folders = folders;
    }

    public String save(MultipartFile file, BucketName bucket) {
        if (file.isEmpty()) {
            throw new IllegalStateException("El archivo esta vacio, sube uno con datos");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s", bucket.getBucketName());
        String fileName = String.format("%s", file.getOriginalFilename());

        try {
            return uploadToS3(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("No pudimos subir el recurso");
        }
    }

    public String uploadToS3(
            String path,
            String filename,
            Optional<Map<String, String>> optionalMetadata,
            InputStream inputStream
    ) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            String s3Key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + '-' + filename;
            // use the prefix folder
            s3Key = this.folders + s3Key;
            amazonS3.putObject(new PutObjectRequest(path, s3Key, inputStream, objectMetadata));
            // always set the prefix folder to empty after save the
            this.setPrefixFolder("");
            return s3Key;
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload a file. " + e.getMessage());
        }
    }
}
