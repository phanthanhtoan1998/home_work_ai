package com.example.service;

import com.example.dto.response.RecordDomainResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class RecordService {

    /**
     * Lấy danh sách chi tiết các bài thi dựa trên ID.
     * Thường dùng để so sánh kết quả hoặc hiển thị history.
     */
    public List<RecordDomainResponse> getListDetail(List<Long> ids) {
        // Logic truy vấn DB ExamResult
        return Collections.emptyList();
    }
}
