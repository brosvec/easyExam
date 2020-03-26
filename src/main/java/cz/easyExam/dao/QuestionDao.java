package cz.easyExam.dao;

import cz.easyExam.model.Question;
import cz.easyExam.model.Test;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;

@Repository
public class QuestionDao extends BaseDao<Question> {
    public QuestionDao() {
        super(Question.class);
    }

    public List<Question> findByTestId(Integer testId) {
        Query jpqlQuery = em.createQuery("SELECT q FROM Question q WHERE q.test.id=:testId");
        jpqlQuery.setParameter("testId", testId);
        return jpqlQuery.getResultList();
    }

    @Override
    public void persist(Question entity) {
        List<Question> questions = findByTestId(entity.getTest().getId());
        // get highest question number
        int maxQuestionNumber = 0;
        if(questions.size() > 0) {
           maxQuestionNumber = questions.stream().max(Comparator.comparing(a -> a.getQuestionNumber())).get().getQuestionNumber();
        }
        entity.setQuestionNumber((maxQuestionNumber + 1));
        super.persist(entity);
    }

    @Override
    public void remove(Question entity) {
        updateQuestionNumberInTestFromQuestion(entity);
        super.remove(entity);
    }

    private void updateQuestionNumberInTestFromQuestion(Question question){
        List<Question> questions =  findByTestId(question.getTest().getId());
        // update number
        questions.stream().filter(a -> a.getQuestionNumber() > question.getQuestionNumber()).forEach(a -> a.setQuestionNumber((a.getQuestionNumber() - 1)));
        // update in DB
        for (Question item:questions) {
            update(item);
        }
    }
}
