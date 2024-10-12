package com.taguz91.api_serena.service.contracts;

import java.io.IOException;

public interface DownloadImageService {
    public byte[] download(String key) throws IOException;
}
