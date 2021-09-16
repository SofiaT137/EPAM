package com.epam.jwd.project3.service.impl;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.composite.Composite;
import com.epam.jwd.project3.model.composite.Library;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.service.api.UserService;

import java.util.List;
import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {

    private User user;
    private Library library;
    private UserRepositoryImpl userRepository;

    public UserServiceImpl(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registration(User user) {
        userRepository.save(user);
    }

    @Override
    public User signIn(String userName) {
       return userRepository.findByUserName(userName);
    }

    @Override
    public void exchangeBooksWithAnotherUser(User other,Book requestBook) {
        List<Book> readerShelf = user.getReaderShelf();
        for (Book book : readerShelf) {
            if (book == requestBook && !(book.isAvailableToTakeHome())){
                readerShelf.remove(requestBook);
                other.getReaderShelf().add(requestBook);//можно ли ему добавить книгу
            }
        }
    }

    @Override
    public void takeTheBook(Book book) {
        if(user.getReaderShelf().size() >= 2){
//            throw new FullReaderShelfException("No place to take another book. Please,return some books back!");
            return;
        }
        user.getReaderShelf().add(book);
    }

    @Override
    public void returnTheBook(Book book) {
        if (!(user.getReaderShelf().contains(book))){
            throw new NoSuchElementException();
        }
        user.getReaderShelf().remove(book);
        List<Composite> shelf = library.getList();
        for (Composite books : shelf) {
            if (books.equals(book)) {
                Book currentBook = (Book) books;
                currentBook.setTaken(false);
            }
        }
    }

    @Override
    public void signOut() {
        user.setActive(false);
    }
}
