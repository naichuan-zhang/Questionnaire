package com.mrkj.ygl.service.questionnaire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrkj.ygl.dao.questionnaire.QuestionnaireAnswerMapper;
import com.mrkj.ygl.dao.questionnaire.QuestionnaireMainMapper;
import com.mrkj.ygl.dao.questionnaire.QuestionnaireMainUserMapper;
import com.mrkj.ygl.dao.questionnaire.QuestionnaireMsgMapper;
import com.mrkj.ygl.dao.questionnaire.QuestionnaireQuestionMapper;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireAnswer;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireMain;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireMainUser;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireMsg;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireQuestion;

@Service
public class QuestionnaireService {
	
	@Autowired
	QuestionnaireAnswerMapper qam;
	
	@Autowired QuestionnaireMainMapper qmm;
	
	@Autowired
	QuestionnaireQuestionMapper qqm;
	@Autowired
	QuestionnaireMsgMapper msg;
	@Autowired
	QuestionnaireMainUserMapper qmum;
	@Autowired
	SimpleDateFormat sdf;
	
	public int insertMain (QuestionnaireMain record){
		int result = qmm.insertSelective(record);	//调用Dao层insertSelective方法
		return result;								//如果result等于1那么证明插入成功
	}
	
	public List<QuestionnaireMain> findMainPage (Map<String,Object> parm){
		
		return qmm.selectPage(parm);
		
	}
	
	public Long findCount (Map<String,Object> parm){
		
		return qmm.selectCount(parm).get("count");
		
	}
	
	public boolean deleteMainItem (String mainId){
		
		return qmm.deleteByPrimaryKey(mainId)==1?true:false;
		
	}
	
	public boolean deleteCheck (String[] mainIds){
		
		return qmm.delCheck(mainIds)>=1?true:false;
		
	}
	
	/**
	 * 根据mainId返回一份完整问卷,查看结果时候传入isStatistic 为 true
	 * 
	 * select main.main_id,main.main_title,main.main_creat,main.main_creatuser,question.question_id,question.question_title,question.question_type,answer.answer_text,answer.answer_value,answer.answer_type,msg.msg_creatuser,msg.msg_text FROM questionnaire_main main 
     * LEFT JOIN questionnaire_question question ON  main.main_id = question.main_id 
     * LEFT JOIN questionnaire_answer answer ON answer.question_id = question.question_id
	 * LEFT JOIN questionnaire_msg msg on main.main_id = msg.main_id
     * where main.main_id='?'
     * 这是一步到位的查询方式，这种查询方式速度快，但是我们没办法优化结构，如果数据量小的话，下面这种写法很不错，可能有人会觉得麻烦，不过对于小数据量，我还是喜欢这种灵活的方式。
	 * @param mainId
	 * @return
	 */
	public Map<String,Object> selectQuestionnaire(String mainId,boolean isStatistic){
		Map<String,Object> questionnaire  = new HashMap<String, Object>();  //返回模型
		QuestionnaireMain qm = qmm.selectByPrimaryKey(mainId);	//根据mainId获取主表数据
		//根据mainId获取问题参数
		List<QuestionnaireQuestion> questions = qqm.selectByMainId(mainId);
		//定义一个数组该数组用于保存答案id，为了查询答案
		String[] questionIds = new String[questions.size()];
		//迭代问题数据，填充questionIds数组
		int i = 0;
		for (QuestionnaireQuestion entity: questions){
			questionIds[i] = entity.getQuestionId();
			i++;
		}
		//定义一个List，获取答案
		List<QuestionnaireAnswer> answers = new ArrayList<>();
		if (questions.size()>0){
			answers = qam.selectByQuestionId(questionIds);
		}
		//下面代码是为了实现百分比，所以获取一组答案总选择数
		Map<String,Integer> counts = new HashMap<>();
		for (QuestionnaireQuestion entity: questions){
			String questionId = entity.getQuestionId();
			Integer count = 0;
			for (QuestionnaireAnswer answer :answers){
				if (questionId.equals(answer.getQuestionId())){
					count += answer.getAnswerValue();
				}
			}
			counts.put(entity.getQuestionId(), count);
		}
		//如果是统计结果，那么我们获取questionnaire_msg数据
		if (isStatistic){
			List<QuestionnaireMsg> msgs=msg.selectByMainId(mainId);
			questionnaire.put("msgs",msgs);
		}
		questionnaire.put("main", qm);				//模型当中存入主表信息
		questionnaire.put("question", questions);	//模型当中存入问题信息
		questionnaire.put("answer", answers);		//模型当中存入答案信息
		questionnaire.put("counts", counts);		//模型当中存入问题下答案选择总数信息
		
		return questionnaire;
	}
	
