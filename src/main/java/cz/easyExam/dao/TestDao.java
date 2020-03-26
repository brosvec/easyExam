package cz.easyExam.dao;

import cz.easyExam.model.Difficulty;
import cz.easyExam.model.Test;
import cz.easyExam.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TestDao  extends BaseDao<Test>  {

    public TestDao() {
        super(Test.class);
    }

    public List<Test> findByOwner(Integer ownerId){
        Query jpqlQuery = em.createQuery("SELECT t FROM Test t WHERE t.owner.id=:idOwner");
        jpqlQuery.setParameter("idOwner", ownerId);
        return jpqlQuery.getResultList();
    }

    public Test findByTestName(String testName) {
        try {
            Query jpqlQuery = em.createQuery("SELECT t FROM Test t WHERE t.testName=:testName");
            jpqlQuery.setParameter("testName", testName);
            return (Test) jpqlQuery.getSingleResult();
        }  catch (NoResultException e) {
            return null;
        }
    }

    public List<Test> findAllByParameters(String testName, Difficulty difficulty, String ownerEmail, String ownerUsername) {
        Query jpqlQuery;
        if(difficulty != null) {
            jpqlQuery = em.createQuery("SELECT t FROM Test t WHERE t.testName LIKE :testName AND t.owner.email LIKE :ownerEmail " +
                    "AND t.owner.username LIKE :ownerUsername AND t.difficulty = :difficulty");
            jpqlQuery.setParameter("difficulty", difficulty);
        }
        else{
         jpqlQuery = em.createQuery("SELECT t FROM Test t WHERE t.testName LIKE :testName AND t.owner.email LIKE :ownerEmail AND t.owner.username LIKE :ownerUsername ");
        }
        jpqlQuery.setParameter("testName", '%'+testName+'%');
        jpqlQuery.setParameter("ownerEmail", '%'+ownerEmail+'%');
        jpqlQuery.setParameter("ownerUsername", '%'+ownerUsername+'%');
        return jpqlQuery.getResultList();
    }
}
