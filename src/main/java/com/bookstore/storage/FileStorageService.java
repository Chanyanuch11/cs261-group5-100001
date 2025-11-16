package com.bookstore.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadPath) throws IOException {
        this.uploadDir = Paths.get(uploadPath).toAbsolutePath().normalize();
        Files.createDirectories(this.uploadDir);
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) throw new IOException("File is empty");

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = this.uploadDir.resolve(fileName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}