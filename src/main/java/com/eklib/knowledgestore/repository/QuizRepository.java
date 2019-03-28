package com.eklib.knowledgestore.repository;

import com.eklib.knowledgestore.model.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    //TODO
}