	/**
	 * 多态查找方法，这个方法根据MainIsuse
	 * @return
	 */
	public Map<String,Object> selectQuestionnaire(){
		
		Map<String,Object> questionnaire  = new HashMap<String, Object>();
		
		QuestionnaireMain qm = qmm.selectByMainIsuse();
		
		if (qm!=null){
			List<QuestionnaireQuestion> questions = qqm.selectByMainId(qm.getMainId());
			String[] questionIds = new String[questions.size()];
			int i = 0;
			if (questions!=null){
				for (QuestionnaireQuestion entity: questions){
					questionIds[i] = entity.getQuestionId();
					i++;
				}
			}
			List<QuestionnaireAnswer> answers = new ArrayList<>();
			if (questions.size()>0){
				answers = qam.selectByQuestionId(questionIds);
			}
			questionnaire.put("main", qm);
			questionnaire.put("question", questions);
			questionnaire.put("answer", answers);
			
			return questionnaire;
		}else{
			return null;
		}
		
	}
	
	public int insertQuestion (QuestionnaireQuestion record){
		int result = 0;
		
		result = qqm.insert(record);
		
		return result;
	}
	
	public int insertAnswers (String questionId,String [] answerText,String answerDestype[]){
		int result = 0;
		
		qam.deleteByQuestionId(questionId);
		int i = 0;
		for (String at : answerText){
			QuestionnaireAnswer qaEntity = new QuestionnaireAnswer();
			qaEntity.setAnswerId(UUID.randomUUID().toString());
			if (answerDestype !=null){
				qaEntity.setAnswerDestype(answerDestype[i]);
			}
			qaEntity.setAnswerType("#");
			qaEntity.setAnswerText(at);
			qaEntity.setAnswerValue(0);
			qaEntity.setQuestionId(questionId);
			result += qam.insert(qaEntity);
			i++;
		}
		
		return result;
	}
	
	public int insertAnswers (String questionId,String [] answerText){
		int result = 0;
		
		qam.deleteByQuestionId(questionId);
		int i = 0;
		for (String at : answerText){
			QuestionnaireAnswer qaEntity = new QuestionnaireAnswer();
			qaEntity.setAnswerId(UUID.randomUUID().toString());
			qaEntity.setAnswerDestype("#");
			qaEntity.setAnswerType("#");
			qaEntity.setAnswerText(at);
			qaEntity.setAnswerValue(0);
			qaEntity.setQuestionId(questionId);
			result += qam.insert(qaEntity);
			i++;
		}
		
		return result;
	}
	/**
	 * 删除问题与答案
	 * @param questionId
	 * @return
	 */
	public boolean delectQuestionAndAnswer (String questionId){
		
		boolean bflag = qqm.deleteByPrimaryKey(questionId)==1;
		
		return bflag;
		
	}
	
	public QuestionnaireQuestion selectQuestionById (String questionId){
		
		QuestionnaireQuestion question = qqm.selectByPrimaryKey(questionId);
		
		return question;
		
	}

	public Map<String,Object> selectQuestionByIdToAnswer (String questionId){
		
		Map<String,Object> map = new HashMap<>();
		QuestionnaireQuestion question = qqm.selectByPrimaryKey(questionId);
		List<QuestionnaireAnswer> answers = qam.selectByQuestionId(new String[]{questionId});
		map.put("question", question);
		map.put("answers", answers);
		return map;
		
	}
	
	public boolean updateQuestion (QuestionnaireQuestion record){
		
		return qqm.updateByPrimaryKeySelective(record)==1;
	
	}
	
	/**
	 * 这里是发布一份问卷给用户，首先更新问卷标记为isuse的值为n，然后把当前问卷isuse的值为y
	 * @param record
	 * @return
	 */
	public boolean updateMainAction (QuestionnaireMain record){
		
		if (qmm.updateByPrimaryKeySelective(record)==1){
			return true;
		}
		
		return false;
	}
	/**
	 * 更新答案，更新对应表，更新信息
	 * @param answerIds
	 * @param userId
	 * @param mainId
	 * @param message
	 * @return
	 */
	public boolean updateValueIn(List<String> answerIds,String userId,String mainId,String message,String wxname){
		
		QuestionnaireMainUser qmuEntity = new QuestionnaireMainUser();
		qmuEntity.setMainId(mainId);
		qmuEntity.setUserId(userId);
	
		if (message != null && !"".equals(message)){
			QuestionnaireMsg msgEntity = new QuestionnaireMsg();
			msgEntity.setMsgId(UUID.randomUUID().toString());
			msgEntity.setMainId(mainId);
			msgEntity.setMsgText(message);
			msgEntity.setMsgCreatuser(wxname);
			msgEntity.setMsgCreattime(new Date());
			msg.insert(msgEntity);
		}
		
		return qam.updateValueIn(answerIds)>=0&&qmum.insert(qmuEntity)==1&&qmm.updateMainCreat(mainId)==1;
		
	}
	
