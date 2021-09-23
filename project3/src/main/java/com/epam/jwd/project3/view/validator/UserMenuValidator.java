package com.epam.jwd.project3.view.validator;

import com.epam.jwd.project3.repository.api.UserRepository;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.view.api.UserMenu;

import java.io.IOException;
import java.sql.SQLOutput;

public class UserMenuValidator implements UserMenu {

    private UserMenu userMenu;
    private UserRepository userRepository;
    private BookService bookService;

    private final static String CORRECT_NUMBER_NEEDED = "Enter the correct menu number(1,2,3) : ";
    private final static String CORRECT_NUMBER_MAIN_NEEDED = "Enter the correct menu number(1,2,3,4): ";
    private final static String CORRECT_NAME_NEEDED = "Enter the correct name( Name length must be greater than 2 letters)";
    private final static String CORRECT_UNIQUE_NAME_NEEDED = "This nickname is taken, please enter another name";
    private final static String CORRECT_SHELF_NEEDED = "Enter the correct shelf number(0,1,2,3,4): ";
    private final static String CORRECT_BOOK_NEEDED = "Enter the correct book number(0,1): ";
    private final static String HALL_IS_EMPTY = "The Reading Hall is empty ";
    private final static String CORRECT_NUMBER_HALL_NEEDED = "Enter the correct book number from Reading hall: ";
    private final static String CANNOT_FIND_THIS_NAME = "Cannot find this name in the library";

    public UserMenuValidator(UserMenu userMenu, UserRepository userRepository, BookService service) {
        this.userMenu = userMenu;
        this.userRepository = userRepository;
        this.bookService = service;
    }
    @Override
    public void getStartMenu() {
        userMenu.getStartMenu();
    }

    @Override
    public void getMainLogicMenu() {
        userMenu.getMainLogicMenu();
    }

    @Override
    public int getRequiredNumber() throws IOException {
        int requiredNumber = this.userMenu.getRequiredNumber();
        while (requiredNumber < 1 || requiredNumber > 3){
            System.out.println(CORRECT_NUMBER_NEEDED);
            requiredNumber =  this.userMenu.getRequiredNumber();
        }
        return requiredNumber;
    }

    public String getUniqueForRegistrationName() throws IOException {
        String name = this.userMenu.getUniqueForRegistrationName();
        while (name.length() <= 2) {
            System.out.println(CORRECT_NAME_NEEDED);
            name = this.userMenu.getUniqueForRegistrationName();
        }
        while (true) {
            try {
                userRepository.findByUserName(name);
            } catch (NullPointerException exception) {
                //TODO logger
                break;
            }
            System.out.println(CORRECT_UNIQUE_NAME_NEEDED);
            name = getUniqueForRegistrationName();
        }
        return name;
    }

    public String getUniqueForEntranceName() throws IOException {

        String name = this.userMenu.getUniqueForEntranceName();
        while (name.length() <= 2) {
            System.out.println(CORRECT_NAME_NEEDED);
            name = this.userMenu.getUniqueForEntranceName();
        }
        try {
            userRepository.findByUserName(name);
        } catch (NullPointerException exception) {
            //TODO logger
            System.out.println();
            return "";
        }
        return name;
    }


    @Override
    public int getRequiredMainMenuNumber() throws IOException {
        int requiredMainNumber = this.userMenu.getRequiredMainMenuNumber();
        while (requiredMainNumber < 1 || requiredMainNumber > 4){
            System.out.println(CORRECT_NUMBER_MAIN_NEEDED);
            requiredMainNumber =  this.userMenu.getRequiredMainMenuNumber();
        }
        return requiredMainNumber;
    }

    @Override
    public int getShelfNumber() throws IOException {
       int shelfNumber = this.userMenu.getShelfNumber();
       //TODO check library size;
        while (shelfNumber < 0 || shelfNumber > 4){ //Hardcoded
            System.out.println(CORRECT_SHELF_NEEDED);
            shelfNumber =  this.userMenu.getShelfNumber();
        }
        return shelfNumber;
    }

    @Override
    public int getBookNumber() throws IOException {
        int bookNumber = this.userMenu.getBookNumber();
        //TODO check library shelf size;
        while (bookNumber < 0 || bookNumber > 1){ //Hardcoded
            System.out.println(CORRECT_BOOK_NEEDED);
            bookNumber =  this.userMenu.getBookNumber();
        }
        return bookNumber;
    }

    @Override
    public int getNumberBookForExchange() throws IOException {
        int bookForExchangeNumber = this.userMenu.getNumberBookForExchange();
        while (bookForExchangeNumber < 0 || bookForExchangeNumber > 1){ //User ought to have max 2 books
            System.out.println(CORRECT_BOOK_NEEDED);
            bookForExchangeNumber =  this.userMenu.getNumberBookForExchange();
        }
        return bookForExchangeNumber;
    }

    @Override
    public int getNumberBookHallForExchange() throws IOException {
        int bookForExchangeHall = this.userMenu.getNumberBookHallForExchange();
        int size = bookService.getReadingHall().size();

        if (size== 0) {
            System.out.println(HALL_IS_EMPTY);
            return -1;
        } else {
            while (bookForExchangeHall < 0 || bookForExchangeHall >= size) {
                System.out.println(CORRECT_NUMBER_HALL_NEEDED);
                bookForExchangeHall = this.userMenu.getNumberBookForExchange();
            }
            return bookForExchangeHall;
        }
    }
}
