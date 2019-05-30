package com.eklib.knowledgestore.controller;

import com.eklib.knowledgestore.dto.QuestionDTO;
import com.eklib.knowledgestore.dto.QuestionOptionDTO;
import com.eklib.knowledgestore.dto.QuizDTO;
import com.eklib.knowledgestore.model.question.Question;
import com.eklib.knowledgestore.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.eklib.knowledgestore.model.quiz.Quiz;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public List<QuizDTO> getAll() {
        List<Quiz> quizList = quizRepository.findAll();
        return quizList.stream().map(quiz ->
                new QuizDTO(
                        quiz.getId(),
                        quiz.getUser().getId(),
                        quiz.getName(),
                        quiz.getDescription(),
                        getQuestionDTOsFromQuiz(quiz)
                )
        ).collect(Collectors.toList());
    }

    @GetMapping(value = "{id:[0-9]+}")
    public QuizDTO getQuizById(@PathVariable("id") Long id) {
        Quiz quiz = quizRepository.getOne(id);
        return new QuizDTO(
                quiz.getId(),
                quiz.getUser().getId(),
                quiz.getName(),
                quiz.getDescription(),
                getQuestionDTOsFromQuiz(quiz)
        );
    }

    private List<QuestionDTO> getQuestionDTOsFromQuiz(Quiz quiz) {
        return quiz.getQuestions().stream()
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getCategory().getName(),
                        question.getText(),
                        question.getType().name(),
                        getQuestionOptionDTOFromQuestion(question)))
                .sorted(Comparator.comparing(QuestionDTO::getId))
                .collect(Collectors.toList());
    }

    private List<QuestionOptionDTO> getQuestionOptionDTOFromQuestion(Question question) {
        return question.getQuestionOptions()
                .stream()
                .map(questionOption -> new QuestionOptionDTO(
                        questionOption.getId(),
                        questionOption.getText()))
                .collect(Collectors.toList());
    }
}