	public boolean insertMsg(QuestionnaireMsg record){
		
		return msg.insert(record) == 1;
		
	}
	
	public boolean insertQuestionAndAnswers (String mainId,Map<String,String> filePathMap
											,Map<String, String[]> answertext){
		boolean bflag = true;		//保存成功或失败标记
		String questionId = UUID.randomUUID().toString();		//生成问题Id
		QuestionnaireQuestion qqEntity = new QuestionnaireQuestion();		//创建问题实体类
		qqEntity.setMainId(mainId);		//设置主表Id
		qqEntity.setQuestionDestype("file");
		qqEntity.setQuestionId(questionId);		//设置问题Id
		qqEntity.setQuestionTitle(answertext.get("questionTitle")[0]);		//获取问题标题
		qqEntity.setQuestionType(answertext.get("questionType")[0]);		//获取答案类型
		if (qqm.insert(qqEntity)!=1){
			return false;		//保存失败返回false
		}
		answertext.remove("mainId");		//从参数Map当中清除mainId字段
		answertext.remove("questionTitle");		//从参数Map当中清除questionTitle字段
		answertext.remove("questionType");		//从参数Map当中清除questionType字段
		//上面清除了三个字段，那么剩下都都是答案字段，把Map转换为EntrySet
		Set<Entry<String, String[]>> answerEntry = answertext.entrySet();
		//迭代答案
		for (Entry<String, String[]> tempTest : answerEntry){
			String text = tempTest.getValue()[0];		//获取答案参数
			if (text!=null&&!"".equals(text.trim())){
				QuestionnaireAnswer qaEntity = new QuestionnaireAnswer();	//创建答案实体类
				String filePath = filePathMap.get(tempTest.getKey());		//获取文件路径
				if (filePath!=null&&!"".equals(filePath.trim())){		//判断路径是否存在
					qaEntity.setAnswerText(filePath);		//存在则设置路径
					qaEntity.setAnswerType("y");			//y代表存在附件
					//设置答案描述，并给用户提示存在附件
					qaEntity.setAnswerDestype(tempTest.getValue()[0]+"(点击下载附件)");
				}else{
					qaEntity.setAnswerText("#");		//没有附件那么数据初始化一个“#”号标记
					qaEntity.setAnswerType("n");		//n代表不存在附件
					qaEntity.setAnswerDestype(tempTest.getValue()[0]);		//设置答案描述
				}
				qaEntity.setAnswerId(UUID.randomUUID().toString());		//设置答案Id
				qaEntity.setAnswerValue(0);		//设置答案选择数
				qaEntity.setQuestionId(questionId);		//设置问题Id
				qam.insert(qaEntity);		//插入数据
			}
		}
		return bflag;	//返回标记
	}
	
