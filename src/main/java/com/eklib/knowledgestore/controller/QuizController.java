package com.eklib.knowledgestore.controller;

import com.eklib.knowledgestore.dto.QuestionDTO;
import com.eklib.knowledgestore.dto.QuestionOptionDTO;
import com.eklib.knowledgestore.dto.QuizAnswersDTO;
import com.eklib.knowledgestore.dto.QuizDTO;
import com.eklib.knowledgestore.model.question.Question;
import com.eklib.knowledgestore.model.user.User;
import com.eklib.knowledgestore.repository.QuizRepository;
import com.eklib.knowledgestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.eklib.knowledgestore.model.quiz.Quiz;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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
                        getQuestionDTOsFromQuiz(quiz)
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
                getQuestionDTOsFromQuiz(quiz)
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
    public String checkAnswers(@RequestBody QuizAnswersDTO quizAnswers) {
        Quiz quiz = quizRepository.getOne(quizAnswers.getQuizId());
        Set<Question> questions = quiz.getQuestions();
        Set<Long> questionCorrectOptions = new HashSet<>();
        questions.forEach(question ->
                question.getCorrectOption().forEach(correctOption ->
                        questionCorrectOptions.add(correctOption.getId())
                )
        );
        AtomicInteger result = new AtomicInteger(0);
        quizAnswers.getAnswerIds().forEach(answerId -> {
            if (questionCorrectOptions.contains(answerId)) result.getAndIncrement();
        });

        return "{\"name\" : \"Ваш результат "
                .concat(String.valueOf(result.get()))
                .concat(" из ")
                .concat(String.valueOf(questionCorrectOptions.size()))
                .concat("\"}");
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

