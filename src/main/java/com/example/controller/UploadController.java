package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    // API Upload ảnh
    // POST /api/upload/image
    // Form-data: key="file", value=[file ảnh]
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        // Logic lưu file ảnh vào server hoặc cloud (S3, Cloudinary)
        // Hiện tại chỉ trả về tên file
        return ResponseEntity.ok("Đã upload ảnh: " + file.getOriginalFilename());
    }

    // API Upload âm thanh
    // POST /api/upload/audio
    // Form-data: key="file", value=[file âm thanh]
    @PostMapping("/audio")
    public ResponseEntity<?> uploadAudio(@RequestParam("file") MultipartFile file) {
        // Logic lưu file âm thanh
        return ResponseEntity.ok("Đã upload audio: " + file.getOriginalFilename());
    }
}
