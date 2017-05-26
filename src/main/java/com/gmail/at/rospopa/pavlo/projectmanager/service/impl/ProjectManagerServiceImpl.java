package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.ProjectManager;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.ProjectManagerDao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.ProjectManagerService;

public class ProjectManagerServiceImpl extends AbstractService<Long, ProjectManager>
        implements ProjectManagerService {

    private ProjectManagerDao projectManagerDao;

    public ProjectManagerServiceImpl(ProjectManagerDao projectManagerDao) {
        this.projectManagerDao = projectManagerDao;
    }

    @Override
    protected Dao<Long, ProjectManager> getBackingDao() {
        return projectManagerDao;
    }
}
