package com.eklib.knowledgestore.controller;

import com.eklib.knowledgestore.exception.ResourceNotFoundException;
import com.eklib.knowledgestore.model.question.Question;
import com.eklib.knowledgestore.repository.QuestionOptionRepository;
import com.eklib.knowledgestore.repository.QuestionRepository;
import com.eklib.knowledgestore.repository.QuizRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/question/")
public class QuestionsController {

    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final QuizRepository quizRepository;

    public QuestionsController(QuestionRepository questionRepository,
                               QuestionOptionRepository questionOptionRepository,
                               QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.questionOptionRepository = questionOptionRepository;
        this.quizRepository = quizRepository;
    }

    @GetMapping
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @PostMapping
    public Question create(@RequestBody Question question) {
        return questionRepository.save(question);
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
