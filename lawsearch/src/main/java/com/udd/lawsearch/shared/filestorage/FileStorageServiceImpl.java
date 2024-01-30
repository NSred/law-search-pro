package com.udd.lawsearch.shared.filestorage;

import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService{
    private final MinioClient minioClient;
    @Value("${bucket-name}")
    private String bucketName;

    public void uploadFile(MultipartFile file) {

        try {
            // Check if the bucket exists
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket if not exist
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // Upload the file
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename()) // Using the original file name
                            .stream(file.getInputStream(), file.getSize(), -1) // file.getSize() returns the size of the file
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file to MinIO", e);
        }
    }

    public InputStream getFileFromMinio(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }
}
