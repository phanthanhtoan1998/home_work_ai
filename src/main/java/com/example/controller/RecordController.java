package com.example.controller;

import com.example.dto.common.ResponseConfig;
import com.example.dto.common.ResponseDto;
import com.example.dto.response.RecordDomainResponse;
import com.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý hồ sơ/lịch sử thi (Record).
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * API Lấy chi tiết nhiều bài thi cùng lúc.
     * Endpoint: GET /record/list-detail
     * 
     * @param ids: Danh sách ID bài thi cần lấy (VD: ?ids=1,2,3)
     */
    /**
     * API Lấy chi tiết nhiều bài thi cùng lúc.
     * Endpoint: GET /record/list-detail
     * Cách hoạt động:
     * 1. Nhận danh sách ID bài thi từ query param (VD: ?ids=1,2,3).
     * 2. Gọi Service để tìm kiếm và tổng hợp thông tin chi tiết cho từng ID.
     * 3. Trả về danh sách kết quả.
     */
    @GetMapping("/list-detail")
    public ResponseEntity<ResponseDto<List<RecordDomainResponse>>> getListDetail(@RequestParam("ids") List<Long> ids) {
        // Gọi Service lấy danh sách chi tiết dựa trên List ID
        List<RecordDomainResponse> response = recordService.getListDetail(ids);

        // Trả về kết quả
        return ResponseConfig.success(response);
    }
}
