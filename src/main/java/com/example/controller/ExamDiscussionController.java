package com.example.controller;

import com.example.dto.common.ResponseConfig;
import com.example.dto.common.ResponseDto;
import com.example.dto.request.CreateExamDiscussionRequestDTO;
import com.example.dto.request.ExamDiscussionRequestFilterDTO;
import com.example.dto.request.UpdateExamDiscussionRequestDTO;
import com.example.dto.response.ExamDiscussionResponseDto;
import com.example.service.ExamDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller quản lý các thảo luận (Bình luận/Hỏi đáp) trong bài thi.
 * Tương đương với ExamDiscussionController trong exam_be.
 */
@RestController
@RequestMapping("/exam-discussions")
public class ExamDiscussionController {

    @Autowired
    private ExamDiscussionService examDiscussionService;

    /**
     * API Tạo thảo luận mới.
     * Endpoint: POST /exam-discussions/create
     * Cách hoạt động:
     * 1. Nhận thông tin thảo luận (nội dung, người tạo, loại thảo luận).
     * 2. Gọi service để lưu và trả về kết quả.
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<ExamDiscussionResponseDto>> createDiscussion(
            @RequestBody CreateExamDiscussionRequestDTO requestDTO) {
        // Thực hiện logic nghiệp vụ tạo thảo luận thông qua service
        ExamDiscussionResponseDto response = examDiscussionService.createDiscussion(requestDTO);

        // Trả về đối tượng thảo luận vừa được tạo thành công
        return ResponseConfig.success(response);
    }

    /**
     * API Cập nhật thảo luận.
     * Endpoint: PUT /exam-discussions/update/{id}
     * Cách hoạt động:
     * 1. Lấy ID thảo luận từ URL path variable.
     * 2. Lấy dữ liệu cập nhật từ request body.
     * 3. Gọi service để tìm và cập nhật thảo luận.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto<ExamDiscussionResponseDto>> updateDiscussion(
            @PathVariable Long id,
            @RequestBody UpdateExamDiscussionRequestDTO requestDTO) {
        // Gán ID từ path variable vào DTO để đảm bảo tính nhất quán
        requestDTO.setId(id);

        // Gọi service xử lý cập nhật
        ExamDiscussionResponseDto response = examDiscussionService.updateDiscussion(requestDTO);

        // Trả về kết quả sau khi cập nhật
        return ResponseConfig.success(response);
    }

    /**
     * API Xóa thảo luận.
     * Endpoint: DELETE /exam-discussions/delete/{id}
     * Cách hoạt động:
     * 1. Lấy ID thảo luận cần xóa.
     * 2. Gọi service để thực hiện xóa (soft delete hoặc hard delete).
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteDiscussion(@PathVariable Long id) {
        // Gọi service để xóa thảo luận theo ID
        examDiscussionService.deleteDiscussion(id);

        // Trả về thành công không kèm dữ liệu
        return ResponseConfig.success(null);
    }

    /**
     * API Lấy chi tiết một thảo luận.
     * Endpoint: GET /exam-discussions/detail?discussionId={id}
     * Cách hoạt động:
     * 1. Nhận ID từ query param.
     * 2. Truy xuất thông tin chi tiết của thảo luận đó.
     */
    @GetMapping("/detail")
    public ResponseEntity<ResponseDto<ExamDiscussionResponseDto>> getDiscussionById(
            @RequestParam("discussionId") Long id) {
        // Lấy thông tin chi tiết thảo luận từ service
        ExamDiscussionResponseDto response = examDiscussionService.getDiscussionById(id);

        // Trả về dữ liệu chi tiết
        return ResponseConfig.success(response);
    }

    /**
     * API Lấy danh sách thảo luận theo Exam ID.
     * Endpoint: GET /exam-discussions/exam/{examId}
     * Cách hoạt động:
     * 1. Lấy danh sách các comment gốc (level 0) của bài thi.
     * 2. Thường dùng để render phần bình luận trong trang chi tiết bài thi.
     */
    @GetMapping("/exam/{examId}")
    public ResponseEntity<ResponseDto<List<ExamDiscussionResponseDto>>> getDiscussionsByExamId(
            @PathVariable Long examId) {
        // Lấy danh sách thảo luận dựa trên ID bài thi
        List<ExamDiscussionResponseDto> response = examDiscussionService.getDiscussionsByExamId(examId);
        return ResponseConfig.success(response);
    }

    /**
     * API Lấy các trả lời (Replies) của một comment cha.
     * Endpoint: GET /exam-discussions/parent/{parentId}
     * Cách hoạt động:
     * 1. Lấy danh sách các comment con có parentId trùng khớp.
     */
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<ResponseDto<List<ExamDiscussionResponseDto>>> getDiscussionsByParentId(
            @PathVariable Long parentId) {
        // Truy vấn các thảo luận con của một thảo luận cha
        List<ExamDiscussionResponseDto> response = examDiscussionService.getDiscussionsByParentId(parentId);
        return ResponseConfig.success(response);
    }

    /**
     * API Tìm kiếm/Lọc thảo luận (Admin filter).
     * Endpoint: POST /exam-discussions/search-filter
     * Cách hoạt động:
     * 1. Nhận bộ lọc (examId, userId, nội dung tìm kiếm...).
     * 2. Trả về danh sách thảo luận khớp điều kiện.
     */
    @PostMapping("/search-filter")
    public ResponseEntity<ResponseDto<List<ExamDiscussionResponseDto>>> getDiscussionsByFilter(
            @RequestBody ExamDiscussionRequestFilterDTO filterDTO) {
        // Thực hiện tìm kiếm nâng cao thông qua service
        List<ExamDiscussionResponseDto> response = examDiscussionService.getDiscussionsByFilter(filterDTO);
        return ResponseConfig.success(response);
    }

    /**
     * API Đếm số lượng thảo luận của một bài thi.
     * Endpoint: GET /exam-discussions/exam/{examId}/count
     */
    @GetMapping("/exam/{examId}/count")
    public ResponseEntity<ResponseDto<Long>> getDiscussionCountByExamId(@PathVariable Long examId) {
        // Đếm tổng số thảo luận thuộc về bài thi này
        Long count = examDiscussionService.getDiscussionCountByExamId(examId);
        return ResponseConfig.success(count);
    }

    /**
     * API Đếm số lượng trả lời của một comment.
     * Endpoint: GET /exam-discussions/parent/{parentId}/count
     */
    @GetMapping("/parent/{parentId}/count")
    public ResponseEntity<ResponseDto<Long>> getDiscussionCountByParentId(@PathVariable Long parentId) {
        // Đếm số lượng phản hồi con cho một bình luận cha
        Long count = examDiscussionService.getDiscussionCountByParentId(parentId);
        return ResponseConfig.success(count);
    }
}
