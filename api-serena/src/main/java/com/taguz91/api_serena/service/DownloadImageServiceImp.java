package com.taguz91.api_serena.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.taguz91.api_serena.api.aws.BucketName;
import com.taguz91.api_serena.service.contracts.DownloadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DownloadImageServiceImp implements DownloadImageService {
    @Autowired
    private AmazonS3 amazonS3;

    public byte[] download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(
                BucketName.SYNC_FILES.getBucketName(),
                key
        );

        S3Object s3Object = amazonS3.getObject(getObjectRequest);
        S3ObjectInputStream objectInputStream = s3Object.getObjectContent();

        return IOUtils.toByteArray(objectInputStream);
    }
}
