package com.example.dto.request;

import java.util.List;

public class ExamRequestFilterDTO {

    private List<Long> tagIds;
    private String examName;
    private String questionsTextAz;
    private String searchText;
    private Integer limit = 40;
    private Integer page = 1;
    private Long clientVersion;

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getQuestionsTextAz() {
        return questionsTextAz;
    }

    public void setQuestionsTextAz(String questionsTextAz) {
        this.questionsTextAz = questionsTextAz;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(Long clientVersion) {
        this.clientVersion = clientVersion;
    }
}
