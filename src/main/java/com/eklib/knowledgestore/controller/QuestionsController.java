package com.eklib.knowledgestore.controller;

import com.eklib.knowledgestore.dto.QuestionDTO;
import com.eklib.knowledgestore.dto.QuestionOptionDTO;
import com.eklib.knowledgestore.exception.ResourceNotFoundException;
import com.eklib.knowledgestore.model.category.Category;
import com.eklib.knowledgestore.model.question.Question;
import com.eklib.knowledgestore.model.question.QuestionOption;
import com.eklib.knowledgestore.model.question.QuestionType;
import com.eklib.knowledgestore.model.quiz.Quiz;
import com.eklib.knowledgestore.repository.CategoryRepository;
import com.eklib.knowledgestore.repository.QuestionOptionRepository;
import com.eklib.knowledgestore.repository.QuestionRepository;
import com.eklib.knowledgestore.repository.QuizRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/question")
public class QuestionsController {

    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;

    public QuestionsController(QuestionRepository questionRepository,
                               QuestionOptionRepository questionOptionRepository,
                               QuizRepository quizRepository,
                               CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.questionOptionRepository = questionOptionRepository;
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @PostMapping
    public Question create(@RequestBody QuestionDTO dto) {
        Question question = new Question();
        Set<QuestionOption> correctOptions = new HashSet<>();
        Set<QuestionOption> options = new HashSet<>();
        for (QuestionOptionDTO item: dto.getQuestionOptions()) {
            QuestionOption questionOption = new QuestionOption();
            questionOption.setText(item.getText());
            questionOptionRepository.save(questionOption);
            if (item.getCorrect()) {
                correctOptions.add(questionOption);
            }
            options.add(questionOption);
        }
        question.setText(dto.getText());
        question.setExplanation(dto.getExplanation());
        question.setCorrectOption(correctOptions);
        question.setQuestionOptions(options);
        question.setType(QuestionType.valueOf(dto.getType()));
        Category category = categoryRepository.getOne(1L);
        question.setCategory(category);
        questionRepository.save(question);
        Quiz quiz = quizRepository.getOne(dto.getQuizId());
        Set<Question> questions = quiz.getQuestions();
        questions.add(question);
        quiz.setQuestions(questions);
        quiz.setUpdatedAt(new Date().toInstant());
        quizRepository.save(quiz);
        question.setCategory(null);
        return question;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> findById(@PathVariable(value = "id") Long questionId) throws ResourceNotFoundException {
        return questionRepository.findById(questionId)
                .map(question -> ResponseEntity.ok().body(question))
                .orElse(ResponseEntity.notFound().build());//todo обработку ошибок
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> update(@PathVariable("id") long id, @RequestBody Question question) {
        return questionRepository.findById(id)
                .map(record -> {
                    Question updated = questionRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    //todo добавить другие методы

}
