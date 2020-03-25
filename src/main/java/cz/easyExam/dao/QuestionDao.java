package cz.easyExam.dao;

import cz.easyExam.model.Question;
import cz.easyExam.model.Test;
import org.springframework.stereotype.Repository;

@Repository
public class QuestionDao extends BaseDao<Question> {
    public QuestionDao() {
        super(Question.class);
    }
}
