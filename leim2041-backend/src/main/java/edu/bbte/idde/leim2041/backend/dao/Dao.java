package edu.bbte.idde.leim2041.backend.dao;

import edu.bbte.idde.leim2041.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    T create(T entity);

    Collection<T> findAll();

    T findById(Long id);

    T update(Long id, T entity);

    void delete(Long id);
}
