package cz.easyExam.dao;

import cz.easyExam.model.*;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionUserDao extends BaseDao<QuestionUser> {
    public QuestionUserDao() {
        super(QuestionUser.class);
    }

    public void createPerTestAndUser(Test test, User user) {
        for (Question question: test.getQuestions()) {
            // set atributes
            QuestionUser questionUser = new QuestionUser();
            questionUser.setUser(user);
            questionUser.setQuestion(question);
            questionUser.setState(QuestionState.SKIPPED);

            persist(questionUser);
        }
    }
}
