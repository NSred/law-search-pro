package com.udd.lawsearch.shared.filestorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface FileStorageService {
    void uploadFile(MultipartFile file);
    InputStream getFileFromMinio(String bucketName, String objectName) throws Exception;
}
