package cz.easyExam.service;

import cz.easyExam.dao.TestDao;
import cz.easyExam.model.Difficulty;
import cz.easyExam.model.Test;
import cz.easyExam.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TestService {
    private final TestDao dao;

    @Autowired
    public TestService(TestDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public List<Test> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Test saveTest(Test test) {
        Objects.requireNonNull(test);
        test.setOwner(SecurityUtils.getCurrentUser());
        dao.persist(test);
        return test;
    }

}
