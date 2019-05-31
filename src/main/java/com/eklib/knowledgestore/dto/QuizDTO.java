package com.eklib.knowledgestore.dto;

import java.util.List;

public class QuizDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private List<QuestionDTO> questions;

    public QuizDTO() {
    }

    public QuizDTO(Long id, Long userId, String name, String description, List<QuestionDTO> questions) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public QuizDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public QuizDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
