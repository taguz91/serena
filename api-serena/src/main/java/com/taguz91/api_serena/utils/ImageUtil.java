package com.taguz91.api_serena.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

public class ImageUtil {

    public static MultipartFile base64ToMultipartFile(String name, String base64) {
        int semicolon = base64.indexOf(";");
        String base64WithoutHeader = base64.substring(semicolon + 8);

        byte[] bytes = Base64.getDecoder().decode(base64WithoutHeader);

        String extension = ".jpg";
        String filename = name + extension;
        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return "application/jpg";
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return bytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try(FileOutputStream fos = new FileOutputStream(dest)) {
                    fos.write(bytes);
                }
            }
        };
    }
}
