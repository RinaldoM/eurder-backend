package com.switchfully.order.domain;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

public abstract class Repository<T extends BaseEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public T save(T entity) {
        entity.generateId();
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public abstract List<T> getAll();
    public abstract T get(UUID entityId);

}
