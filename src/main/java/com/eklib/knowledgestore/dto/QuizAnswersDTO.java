package com.eklib.knowledgestore.dto;

import java.util.Set;

public class QuizAnswersDTO {
    private Long quizId;
    private Set<Long> answerIds;

    public QuizAnswersDTO() {
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Set<Long> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(Set<Long> answerIds) {
        this.answerIds = answerIds;
    }
}
