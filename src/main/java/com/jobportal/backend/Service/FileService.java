package com.jobportal.backend.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    // Upload file, trả về tên file đã lưu
    String uploadImage(String path, MultipartFile file) throws IOException;

    // Lấy file từ server (để download hoặc hiển thị)
    InputStream getResource(String path, String fileName) throws FileNotFoundException;

    String uploadImageCloud(MultipartFile file) throws IOException;
    String uploadCv(MultipartFile file) throws IOException;
}