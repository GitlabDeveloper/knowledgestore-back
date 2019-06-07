package com.eklib.knowledgestore.dto;

public class QuizResultDTO {

    private QuizDTO quizDTO;
    private String quizResult;

    public QuizResultDTO() {
    }

    public QuizResultDTO(QuizDTO quizDTO, String quizResult) {
        this.quizDTO = quizDTO;
        this.quizResult = quizResult;
    }

    public QuizDTO getQuizDTO() {
        return quizDTO;
    }

    public void setQuizDTO(QuizDTO quizDTO) {
        this.quizDTO = quizDTO;
    }

    public String getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(String quizResult) {
        this.quizResult = quizResult;
    }
}
