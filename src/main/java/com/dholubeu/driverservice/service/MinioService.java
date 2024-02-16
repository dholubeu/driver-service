package com.dholubeu.driverservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface MinioService {

    void uploadDocuments(Long driverId, MultipartFile file);

}