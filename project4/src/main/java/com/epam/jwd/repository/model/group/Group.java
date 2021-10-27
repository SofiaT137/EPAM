package com.epam.jwd.repository.model.group;

import com.epam.jwd.repository.model.AbstractEntity;

import java.util.Objects;

public class Group extends AbstractEntity<Integer> {

    private String name;

    public Group(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name)
                && Objects.equals(id,group.id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
