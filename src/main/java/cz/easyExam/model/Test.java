package cz.easyExam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity

@Table(name = "Test")
public class Test extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false,unique = true)
    private String testName;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne
    private User owner;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "test_id")
    private List<SubscribedTest> subscribedTest;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "test_id")
    private List<Question> questions;

    @Basic(optional = false)
    @Column(nullable = false)
    private boolean isPublished;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<SubscribedTest> getSubscribedTest() {
        return subscribedTest;
    }

    public void setSubscribedTest(List<SubscribedTest> subscribedTest) {
        this.subscribedTest = subscribedTest;
    }
}
