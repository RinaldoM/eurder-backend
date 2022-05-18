package com.switchfully.order.domain;

public abstract class EntityValidator<T extends BaseEntity> {

    public boolean isValidForCreation(T entity) {
        return !isAFieldEmptyOrNull(entity) && entity.getId() == null;
    }

    public boolean isValidForUpdating(T entity) {
        return !isAFieldEmptyOrNull(entity) && entity.getId() != null;
    }

    protected abstract boolean isAFieldEmptyOrNull(T entity);

    protected boolean isEmptyOrNull(String attribute) {
        return attribute == null || attribute.isEmpty();
    }

    protected boolean isNull(Object object) {
        return object == null;
    }

    protected boolean isNotNull(Object object) {
        return object != null;
    }

    public void throwInvalidStateException(T entity, String type) {
        throw new IllegalStateException("Invalid " + (entity == null ? "NULL_ENTITY" : entity.getClass().getSimpleName())
                + " provided for " + type + ". Provided object: " + (entity == null ? null : entity.toString()));
    }
}
