package edu.bbte.idde.leim2041.backend.dao;

import edu.bbte.idde.leim2041.backend.model.BaseEntity;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    T saveAndFlush(T entity);

    Collection<T> findAll();

    Optional<T> findById(Long id);

    void deleteById(Long id);
}
