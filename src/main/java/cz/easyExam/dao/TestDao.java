package cz.easyExam.dao;

import cz.easyExam.model.Test;
import cz.easyExam.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class TestDao  extends BaseDao<Test>  {

    public TestDao() {
        super(Test.class);
    }
}
