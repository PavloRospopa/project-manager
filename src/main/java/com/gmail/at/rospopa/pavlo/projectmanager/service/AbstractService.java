package com.gmail.at.rospopa.pavlo.projectmanager.service;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.Entity;
import com.gmail.at.rospopa.pavlo.projectmanager.persistence.dao.Dao;

import java.util.List;

public abstract class AbstractService<PK, T extends Entity> implements CrudService<PK, T> {

    protected abstract Dao<PK, T> getBackingDao();

    @Override
    public List<T> findAll() {
        return getBackingDao().findAll();
    }

    @Override
    public T findById(PK id) {
        return getBackingDao().findById(id);
    }

    @Override
    public PK add(T entity) {
        return getBackingDao().add(entity);
    }

    @Override
    public void delete(PK id) {
        getBackingDao().delete(id);
    }

    @Override
    public void update(T entity) {
        getBackingDao().update(entity);
    }
}
