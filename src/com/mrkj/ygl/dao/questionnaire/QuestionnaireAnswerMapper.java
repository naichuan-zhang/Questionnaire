package com.mrkj.ygl.dao.questionnaire;

import java.util.List;

import com.mrkj.ygl.entity.questionnaire.QuestionnaireAnswer;

public interface QuestionnaireAnswerMapper {
    int deleteByPrimaryKey(String answerId);

    int insert(QuestionnaireAnswer record);

    int insertSelective(QuestionnaireAnswer record);

    QuestionnaireAnswer selectByPrimaryKey(String answerId);

    int updateByPrimaryKeySelective(QuestionnaireAnswer record);

    int updateByPrimaryKey(QuestionnaireAnswer record);
    
    List<QuestionnaireAnswer> selectByQuestionId(String[] questionIds);
    
    int deleteByQuestionId(String questionId);
    
    int updateValueIn(List<String> answerIds);
}