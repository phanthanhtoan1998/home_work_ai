package com.example.service;

import com.example.dto.response.QuestionReadFileResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {

    /**
     * Đọc file Excel câu hỏi và trả về danh sách câu hỏi đã parse.
     * Logic cần triển khai:
     * 1. Dùng Apache POI để đọc file.
     * 2. Validate format file.
     * 3. Convert từng dòng thành Object DTO.
     */
    public List<QuestionReadFileResponseDto> readFileExcel(MultipartFile file) {
        // Logic đọc file giả lập
        return Collections.emptyList();
    }
}
