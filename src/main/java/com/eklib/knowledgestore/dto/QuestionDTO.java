package com.eklib.knowledgestore.dto;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String category;
    private String text;
    private String type;
    private List<QuestionOptionDTO> questionOptions;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, String category, String text, String type, List<QuestionOptionDTO> questionOptions) {
        this.id = id;
        this.category = category;
        this.text = text;
        this.type = type;
        this.questionOptions = questionOptions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<QuestionOptionDTO> getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(List<QuestionOptionDTO> questionOptions) {
        this.questionOptions = questionOptions;
    }
}
