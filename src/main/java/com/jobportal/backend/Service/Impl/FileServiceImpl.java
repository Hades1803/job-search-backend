package com.jobportal.backend.Service.Impl;


import com.jobportal.backend.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();

        // Tạo tên file mới tránh trùng
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId + originalFileName.substring(originalFileName.lastIndexOf('.'));

        // Tạo thư mục nếu chưa có
        Path folder = Paths.get(path);
        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        // Lưu file
        Path filePath = folder.resolve(fileName);
        Files.write(filePath, file.getBytes());

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        Path filePath = Paths.get(path, fileName);
        return new FileInputStream(filePath.toFile());
    }
}