package com.example.repository;

import com.example.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    java.util.List<Exam> findByTitleContaining(String title);
}
