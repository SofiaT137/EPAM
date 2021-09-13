package com.epam.jwd.project3.model;

import java.util.Objects;

public class Book extends Composite{

    private String author;
    private int yearOfPublishing;
    private int pageCount;
    private boolean isAvailableToTakeHome;
    private boolean isTaken;

    public Book(String name,String author, int yearOfPublishing, int pageCount, boolean isAvailableToTakeHome) {
        this.name = name;
        this.author = author;
        this.yearOfPublishing = yearOfPublishing;
        this.pageCount = pageCount;
        this.isAvailableToTakeHome = isAvailableToTakeHome;
        this.isTaken = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublishing() {
        return yearOfPublishing;
    }

    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isAvailableToTakeHome() {
        return isAvailableToTakeHome;
    }

    public void setAvailableToTakeHome(boolean availableToTakeHome) {
        isAvailableToTakeHome = availableToTakeHome;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return getYearOfPublishing() == book.getYearOfPublishing() && getPageCount() == book.getPageCount() && isAvailableToTakeHome() == book.isAvailableToTakeHome() && getName().equals(book.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getYearOfPublishing(), getPageCount(), isAvailableToTakeHome());
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublishing=" + yearOfPublishing +
                ", pageCount=" + pageCount +
                ", isAvailableToTakeHome=" + isAvailableToTakeHome +
                ", isTaken=" + isTaken +
                '}';
    }
}
