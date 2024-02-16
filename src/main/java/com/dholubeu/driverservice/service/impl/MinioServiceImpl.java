package com.dholubeu.driverservice.service.impl;

import com.dholubeu.driverservice.service.MinioService;
import com.dholubeu.driverservice.service.property.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    @Override
    @SneakyThrows
    public void uploadDocuments (Long driverId, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFilename = driverId + fileExtension;
        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        String contentType = "application/octet-stream";
        if (fileExtension.equalsIgnoreCase(".pdf")) {
            contentType = "application/pdf";
        } else if (fileExtension.equalsIgnoreCase(".doc")
                || fileExtension.equalsIgnoreCase(".docx")) {
            contentType = "application/msword";
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(newFilename)
                .stream(inputStream, file.getSize(), -1)
                .contentType(contentType)
                .build());
    }

}