package edu.bbte.idde.leim2041.backend.dao.mem;

import edu.bbte.idde.leim2041.backend.controller.NotFoundException;
import edu.bbte.idde.leim2041.backend.dao.Dao;
import edu.bbte.idde.leim2041.backend.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class AbstractMemDao<T extends BaseEntity> implements Dao<T> {
    protected final ConcurrentHashMap<Long, T> database = new ConcurrentHashMap<>();
    private static final AtomicLong ENTITY_ID = new AtomicLong();

    @Override
    public Collection<T> findAll() {
        log.info("All objects returned");
        return database.values();
    }

    @Override
    public Optional<T> findById(Long id) {
        log.info("Object with id " + id.toString() + " returned");
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public T saveAndFlush(T entity) {
        Long newId = ENTITY_ID.incrementAndGet();
        entity.setId(newId);
        database.put(newId, entity);
        log.info("New object created: " + entity);
        return  entity;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Object with id " + findById(id).toString() + " deleted");
        try {
            database.remove(id);
        } catch (NotFoundException e) {
            log.info(String.valueOf(e));
        }
    }
}
