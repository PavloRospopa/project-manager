package com.gmail.at.rospopa.pavlo.projectmanager.service;

import com.gmail.at.rospopa.pavlo.projectmanager.domain.Entity;

import java.util.List;

public interface CrudService<PK, T extends Entity> {
    List<T> findAll();
    T findById(PK id);
    PK add(T entity);
    void delete(PK id);
    void update(T entity);
}
