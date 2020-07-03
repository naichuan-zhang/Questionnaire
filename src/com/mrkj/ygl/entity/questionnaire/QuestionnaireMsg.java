package com.mrkj.ygl.entity.questionnaire;

import java.util.Date;

public class QuestionnaireMsg {
    private String msgId;

    private String msgText;

    private String mainId;

    private String msgCreatuser;

    private Date msgCreattime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText == null ? null : msgText.trim();
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId == null ? null : mainId.trim();
    }

    public String getMsgCreatuser() {
        return msgCreatuser;
    }

    public void setMsgCreatuser(String msgCreatuser) {
        this.msgCreatuser = msgCreatuser == null ? null : msgCreatuser.trim();
    }

    public Date getMsgCreattime() {
        return msgCreattime;
    }

    public void setMsgCreattime(Date msgCreattime) {
        this.msgCreattime = msgCreattime;
    }
}