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
}
