package com.epam.jwd.project3.service.impl;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.composite.Composite;
import com.epam.jwd.project3.model.composite.Library;
import com.epam.jwd.project3.model.composite.Shelf;
import com.epam.jwd.project3.service.api.BookFactory;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.book_factory.GoBookFactory;
import com.epam.jwd.project3.service.book_factory.JavaBookFactory;
import com.epam.jwd.project3.service.book_factory.JavaScriptBookFactory;
import com.epam.jwd.project3.service.book_factory.RubyBookFactory;
import com.epam.jwd.project3.service.book_factory.PythonBookFactory;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements BookService {

    private Library library;

    public BookServiceImpl(Library library) {
        this.library = library;
    }

    @Override
    public void createGoBooksList() {
        BookFactory goFactory = new GoBookFactory();
        fillLibrary(getListOfBooks(goFactory,"Go"));
    }

    @Override
    public void createJavaBooksList() {
        BookFactory javaFactory = new JavaBookFactory();
        fillLibrary(getListOfBooks(javaFactory,"Java"));
    }

    @Override
    public void createJavaScriptBooksList() {
        BookFactory javaScriptFactory = new JavaScriptBookFactory();
        fillLibrary(getListOfBooks(javaScriptFactory,"JavaScript"));
    }

    @Override
        public void createPythonBooksList() {
        BookFactory pythonFactory = new PythonBookFactory();
        fillLibrary(getListOfBooks(pythonFactory,"Python"));
    }

    @Override
    public void createRubyBooksList() {
        BookFactory rubyFactory = new RubyBookFactory();
        fillLibrary(getListOfBooks(rubyFactory,"Ruby"));
    }

    @Override
    public void printLibrary() {
        System.out.println(library.getName());
        List<Composite> shelf_list = library.getList();
        for (int i = 0; i < shelf_list.size(); i++) {
            Composite shelf = shelf_list.get(i);
            System.out.println("#" + i + " "+shelf.getName());
            List<Composite> books = shelf.getList();
            for (int j = 0; j < books.size(); j++) {
                Book book = (Book) books.get(j);
                if (!book.isTaken()){
                    System.out.println("---#" + j +" "+ book);
                }
            }
        }
    }

    @Override
    public List<Book> getReadingHall(){
        List<Book> readingHall = new ArrayList<>();
        List<Composite> shelf_list = library.getList();
        for (int i = 0; i < shelf_list.size(); i++) {
            Composite shelf = shelf_list.get(i);
            List<Composite> books = shelf.getList();
            for (int j = 0; j < books.size(); j++) {
                Book book = (Book) books.get(j);
                if (!book.isAvailableToTakeHome() && book.isTaken()){
                    readingHall.add(book);
                }
            }
        }
        return readingHall;
    }

    public synchronized void printHall(List<Book> getReadingHall,List<Book> getUsersBooks) {
        for (int i = 0; i < getReadingHall.size(); i++) {
            for (int j = 0; j < getUsersBooks.size(); j++) {
                if (!getReadingHall.get(i).equals(getUsersBooks.get(j))){
                    System.out.println("---#" + i +" "+ getReadingHall.get(i));
                }
            }
        }
    }

    public Book getBookFromReadingHall(List<Book> getReadingHall,int requiredNumber){
        return getReadingHall.get(requiredNumber);
    }

    private void fillLibrary(Composite shelf) {
        library.add(shelf);
    }


    private Composite getListOfBooks(BookFactory bookFactory,String shelfName) {
        Shelf shelf = new Shelf(shelfName);
        shelf.add(bookFactory.createReadingRoomBook());
        shelf.add(bookFactory.createTakingHomeBook());
        return shelf;
    }

    @Override
    public Book getBookFromLibrary(int shellIndex, int bookIndex){
        Book book = (Book) library.getList().get(shellIndex).getList().get(bookIndex);
        book.setTaken(true);
        return book;
    }

    @Override
    public void returnBookToLibrary(Book bookToReturn){
        List<Composite> libraryList = library.getList();
        for (Composite shelf : libraryList) {
            List<Composite> books_list = shelf.getList();
            for (Composite composite : books_list) {
                Book book = (Book) composite;
                if (book == bookToReturn){
                    book.setTaken(false);
                }
            }
        }
    }
}
