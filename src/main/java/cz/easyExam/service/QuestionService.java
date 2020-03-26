package cz.easyExam.service;

import cz.easyExam.dao.QuestionDao;
import cz.easyExam.exception.ValidationException;
import cz.easyExam.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class QuestionService {
    private final QuestionDao dao;
    private final TestService testService;

    @Autowired
    public QuestionService(QuestionDao dao, TestService testService) {
        this.dao = dao;
        this.testService = testService;
    }

    @Transactional
    public Question saveQuestion(Integer testId,Question question) throws ValidationException {
        Objects.requireNonNull(question);
        if(!testService.isLoggedUsersTest(testId)) {
            throw new ValidationException("This is not test of current user");
        }
        question.setTest(testService.findById(testId));
        dao.persist(question);
        return question;
    }

    @Transactional(readOnly = true)
    public List<Question> findByTest(Integer testId) {
        return  dao.findByTestId(testId);
    }

    @Transactional(readOnly = true)
    public Question findById(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void update(Integer questionId, Question question) throws ValidationException{
        Objects.requireNonNull(question);
       Question originalQuestion = dao.find(questionId);
       if(originalQuestion == null){
            throw new ValidationException("question with this id does not exist");
       }
        if(!testService.isLoggedUsersTest(originalQuestion.getTest())) {
            throw new ValidationException("This is not test of current user");
        }
       // set variables
        originalQuestion.setQuestionText(question.getQuestionText());
        originalQuestion.setAnswerText(question.getAnswerText());
        originalQuestion.setAnswer(question.getAnswer());
        // update
       dao.update(question);
    }

    @Transactional
    public void remove(Integer questionId) {
        Question question = dao.find(questionId);
        dao.remove(question);
    }
}
