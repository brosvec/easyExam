package cz.easyExam.service;

import cz.easyExam.dao.QuestionUserDao;
import cz.easyExam.dao.SubscribedTestDao;
import cz.easyExam.dao.TestDao;
import cz.easyExam.exception.ValidationException;
import cz.easyExam.model.Difficulty;
import cz.easyExam.model.SubscribedTest;
import cz.easyExam.model.Test;
import cz.easyExam.model.User;
import cz.easyExam.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
public class TestService {
    private final TestDao dao;
    private final SubscribedTestDao subscribedTestDao;
    private final UserService userService;
    private final QuestionUserDao questionUserDao;


    @Autowired
    public TestService(TestDao dao, SubscribedTestDao subscribedTestDao, UserService userService, QuestionUserDao questionUserDao) {
        this.dao = dao;
        this.subscribedTestDao = subscribedTestDao;
        this.userService = userService;
        this.questionUserDao = questionUserDao;
    }

    @Transactional(readOnly = true)
    public List<Test> findAll() {
        return dao.findAll();
    }
    @Transactional(readOnly = true)
    public List<Test> findAllByParameters(String testName,Difficulty difficulty,String ownerEmail, String ownerUsername) {
        return dao.findAllByParameters(testName,difficulty,ownerEmail,ownerUsername);
    }

    @Transactional(readOnly = true)
    public Test findById(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void deleteById(Integer id) throws ValidationException {
        Test test = findById(id);
        if(isLoggedUsersTest(test)){
            dao.remove(test);
        }
        else {
            throw new ValidationException("This is not test of current user!");
        }
    }

    @Transactional(readOnly = true)
    public List<Test> findloggedUserTests() {
        return dao.findByOwner(SecurityUtils.getCurrentUser().getId());
    }

    @Transactional(readOnly = true)
    public List<Test> findLoggedUserSubscribedTests() {
        return subscribedTestDao.findByUserId(SecurityUtils.getCurrentUser().getId());
    }

    @Transactional
    public Test saveTest(Test test) throws ValidationException {
        Objects.requireNonNull(test);
        if(dao.findByTestName(test.getTestName()) != null){
            throw new ValidationException("Test with this name already exists");
        }
        test.setOwner(SecurityUtils.getCurrentUser());
        dao.persist(test);
        return test;
    }

    @Transactional
    public Test updateTest(Integer id, Test test) {
        Objects.requireNonNull(test);
        Test originalTest = dao.find(id);
        if(isLoggedUsersTest(originalTest)) {
            originalTest.setDifficulty(test.getDifficulty());
            originalTest.setTestName(test.getTestName());
            dao.update(originalTest);
        }
        return originalTest;
    }
    public boolean isLoggedUsersTest (Integer testId) throws ValidationException{
        Test test = dao.find(testId);
        if(test == null){
            throw new ValidationException("Test with this id is not found");
        }
        return test.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId());
    }

    public boolean isLoggedUsersTest(Test test){
        return test.getOwner().getId().equals(SecurityUtils.getCurrentUser().getId());
    }

    @Transactional
    public void subscribeTest(Integer testId, SubscribedTest subscribedTest) {
        SubscribedTest newSubTest = new SubscribedTest();
        Test test = dao.find(testId);
        User user =  userService.getCurrentUser();
        if(test == null){
            throw new ValidationException("test with this id is not found.");
        }
        if(subscribedTestDao.getSubscribedTestRecordBy(test,user) != null ){
            throw new ValidationException("You already subscribe this test.");
        }
        // set attributes
        newSubTest.setUser(user);
        newSubTest.setTest(test);
        newSubTest.setFinished(false);
        newSubTest.setArchived(false);
        newSubTest.setQuestionOrderRandom(subscribedTest.getQuestionOrderRandom());
        subscribedTestDao.persist(newSubTest);
        questionUserDao.createPerTestAndUser(test,user);
    }

    @Transactional
    public void updateSubscribeTest(Integer testId, SubscribedTest subscribedTest) {
        Test test = dao.find(testId);
        if(test == null){
            throw new ValidationException("test with this id is not found");
        }
        User user =  userService.getCurrentUser();
        SubscribedTest originalSubscribedTest = subscribedTestDao.getSubscribedTestRecordBy(test,user);
        // update attributes
        originalSubscribedTest.setFinishDate(subscribedTest.getFinishDate());
        originalSubscribedTest.setFinished(subscribedTest.getFinished());
        originalSubscribedTest.setArchived(subscribedTest.getArchived());
        originalSubscribedTest.setQuestionOrderRandom(subscribedTest.getQuestionOrderRandom());
        // update
        subscribedTestDao.update(originalSubscribedTest);
    }



}
