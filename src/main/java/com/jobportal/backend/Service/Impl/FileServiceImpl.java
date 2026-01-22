package com.jobportal.backend.Service.Impl;


import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.jobportal.backend.Service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Cloudinary cloudinary;

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

    public String uploadImageCloud(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder", "jobportal/images"));
        return uploadResult.get("secure_url").toString();
    }


    @Override
    public String uploadCv(MultipartFile file) throws IOException {

        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "jobportal/cv"
        );

        Map uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), options);

        // ⚠️ LƯU public_id, KHÔNG LƯU secure_url
        return uploadResult.get("public_id").toString();
    }

    @Override
    public String generateCvPreviewUrl(String publicId) {

        return cloudinary.url()
                .resourceType("image")   // render PDF → image
                .format("jpg")
                .transformation(new Transformation()
                        .page(1)
                        .width(800)
                        .crop("fit")
                )
                .secure(true)
                .generate(publicId);
    }


}