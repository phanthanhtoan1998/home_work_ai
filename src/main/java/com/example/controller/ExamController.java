package com.example.controller;

import com.example.entity.Exam;
import com.example.entity.Question;
import com.example.service.ExamService;
import com.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
public class ExamController {
    @Autowired
    private ExamService examService;

    @Autowired
    private QuestionService questionService;

    // API Lấy danh sách tất cả đề thi
    // GET /api/tests
    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    // API Lấy chi tiết một đề thi theo ID
    // GET /api/tests/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable Long id) {
        return examService.getExamById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // API Tạo đề thi mới
    // POST /api/tests
    @PostMapping
    public ResponseEntity<Exam> createExam(@RequestBody Exam exam) {
        return ResponseEntity.ok(examService.createExam(exam));
    }

    // API Xóa đề thi
    // DELETE /api/tests/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.ok().build();
    }

    // API Thêm câu hỏi vào đề thi
    // POST /api/tests/{id}/questions
    @PostMapping("/{id}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable Long id, @RequestBody Question question) {
        return examService.getExamById(id).map(exam -> {
            question.setExam(exam);
            return ResponseEntity.ok(questionService.createQuestion(question));
        }).orElse(ResponseEntity.notFound().build());
    }

    // API Lấy danh sách câu hỏi của một đề thi
    // GET /api/tests/{id}/questions
    @GetMapping("/{id}/questions")
    public ResponseEntity<java.util.Set<Question>> getQuestions(@PathVariable Long id) {
        return examService.getExamById(id)
                .map(exam -> ResponseEntity.ok(exam.getQuestions()))
                .orElse(ResponseEntity.notFound().build());
    }
}
