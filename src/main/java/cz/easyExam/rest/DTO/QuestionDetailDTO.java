package cz.easyExam.rest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.easyExam.model.Question;
import cz.easyExam.model.Test;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

public class QuestionDetailDTO {
    private String questionText;
    private String answerText;
    private int questionNumber;
    private byte[] questionAttachment;
    private byte[] answer;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
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

    public static List<QuestionDetailDTO> convertQuestionsToDetailDTO(List<Question> questions) {
        List<QuestionDetailDTO> questionDetailDTOList = new ArrayList<>();
        for (Question question: questions) {
            questionDetailDTOList.add(convertQuestionToDetailDTO(question));
        }
        return questionDetailDTOList;
    }

    public static Question convertDetailDTOtoQuestion(QuestionDetailDTO questionDetailDTO){
        Question question = new Question();
        question.setQuestionNumber(questionDetailDTO.getQuestionNumber());
        question.setAnswerText(questionDetailDTO.getAnswerText());
        question.setAnswer(questionDetailDTO.getAnswer());
        question.setQuestionText(questionDetailDTO.getQuestionText());
        question.setQuestionAttachment(questionDetailDTO.getQuestionAttachment());
        question.setId(questionDetailDTO.getId());
        return question;
    }

    public static QuestionDetailDTO convertQuestionToDetailDTO(Question question){
        QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
        questionDetailDTO.setQuestionNumber(question.getQuestionNumber());
        questionDetailDTO.setAnswerText(question.getAnswerText());
        questionDetailDTO.setAnswer(question.getAnswer());
        questionDetailDTO.setQuestionText(question.getQuestionText());
        questionDetailDTO.setQuestionAttachment(question.getQuestionAttachment());
        questionDetailDTO.setId(question.getId());
        return questionDetailDTO;
    }
}
