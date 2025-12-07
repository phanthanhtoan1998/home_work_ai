package com.example.service;

import com.example.entity.Exam;
import com.example.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    // New API: Search Filter
    public List<com.example.dto.response.ExamResponseDto> getListExamFilter(
            com.example.dto.request.ExamRequestFilterDTO filter) {
        List<Exam> exams;
        if (filter.getExamName() != null && !filter.getExamName().isEmpty()) {
            exams = examRepository.findByTitleContaining(filter.getExamName());
        } else {
            exams = examRepository.findAll();
        }

        return exams.stream().map(this::convertToDto).collect(java.util.stream.Collectors.toList());
    }

    // New API: Get Detail
    public com.example.dto.response.ExamResponseDto getExamDetail(Long id, Long versionCache) {
        Exam exam = getExamById(id);
        return convertToDto(exam);
    }

    // New API: Create
    public void createExam(com.example.dto.request.CreateExamRequestDTO dto) {
        Exam exam = new Exam();
        exam.setTitle(dto.getName());
        exam.setDescription("Type: " + dto.getType()); // Mapping basics
        // Handle other fields like file upload if implemented
        examRepository.save(exam);
    }

    // New API: Update
    public void updateExam(com.example.dto.request.CreateExamRequestDTO dto, Long examId) {
        Exam exam = getExamById(examId);
        exam.setTitle(dto.getName());
        examRepository.save(exam);
    }

    private com.example.dto.response.ExamResponseDto convertToDto(Exam exam) {
        com.example.dto.response.ExamResponseDto dto = new com.example.dto.response.ExamResponseDto();
        dto.setId(exam.getId());
        dto.setName(exam.getTitle());
        // dto.setType(exam.getType()); // If entity supports it
        dto.setNumberQuestion(exam.getQuestions() != null ? exam.getQuestions().size() : 0);
        return dto;
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}
