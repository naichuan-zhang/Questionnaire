package com.mrkj.ygl.entity.workday;

import java.util.Date;

public class WorkDay {
    private String workId;

    private String workTitle;

    private Date workStart;

    private Date workEnd;

    private String workColor;

    private String workScope;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle == null ? null : workTitle.trim();
    }

    public Date getWorkStart() {
        return workStart;
    }

    public void setWorkStart(Date workStart) {
        this.workStart = workStart;
    }

    public Date getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(Date workEnd) {
        this.workEnd = workEnd;
    }

    public String getWorkColor() {
        return workColor;
    }

    public void setWorkColor(String workColor) {
        this.workColor = workColor == null ? null : workColor.trim();
    }

    public String getWorkScope() {
        return workScope;
    }

    public void setWorkScope(String workScope) {
        this.workScope = workScope == null ? null : workScope.trim();
    }
}