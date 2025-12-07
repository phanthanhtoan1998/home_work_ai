package com.example.controller;

import com.example.dto.common.ResponseConfig;
import com.example.dto.common.ResponseDto;
import com.example.dto.request.CreateExamRequestDTO;
import com.example.dto.request.ExamRequestFilterDTO;
import com.example.dto.response.ExamResponseDto;
import com.example.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * API Tìm kiếm và Lọc đề thi.
     * Endpoint: POST /exam/search-filter
     * Cách hoạt động:
     * 1. Nhận vào JSON chứa các tiêu chí lọc (tên đề thi, tags, v.v.).
     * 2. Gọi Service để query database dựa trên các tiêu chí này.
     * 3. Trả về danh sách đề thi thỏa mãn điều kiện.
     * Lưu ý: API này Public, không yêu cầu đăng nhập.
     */
    @PostMapping("/search-filter")
    public ResponseEntity<ResponseDto<List<ExamResponseDto>>> searchExamFilter(
            @RequestBody ExamRequestFilterDTO examFilterDTO) {
        // Gọi xuống Service để lấy danh sách đề thi theo bộ lọc (tên, tags...)
        List<ExamResponseDto> result = examService.getListExamFilter(examFilterDTO);

        // Đóng gói kết quả vào đối tượng ResponseDto chuẩn và trả về HTTP 200 OK
        return ResponseConfig.success(result);
    }

    /**
     * API Tạo đề thi mới.
     * Endpoint: POST /exam/create
     * Cách hoạt động:
     * 1. Kiểm tra người dùng đã đăng nhập chưa (@PreAuthorize).
     * 2. Nhận dữ liệu đề thi (tên, file câu hỏi, tags) từ RequestBody.
     * 3. Gọi Service để lưu đề thi vào DB.
     * 4. Nếu có file câu hỏi đi kèm, Service sẽ xử lý luồng import file.
     */
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDto<Void>> createExam(@RequestBody CreateExamRequestDTO dto) {
        // Gọi Service để thực hiện logic tạo mới đề thi từ DTO
        examService.createExam(dto);

        // Trả về phản hồi thành công (data = null vì tạo mới không cần trả về dữ liệu)
        return ResponseConfig.success(null);
    }

    /**
     * API Lấy chi tiết đề thi.
     * Endpoint: GET /exam/detail?examId={id}
     * Cách hoạt động:
     * 1. Nhận ID đề thi từ Query Param.
     * 2. (Optional) Nhận versionCache để hỗ trợ cơ chế cache client-side.
     * 3. Trả về toàn bộ thông tin đề thi, bao gồm cả danh sách các lần thi
     * (records) nếu có.
     */
    @GetMapping("/detail")
    public ResponseEntity<ResponseDto<ExamResponseDto>> getExamDetail(
            @RequestParam("examId") Long examId,
            @RequestParam(value = "versionCache", required = false) Long versionCache) {
        // Gọi Service để lấy thông tin chi tiết của đề thi theo ID
        ExamResponseDto detail = examService.getExamDetail(examId, versionCache);

        // Trả về kết quả cho client
        return ResponseConfig.success(detail);
    }

    /**
     * API Cập nhật đề thi.
     * Endpoint: PUT /exam/update/{examId}
     * Cách hoạt động:
     * 1. Nhận ID đề thi cần sửa từ Path Variable.
     * 2. Nhận thông tin mới từ RequestBody.
     * 3. Cập nhật vào DB.
     */
    @PutMapping("/update/{examId}")
    public ResponseEntity<ResponseDto<Void>> updateExam(@RequestBody CreateExamRequestDTO dto,
            @PathVariable Long examId) {
        // Gọi Service để cập nhật thông tin đề thi dựa trên ID và dữ liệu mới
        examService.updateExam(dto, examId);

        // Trả về phản hồi thành công
        return ResponseConfig.success(null);
    }
}
