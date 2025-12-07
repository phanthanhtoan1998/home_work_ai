package com.example.dto;

import java.util.Map;

public class ExamSubmission {
    private Long examResultId;
    private Map<Long, Integer> answers;

    public Long getExamResultId() {
        return examResultId;
    }

    public void setExamResultId(Long examResultId) {
        this.examResultId = examResultId;
    }

    public Map<Long, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Integer> answers) {
        this.answers = answers;
    }
}
