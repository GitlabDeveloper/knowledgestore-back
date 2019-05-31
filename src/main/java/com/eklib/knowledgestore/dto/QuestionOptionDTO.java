package com.eklib.knowledgestore.dto;

public class QuestionOptionDTO {
    private Long id;
    private String text;
    private boolean correct;

    public QuestionOptionDTO() {
    }
    
    public QuestionOptionDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public QuestionOptionDTO(Long id, String text, boolean correct) {
        this.id = id;
        this.text = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
