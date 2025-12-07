package com.example.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class CreateExamRequestDTO extends BaseExamRequestDto {

    private Long tagId;
    private MultipartFile questionFile;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public MultipartFile getQuestionFile() {
        return questionFile;
    }

    public void setQuestionFile(MultipartFile questionFile) {
        this.questionFile = questionFile;
    }
}
