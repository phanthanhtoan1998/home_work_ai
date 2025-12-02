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

    public ExamResult saveResult(ExamResult result) {
        return examResultRepository.save(result);
    }

    public List<ExamResult> getResultsByUserId(Long userId) {
        return examResultRepository.findByUserId(userId);
    }
}
