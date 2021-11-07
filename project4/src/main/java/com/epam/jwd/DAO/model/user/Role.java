package com.epam.jwd.DAO.model.user;

import com.epam.jwd.DAO.model.AbstractEntity;

import java.util.Objects;

public class Role extends AbstractEntity<Integer> {

    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.getName()) && Objects.equals(id,role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}