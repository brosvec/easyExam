package cz.easyExam.rest;

import cz.easyExam.exception.ValidationException;
import cz.easyExam.model.Question;
import cz.easyExam.rest.DTO.QuestionDetailDTO;
import cz.easyExam.service.QuestionService;
import cz.easyExam.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/question")
public class QuestionController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/{Id}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public QuestionDetailDTO getQuestion(@PathVariable Integer Id) {
        Question question = questionService.findById(Id);
        return QuestionDetailDTO.convertQuestionToDetailDTO(question);
    }

    @GetMapping(value = "/testId/{testId}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<QuestionDetailDTO> getQuestions(@PathVariable Integer testId) {
       List<Question> questions = questionService.findByTest(testId);
        return QuestionDetailDTO.convertQuestionsToDetailDTO(questions);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping(value = "/test/{testId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveQuestion(@PathVariable Integer testId, @RequestBody QuestionDetailDTO questionDetailDTO) {
            Question question = QuestionDetailDTO.convertDetailDTOtoQuestion(questionDetailDTO);
            questionService.saveQuestion(testId,question);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping(value = "/{Id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateQuestion(@PathVariable Integer Id,@RequestBody QuestionDetailDTO questionDetailDTO) {
            Question question = QuestionDetailDTO.convertDetailDTOtoQuestion(questionDetailDTO);
            questionService.update(Id,question);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping(value = "/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Integer questionId) {
        questionService.remove(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
