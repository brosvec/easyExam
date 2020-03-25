package cz.easyExam.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SUBSCRIBED_TEST",
       uniqueConstraints= { @UniqueConstraint(columnNames = {"test_id", "user_id"}) }
        )
public class SubscribedTest  extends AbstractEntity  {

    @Basic(optional = false)
    @Column(nullable = false)
    private Boolean isFinished;

    @Basic(optional = false)
    @Column(nullable = false)
    private Boolean isQuestionOrderRandom;

    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDate finishDate;

    @ManyToOne
    private Test test;

    @ManyToOne
    private User user;

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getQuestionOrderRandom() {
        return isQuestionOrderRandom;
    }

    public void setQuestionOrderRandom(Boolean questionOrderRandom) {
        isQuestionOrderRandom = questionOrderRandom;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
