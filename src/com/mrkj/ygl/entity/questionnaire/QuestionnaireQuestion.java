package com.mrkj.ygl.entity.questionnaire;

public class QuestionnaireQuestion {
    private String questionId;

    private String questionTitle;

    private String questionType;
    
    private String questionDestype;
    
    private String mainId;

    /**
	 * @return the questionType
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType the questionType to set
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return the questionDestype
	 */
	public String getQuestionDestype() {
		return questionDestype;
	}

	/**
	 * @param questionDestype the questionDestype to set
	 */
	public void setQuestionDestype(String questionDestype) {
		this.questionDestype = questionDestype;
	}

	public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId == null ? null : questionId.trim();
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle == null ? null : questionTitle.trim();
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId == null ? null : mainId.trim();
    }
}