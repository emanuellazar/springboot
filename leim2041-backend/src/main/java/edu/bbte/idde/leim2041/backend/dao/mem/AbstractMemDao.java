package edu.bbte.idde.leim2041.backend.dao.mem;

import edu.bbte.idde.leim2041.backend.dao.Dao;
import edu.bbte.idde.leim2041.backend.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
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
    public T findById(Long id) {
        log.info("Object with id " + id.toString() + " returned");
        return database.get(id);
    }

    @Override
    public T create(T entity) {
        Long newId = ENTITY_ID.incrementAndGet();
        entity.setId(newId);
        database.put(newId, entity);
        log.info("New object created: " + entity);
        return  entity;
    }

    @Override
    public T update(Long id, T entity) {
        log.info("Object updated\n");
        database.replace(id, entity);
        return entity;
    }

    @Override
    public void delete(Long id) {
        log.info("Object with id " + findById(id).toString() + " deleted");
        database.remove(id);
    }
}
