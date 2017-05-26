package com.gmail.at.rospopa.pavlo.projectmanager.service.impl;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.Administrator;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.AdministratorDao;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AbstractService;
import com.gmail.at.rospopa.pavlo.projectmanager.service.AdministratorService;

public class AdministratorServiceImpl extends AbstractService<Long, Administrator>
        implements AdministratorService {

    private AdministratorDao administratorDao;

    public AdministratorServiceImpl(AdministratorDao administratorDao) {
        this.administratorDao = administratorDao;
    }

    @Override
    protected Dao<Long, Administrator> getBackingDao() {
        return administratorDao;
    }
}
