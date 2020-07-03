package com.mrkj.ygl.entity.questionnaire;

public class QuestionnaireAnswer {
    private String answerId;

    private Integer answerValue;

    private String answerType;

    private String answerDestype;

    private String answerText;

    private String questionId;

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId == null ? null : answerId.trim();
    }

    public Integer getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(Integer answerValue) {
        this.answerValue = answerValue;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType == null ? null : answerType.trim();
    }

    public String getAnswerDestype() {
        return answerDestype;
    }

    public void setAnswerDestype(String answerDestype) {
        this.answerDestype = answerDestype == null ? null : answerDestype.trim();
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText == null ? null : answerText.trim();
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }
}