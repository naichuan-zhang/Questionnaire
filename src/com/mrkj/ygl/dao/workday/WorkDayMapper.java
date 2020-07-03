package com.mrkj.ygl.dao.workday;

import java.util.List;

import com.mrkj.ygl.entity.workday.WorkDay;

public interface WorkDayMapper {
    int deleteByPrimaryKey(String workId);

    int insert(WorkDay record);

    int insertSelective(WorkDay record);

    WorkDay selectByPrimaryKey(String workId);

    int updateByPrimaryKeySelective(WorkDay record);

    int updateByPrimaryKey(WorkDay record);
    
    List<WorkDay> selectAll();
}