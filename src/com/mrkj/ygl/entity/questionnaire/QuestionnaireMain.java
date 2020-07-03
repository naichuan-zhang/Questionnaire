package com.mrkj.ygl.entity.questionnaire;

import java.util.Date;

public class QuestionnaireMain {
    private String mainId;

    private String mainTitle;

    private Date mainCreat;

    private String mainIsuse;

    private Date mainEndtime;
    
    private String mainCreatuser;
    
    private Integer mainCreatdep;
    
    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId == null ? null : mainId.trim();
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle == null ? null : mainTitle.trim();
    }

    public Date getMainCreat() {
        return mainCreat;
    }

    public void setMainCreat(Date mainCreat) {
        this.mainCreat = mainCreat;
    }

    public String getMainIsuse() {
        return mainIsuse;
    }

    public void setMainIsuse(String mainIsuse) {
        this.mainIsuse = mainIsuse == null ? null : mainIsuse.trim();
    }

	/**
	 * @return the mainEndtime
	 */
	public Date getMainEndtime() {
		return mainEndtime;
	}

	/**
	 * @param mainEndtime the mainEndtime to set
	 */
	public void setMainEndtime(Date mainEndtime) {
		this.mainEndtime = mainEndtime;
	}

	/**
	 * @return the mainCreatuser
	 */
	public String getMainCreatuser() {
		return mainCreatuser;
	}

	/**
	 * @param mainCreatuser the mainCreatuser to set
	 */
	public void setMainCreatuser(String mainCreatuser) {
		this.mainCreatuser = mainCreatuser;
	}

	/**
	 * @return the mainCreatdep
	 */
	public Integer getMainCreatdep() {
		return mainCreatdep;
	}

	/**
	 * @param mainCreatdep the mainCreatdep to set
	 */
	public void setMainCreatdep(Integer mainCreatdep) {
		this.mainCreatdep = mainCreatdep;
	}

}