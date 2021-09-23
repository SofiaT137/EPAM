package com.epam.jwd.project3.view.validator;

import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.view.api.UserMenu;
import com.epam.jwd.project3.view.impl.UserMenuImpl;

import java.io.IOException;

public class UserMenuValidator implements UserMenu {

    private UserMenuImpl userMenu;
    private UserRepositoryImpl userRepository;

    private final static String CORRECT_NUMBER_NEEDED = "Enter the correct menu number(1,2,3) : ";
    private final static String CORRECT_NUMBER_MAIN_NEEDED = "Enter the correct menu number(1,2,3,4): ";
    private final static String CORRECT_NAME_NEEDED = "Enter the correct name( Name length must be greater than 2 letters)";
    private final static String CORRECT_UNIQUE_NAME_NEEDED = "This nickname is busy, please enter another name";
    private final static String CORRECT_SHELF_NEEDED = "Enter the correct shelf number(0,1,2,3,4): ";
    private final static String CORRECT_BOOK_NEEDED = "Enter the correct book number(0,1): ";

    public UserMenuValidator(UserMenuImpl userMenu,UserRepositoryImpl userRepository) {
        this.userMenu = userMenu;
        this.userRepository = userRepository;
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

    @Override
    public String getUniqueName() throws IOException {
        String name = this.userMenu.getUniqueName();
        while (name.length() <= 2){
            System.out.println(CORRECT_NAME_NEEDED);
            name = this.userMenu.getUniqueName();
        }

        while (userRepository.findByUserName(name)!= null){
            System.out.println(CORRECT_UNIQUE_NAME_NEEDED);
            name = this.userMenu.getUniqueName();
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
        while (shelfNumber < 0 || shelfNumber > 4){
            System.out.println(CORRECT_SHELF_NEEDED);
            shelfNumber =  this.userMenu.getShelfNumber();
        }
        return shelfNumber;
    }

    @Override
    public int getBookNumber() throws IOException {
        int bookNumber = this.userMenu.getBookNumber();
        while (bookNumber < 0 || bookNumber > 1){
            System.out.println(CORRECT_BOOK_NEEDED);
            bookNumber =  this.userMenu.getBookNumber();
        }
        return bookNumber;
    }

    @Override
    public int getNumberBookForExchange() throws IOException {
        int bookForExchangeNumber = this.userMenu.getNumberBookForExchange();
        while (bookForExchangeNumber < 0 || bookForExchangeNumber > 1){
            System.out.println(CORRECT_BOOK_NEEDED);
            bookForExchangeNumber =  this.userMenu.getNumberBookForExchange();
        }
        return bookForExchangeNumber;
    }

    @Override
    public int getNumberBookHallForExchange() throws IOException {
        return 0;
    }
}
