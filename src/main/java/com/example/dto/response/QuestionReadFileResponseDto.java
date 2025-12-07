package com.example.dto.response;

public class QuestionReadFileResponseDto {
    private Integer numberQuestion;
    private String type;
    private String name;
    private String text;
    private String answerList;
    private String correctAnswerList;

    public Integer getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(Integer numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswerList() {
        return answerList;
    }

    public void setAnswerList(String answerList) {
        this.answerList = answerList;
    }

    public String getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(String correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
    }
}
