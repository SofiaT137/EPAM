package com.epam.jwd.project3.model.user;

import com.epam.jwd.project3.model.composite.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String name;
    private boolean isActive;
    private List<Book> readerShelf;

    public User(String name,boolean isActive) {
        this.name = name;
        this.isActive = isActive;
        this.readerShelf = new ArrayList<>(2);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getReaderShelf() {
        return readerShelf;
    }
    public int getReaderShelfSize() {
        return readerShelf.size();
    }

    public void setReaderShelf(List<Book> readerShelf) {
        this.readerShelf = readerShelf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return isActive() == user.isActive() && getName().equals(user.getName()) && getReaderShelf().equals(user.getReaderShelf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), isActive(), getReaderShelf());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", isActive=" + isActive +
                ", readerShelf=" + readerShelf +
                '}';
    }

    public void showUserShelf(){
        if (readerShelf.size() == 0){
            return;
        }
        for (int i = 0; i < readerShelf.size(); i++) {
            Book book = readerShelf.get(i);
            System.out.println(i + book.toString());
        }
    }
}
