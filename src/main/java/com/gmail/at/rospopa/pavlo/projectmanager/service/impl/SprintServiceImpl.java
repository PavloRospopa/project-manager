package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.Sprint;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.SprintDao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.SprintService;

public class SprintServiceImpl extends AbstractService<Long, Sprint>
        implements SprintService {

    private SprintDao sprintDao;

    public SprintServiceImpl(SprintDao sprintDao) {
        this.sprintDao = sprintDao;
    }

    @Override
    protected Dao<Long, Sprint> getBackingDao() {
        return sprintDao;
    }
}
