package com.example.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class BaseExamRequestDto {

    private String name;
    private MultipartFile fileImage;
    private String type;
    private Integer numberQuestion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFileImage() {
        return fileImage;
    }

    public void setFileImage(MultipartFile fileImage) {
        this.fileImage = fileImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(Integer numberQuestion) {
        this.numberQuestion = numberQuestion;
    }
}
