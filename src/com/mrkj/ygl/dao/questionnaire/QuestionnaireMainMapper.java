package com.mrkj.ygl.dao.questionnaire;

import java.util.List;
import java.util.Map;

import com.mrkj.ygl.entity.questionnaire.QuestionnaireMain;

public interface QuestionnaireMainMapper {
    int deleteByPrimaryKey(String mainId);

    int insert(QuestionnaireMain record);

    int insertSelective(QuestionnaireMain record);

    QuestionnaireMain selectByPrimaryKey(String mainId);

    QuestionnaireMain selectByMainIsuse();
    
    List<QuestionnaireMain> selectPage(Map<String,Object> parm);
    
    Map<String,Long> selectCount(Map<String,Object> parm);
    
    int updateByPrimaryKeySelective(QuestionnaireMain record);

    int updateByPrimaryKey(QuestionnaireMain record);
    
    int delCheck(String[] mainIds);
    
    int updateMainIsuseN ();
    
    int updateMainCreat (String mainId);
    
}