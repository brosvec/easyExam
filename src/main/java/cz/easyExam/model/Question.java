package cz.easyExam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUESTION")
public class Question extends AbstractEntity {

    @Basic(optional = false)
    @Column(nullable = false)
    private String questionText;

    @Column
    private String answerText;

    @Column
    private int questionNumber;

    @JsonIgnore
    @Lob
    @Column(length=100000)
    private byte[] questionAttachment;

    @JsonIgnore
    @Lob
    @Column(length=100000)
    private byte[] answer;

    @JsonIgnore
    @ManyToOne
    private Test test;

    @JsonIgnore
    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "question_id")
    private List<QuestionUser> questionUsers;

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public byte[] getQuestionAttachment() {
        return questionAttachment;
    }

    public void setQuestionAttachment(byte[] questionAttachment) {
        this.questionAttachment = questionAttachment;
    }

    public byte[] getAnswer() {
        return answer;
    }

    public void setAnswer(byte[] answer) {
        this.answer = answer;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<QuestionUser> getQuestionUsers() {
        return questionUsers;
    }

    public void setQuestionUsers(List<QuestionUser> questionUsers) {
        this.questionUsers = questionUsers;
    }
}
