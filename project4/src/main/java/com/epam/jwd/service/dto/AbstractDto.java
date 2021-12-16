package com.epam.jwd.service.dto;

/**
 * The abstractDto creation class
 * @param <T>
 */
public class AbstractDto<T>{
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
