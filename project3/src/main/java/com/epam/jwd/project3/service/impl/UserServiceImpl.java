package com.epam.jwd.project3.service.impl;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.service.api.UserService;
import com.epam.jwd.project3.service.exception.FullReaderShelfException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Semaphore;

public class UserServiceImpl implements UserService {

    private User user;
    private UserRepositoryImpl userRepository;
    private Semaphore semaphore;

    private final String FULL_READER_SHELF = "No place to take another book. Please,return some books back!";

    public UserServiceImpl(UserRepositoryImpl userRepository, Semaphore semaphore) {

        this.userRepository = userRepository;
        this.semaphore = semaphore;
        this.user = null;
    }

    @Override
    public void registration(User user) {
        userRepository.save(user);
    }

    @Override
    public User signIn(String userName) throws InterruptedException {
       this.user = userRepository.findByUserName(userName);
       semaphore.acquire();
       return this.user;
    }

    @Override
    public void takeTheBook(Book book) throws FullReaderShelfException {
        if(user.getReaderShelf().size() >= 2){
            throw new FullReaderShelfException("No place to take another book. Please,return some books back!");
        }
        user.getReaderShelf().add(book);
    }

    @Override
    public void returnTheBook(Book book) {
        if (!(user.getReaderShelf().contains(book))){
            throw new NoSuchElementException("There is no book named: " + book.getName());
        }
        user.getReaderShelf().remove(book);
    }


    public List<Book> getBooksAvailableToExchange(){
      List<Book> readersBooks = user.getReaderShelf();
      List<Book> booksAvailableToExchange = new ArrayList<>();
        for (int i = 0; i < readersBooks.size(); i++) {
            if (!readersBooks.get(i).isAvailableToTakeHome()){
                booksAvailableToExchange.add(readersBooks.get(i));
            }
        }
        return booksAvailableToExchange;
    }

    public void printBooksAvailableToExchange(List<Book> booksAvailableToExchange){
        for (int i = 0; i < booksAvailableToExchange.size(); i++) {
            System.out.println("---#" + i + " " + booksAvailableToExchange.get(i));
        }
    }

    public User getUserForExchanging(Book book){
        List<User> listOfAllUser = userRepository.getUserStorage();
        for (User user : listOfAllUser) {
            List<Book> books = user.getReaderShelf();
            for (Book existBook : books) {
                if (existBook == book){
                    return user;
                }
            }
        }
        throw new NoSuchElementException("I can't find user named: " + user.getName());
    }

    @Override
    public void signOut() {
        this.user.setActive(false);
        this.user = null;
        semaphore.release();
    }


}
