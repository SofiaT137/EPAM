package com.epam.jwd.dao.model;

public abstract class AbstractEntity<T> {
    protected T id;

    protected AbstractEntity() {

    }

    protected AbstractEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
