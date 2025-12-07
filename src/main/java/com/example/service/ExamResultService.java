package com.example.service;

import com.example.entity.ExamResult;
import com.example.repository.ExamResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamResultService {
    @Autowired
    private ExamResultRepository examResultRepository;

    @Autowired
    private com.example.service.UserService userService;

    @Autowired
    private com.example.service.ExamService examService;

    public ExamResult startExam(Long examId, String username) {
        com.example.entity.User user = userService.findByUsername(username);
        com.example.entity.Exam exam = examService.getExamById(examId);

        ExamResult result = new ExamResult();
        result.setUser(user);
        result.setExam(exam);
        result.setStartTime(java.time.LocalDateTime.now());
        return examResultRepository.save(result);
    }

    public ExamResult submitExam(com.example.dto.ExamSubmission submission) {
        ExamResult result = examResultRepository.findById(submission.getExamResultId())
                .orElseThrow(() -> new RuntimeException("Exam Result not found"));

        if (result.getEndTime() != null) {
            throw new RuntimeException("Exam already submitted");
        }

        int score = 0;
        com.example.entity.Exam exam = result.getExam();
        java.util.Map<Long, Integer> answers = submission.getAnswers();

        for (com.example.entity.Question question : exam.getQuestions()) {
            if (answers.containsKey(question.getId())) {
                Integer selectedOption = answers.get(question.getId());
                if (selectedOption != null && selectedOption.equals(question.getCorrectOption())) {
                    score++;
                }
            }
        }

        result.setScore(score);
        result.setEndTime(java.time.LocalDateTime.now());
        return examResultRepository.save(result);
    }

    public List<ExamResult> getResultsByUserId(Long userId) {
        return examResultRepository.findByUserId(userId);
    }

    public List<ExamResult> getMyResults(String username) {
        com.example.entity.User user = userService.findByUsername(username);
        return examResultRepository.findByUserId(user.getId());
    }

    public ExamResult getResultById(Long id, String username) {
        ExamResult result = examResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam Result not found"));
        if (!result.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized access to exam result");
        }
        return result;
    }
}
