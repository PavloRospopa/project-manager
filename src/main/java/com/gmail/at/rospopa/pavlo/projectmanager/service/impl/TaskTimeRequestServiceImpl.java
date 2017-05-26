package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.TaskTimeRequest;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.TaskTimeRequestDao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.TaskTimeRequestService;

public class TaskTimeRequestServiceImpl extends AbstractService<Long, TaskTimeRequest>
        implements TaskTimeRequestService {

    private TaskTimeRequestDao taskTimeRequestDao;

    public TaskTimeRequestServiceImpl(TaskTimeRequestDao taskTimeRequestDao) {
        this.taskTimeRequestDao = taskTimeRequestDao;
    }

    @Override
    protected Dao<Long, TaskTimeRequest> getBackingDao() {
        return taskTimeRequestDao;
    }
}