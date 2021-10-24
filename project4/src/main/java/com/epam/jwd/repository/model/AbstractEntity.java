package com.epam.jwd.repository.model;

public abstract class AbstractEntity<T> {
    protected T id;

    public AbstractEntity() {

    }

    public AbstractEntity(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
