package cz.easyExam.dao;

import cz.easyExam.model.SubscribedTest;
import cz.easyExam.model.Test;
import cz.easyExam.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class SubscribedTestDao extends BaseDao<SubscribedTest> {

    public SubscribedTestDao() {
        super(SubscribedTest.class);
    }

    public SubscribedTest getSubscribedTestRecordBy(Test test, User user)
    {
        Query jpqlQuery = em.createQuery("SELECT st FROM SubscribedTest st WHERE st.test = :test AND st.user = :user");
        jpqlQuery.setParameter("test", test);
        jpqlQuery.setParameter("user", user);
        return (SubscribedTest) jpqlQuery.getSingleResult();
    }

    public List<Test> findByUserId(Integer userId) {
        Query jpqlQuery = em.createQuery("SELECT st FROM SubscribedTest st WHERE  st.user.id = :userId");
        jpqlQuery.setParameter("userId", userId);
        return jpqlQuery.getResultList();
    }
}
