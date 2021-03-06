package com.eklib.knowledgestore.controller;

import com.eklib.knowledgestore.dto.*;
import com.eklib.knowledgestore.model.question.Question;
import com.eklib.knowledgestore.model.question.QuestionOption;
import com.eklib.knowledgestore.model.user.User;
import com.eklib.knowledgestore.repository.QuizRepository;
import com.eklib.knowledgestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.eklib.knowledgestore.model.quiz.Quiz;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizController(QuizRepository quizRepository,
                          UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
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
                        getQuestionDTOsFromQuiz(quiz, false)
                )
        ).collect(Collectors.toList());
    }

    @GetMapping("/forUpdate")
    public List<QuizDTO> getAllForUpdate() {
        List<Quiz> quizList = quizRepository.findAll();
        return quizList.stream().map(quiz ->
                new QuizDTO(quiz.getId(),
                        quiz.getName(),
                        quiz.getDescription()))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id:[0-9]+}")
    public QuizDTO getQuizById(@PathVariable("id") Long id) {
        Quiz quiz = quizRepository.getOne(id);
        return new QuizDTO(
                quiz.getId(),
                quiz.getUser().getId(),
                quiz.getName(),
                quiz.getDescription(),
                getQuestionDTOsFromQuiz(quiz, false)
        );
    }

    @PostMapping("/create")
    public Quiz create(@RequestBody QuizDTO dto) {
        User user = userRepository.getOne(1L);
        Quiz quiz = new Quiz();
        quiz.setName(dto.getName());
        quiz.setDescription(dto.getDescription());
        quiz.setCreatedAt(new Date().toInstant());
        quiz.setUpdatedAt(new Date().toInstant());
        quiz.setUser(user);
        quizRepository.save(quiz);
        quiz.setUser(null);
        return quiz;
    }

    @PostMapping(value = "/check", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public QuizResultDTO checkAnswers(@RequestBody QuizAnswersDTO quizAnswers) {
        Quiz quiz = quizRepository.getOne(quizAnswers.getQuizId());
        Set<Long> answersIds = quizAnswers.getAnswerIds();

        List<QuestionDTO> questionDTOList = getQuestionDTOsFromQuiz(quiz, true);
        questionDTOList.forEach(questionDTO ->
                questionDTO.getQuestionOptions().forEach(questionOptionDTO -> {
                    if (answersIds.contains(questionOptionDTO.getId()) && questionOptionDTO.getCorrect() == null) {
                        questionOptionDTO.setCorrect(false);
                    }
                })
        );

        QuizDTO quizDTO = new QuizDTO(
                quiz.getId(),
                quiz.getUser().getId(),
                quiz.getName(),
                quiz.getDescription(),
                questionDTOList
        );

        return new QuizResultDTO(quizDTO, "lol");
    }

    private List<QuestionDTO> getQuestionDTOsFromQuiz(Quiz quiz, Boolean withCorrectAnswers) {
        return quiz.getQuestions().stream()
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getCategory().getName(),
                        question.getText(),
                        question.getType().name(),
                        getQuestionOptionDTOFromQuestion(question, withCorrectAnswers),
                        withCorrectAnswers ? question.getExplanation() : null))
                .sorted(Comparator.comparing(QuestionDTO::getId))
                .collect(Collectors.toList());
    }

    private List<QuestionOptionDTO> getQuestionOptionDTOFromQuestion(Question question, Boolean withCorrectAnswers) {
        Set<Long> correctOptionsIds = question.getCorrectOption().stream()
                .map(QuestionOption::getId)
                .collect(Collectors.toSet());
        return question.getQuestionOptions()
                .stream()
                .map(questionOption -> new QuestionOptionDTO(
                        questionOption.getId(),
                        questionOption.getText(),
                        correctOptionsIds.contains(questionOption.getId()) && withCorrectAnswers ? true : null))
                .collect(Collectors.toList());
    }
}

