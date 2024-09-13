package com.taguz91.api_serena.service.contracts;

import com.taguz91.api_serena.api.aws.BucketName;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {

    public String save(MultipartFile file, BucketName bucket);

    public String uploadToS3(
            String path,
            String filename,
            Optional<Map<String, String>> optionalMetada,
            InputStream inputStream
    );
}