	/**
	 * 更新问题与答案
	 * @param mainId
	 * @param filePathMap
	 * @param answertext
	 * @return
	 */
	public boolean updateQuestionAndAnswers (String mainId,Map<String,String> filePathMap
													,Map<String, String[]> answertext){
		boolean bflag = true;
		String questionId = answertext.get("questionId")[0];
		QuestionnaireQuestion qqEntity = new QuestionnaireQuestion();
		qqEntity.setQuestionId(questionId);
		qqEntity.setQuestionTitle(answertext.get("questionTitle")[0]);
		qqEntity.setQuestionType(answertext.get("questionType")[0]);
		if (qqm.updateByPrimaryKeySelective(qqEntity)!=1){
			return false;
		}
		
		answertext.remove("mainId");
		answertext.remove("questionId");
		answertext.remove("questionTitle");
		answertext.remove("questionType");
		Set<Entry<String, String[]>> answerEntry = answertext.entrySet();

		for (Entry<String, String[]> tempTest : answerEntry){
			String text = tempTest.getValue()[0];
			if (text!=null&&!"".equals(text.trim())){
				QuestionnaireAnswer qaEntity = new QuestionnaireAnswer();
				String filePath = filePathMap.get(tempTest.getKey());
				if (filePath!=null&&!"".equals(filePath.trim())){
					qaEntity.setAnswerText(filePath);
					qaEntity.setAnswerType("y");
					qaEntity.setAnswerDestype(tempTest.getValue()[0]+"(点击下载附件)");
				}else{
					qaEntity.setAnswerText("#");
					qaEntity.setAnswerType("n");
					qaEntity.setAnswerDestype(tempTest.getValue()[0]);
				}
				
				qaEntity.setAnswerId(tempTest.getKey());  
				/**
				 * 更新代码如插入代码相同，不同之处在于以下做了一个判断，首先根据Id更新代码，如果更新不成功则进行插入
				 * 操作。
				 */
				int result= qam.updateByPrimaryKeySelective(qaEntity);
				if (result != 1){
					qaEntity.setAnswerValue(0);
					qaEntity.setQuestionId(questionId);
					qam.insert(qaEntity);
				}
			}
		}
		
		return bflag;
	}
	
	public boolean updateCopyQuestion (String mainId,String username){
		QuestionnaireMain quest = qmm.selectByPrimaryKey(mainId);	//根据Id获取主表数据
		if (quest!=null){
			String newMainId = UUID.randomUUID().toString();		//新建一个主表Id
			quest.setMainId(newMainId);								//设置主表Id
			quest.setMainIsuse("n");								//设置发布状态
			quest.setMainCreat(new Date());							//设置创建时间
			quest.setMainTitle(quest.getMainTitle()+"(副本)");		//设置拷贝标题
			quest.setMainCreatuser(username);						//设置创建人
			qmm.insertSelective(quest);								//插入新数据
			//根据mainId获取问题数据
			List<QuestionnaireQuestion> oldQuestions = qqm.selectByMainId(mainId);
			if (oldQuestions!=null){
				//迭代问题数据，并循环插入新的问题
				for (QuestionnaireQuestion oldQuestion : oldQuestions){
					//获取问题数据Id
					String oldQuestionId = oldQuestion.getQuestionId();
					//新建一个问题Id
					String newQueationId =  UUID.randomUUID().toString();
					//把原数据的Id设置成新数据Id
					oldQuestion.setQuestionId(newQueationId);
					//把源数据的主表Id修改成新主表的Id
					oldQuestion.setMainId(newMainId);
					//插入对象，这里使用的是获取对象模型
					qqm.insert(oldQuestion);
					//根据Id获取答案
					List<QuestionnaireAnswer> oldAnswers = 
							qam.selectByQuestionId(new String[]{oldQuestionId});
					//判断答案是否为空
					if (oldAnswers != null){
						//迭代数据，并循环插入新的答案
						for (QuestionnaireAnswer oldAnswer : oldAnswers){
							//创建一个新的答案Id
							String newAnswerId = UUID.randomUUID().toString();
							//设置新的Id
							oldAnswer.setAnswerId(newAnswerId);
							//设置新的问题Id
							oldAnswer.setQuestionId(newQueationId);
							//初始化选择答案数
							oldAnswer.setAnswerValue(0);
							//插入数据
							qam.insert(oldAnswer);
						}
					}
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	public Boolean updateMainTitle (String mainId,String mainTitle,String mainEndtime){
		
		if (mainId!=null&&mainTitle!=null&&!"".equals(mainId.trim())&&!"".equals(mainTitle.trim())){
			QuestionnaireMain qm = new QuestionnaireMain();
			qm.setMainId(mainId);
			qm.setMainTitle(mainTitle);
			try{
				if (mainEndtime!=null){
					qm.setMainEndtime(sdf.parse(mainEndtime));
				}
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return qmm.updateByPrimaryKeySelective(qm)==1;
		}else{
			return false;
		}
	}
	
	/**
	 * 如果查到则证明用户已经回答过问题
	 * @param userId 用户username
	 * @param mainId 问卷主表ID
	 * @return
	 */
	public Boolean selectMainUser (String userId,String mainId){
		
		if (userId!=null&&!"".equals(userId.trim())&&mainId!=null&&!"".equals(mainId.trim())){
			Map<String,String> parm = new HashMap<>();
			parm.put("mainId", mainId);
			parm.put("userId", userId);
			return qmum.selectByMainIDUserId(parm)==null;
		}else{
			return false;
		}
		
	}
}
