package com.taguz91.api_serena.api.aws;

public enum BucketName {
    SYNC_FILES("sync-files-test"),
    PHOTOS_FILES("photos-test");

    private final String name;

    private BucketName(String name) {
        this.name = name;
    }

    public String getBucketName() {
        return this.name;
    }
}
