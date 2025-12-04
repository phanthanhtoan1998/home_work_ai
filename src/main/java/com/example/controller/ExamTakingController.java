package com.example.controller;

import com.example.entity.ExamResult;
import com.example.service.ExamResultService;
import com.example.service.ExamService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamTakingController {

    @Autowired
    private ExamResultService examResultService;

    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    // API Bắt đầu làm bài thi
    // POST /api/tests/{id}/start
    @PostMapping("/tests/{id}/start")
    public ResponseEntity<?> startExam(@PathVariable Long id, Principal principal) {
        // Logic bắt đầu bài thi (ví dụ: lưu thời gian bắt đầu)
        // Hiện tại chỉ trả về thông báo thành công
        return ResponseEntity.ok("Bắt đầu làm bài thi thành công!");
    }

    // API Nộp bài thi
    // POST /api/submit
    // Body: { "exam": {"id": 1}, "score": 85, ... }
    @PostMapping("/submit")
    public ResponseEntity<?> submitExam(@RequestBody ExamResult result, Principal principal) {
        // Logic tính điểm sẽ được thực hiện ở đây (hoặc ở Frontend gửi lên)
        // Lưu thời gian nộp bài
        result.setEndTime(LocalDateTime.now());

        // Gán kết quả cho user đang đăng nhập
        if (principal != null) {
            result.setUser(userService.findByUsername(principal.getName()));
        }
        return ResponseEntity.ok(examResultService.saveResult(result));
    }

    // API Xem lịch sử kết quả thi của bản thân
    // GET /api/results
    @GetMapping("/results")
    public List<ExamResult> getMyResults(Principal principal) {
        if (principal == null)
            return List.of();
        Long userId = userService.findByUsername(principal.getName()).getId();
        return examResultService.getResultsByUserId(userId);
    }
}
