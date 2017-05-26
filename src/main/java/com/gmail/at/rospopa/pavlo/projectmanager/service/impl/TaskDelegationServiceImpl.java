package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.TaskDelegation;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.TaskDelegationDao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.TaskDelegationService;

public class TaskDelegationServiceImpl extends AbstractService<Long, TaskDelegation>
        implements TaskDelegationService {

    private TaskDelegationDao taskDelegationDao;

    public TaskDelegationServiceImpl(TaskDelegationDao taskDelegationDao) {
        this.taskDelegationDao = taskDelegationDao;
    }

    @Override
    protected Dao<Long, TaskDelegation> getBackingDao() {
        return taskDelegationDao;
    }
}
