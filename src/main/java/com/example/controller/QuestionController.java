package com.example.controller;

import com.example.dto.common.ResponseConfig;
import com.example.dto.common.ResponseDto;
import com.example.dto.response.QuestionReadFileResponseDto;
import com.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller quản lý câu hỏi.
 * Feature chính: Import câu hỏi từ file Excel.
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * API Đọc file Excel câu hỏi.
     * Endpoint: POST /question/read-file-question
     * 
     * @param file: File Excel upload lên (.xlsx)
     * @return Danh sách câu hỏi đã đọc được từ file để Frontend hiển thị preview.
     */
    /**
     * API Đọc file Excel câu hỏi.
     * Endpoint: POST /question/read-file-question
     * Cách hoạt động:
     * 1. Nhận file Excel (.xlsx) từ client qua FormData.
     * 2. Gọi logic Service để parse từng dòng trong file Excel.
     * 3. Trả về danh sách câu hỏi đã đọc được để người dùng xem trước (preview).
     */
    @PostMapping("/read-file-question")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDto<List<QuestionReadFileResponseDto>>> readFileQuestion(
            @RequestPart("file") MultipartFile file) {
        // Gọi Service đọc file và chuyển đổi thành danh sách DTO
        List<QuestionReadFileResponseDto> response = questionService.readFileExcel(file);

        // Trả về danh sách câu hỏi xem trước
        return ResponseConfig.success(response);
    }
}
