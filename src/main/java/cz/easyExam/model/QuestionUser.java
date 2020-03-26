package cz.easyExam.model;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION_PDA_USER")
public class QuestionUser extends AbstractEntity {

    @ManyToOne
    User user;

    @ManyToOne
    Question question;

    @Enumerated(EnumType.STRING)
    private QuestionState state;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public QuestionState getState() {
        return state;
    }

    public void setState(QuestionState state) {
        this.state = state;
    }
}
