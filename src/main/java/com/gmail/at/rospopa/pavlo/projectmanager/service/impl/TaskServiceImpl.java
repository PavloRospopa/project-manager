package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.Task;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.TaskDao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.TaskService;

public class TaskServiceImpl extends AbstractService<Long, Task>
        implements TaskService {

    private TaskDao taskDao;

    public TaskServiceImpl(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    protected Dao<Long, Task> getBackingDao() {
        return taskDao;
    }
}
