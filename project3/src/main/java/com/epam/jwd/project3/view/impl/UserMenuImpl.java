package com.epam.jwd.project3.view.impl;

import com.epam.jwd.project3.view.api.UserMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserMenuImpl implements UserMenu {

    private static final String MENU_FOR_START = """
            Select:
            1.Sign In
            2.Registration
            3.Exit
            """;

    private static final String MENU_FOR_MAIN_LOGIC = """
            Select:
            1.Take the book
            2.Go to the Reading Hall
            3.Return the book
            4.Sign out;
            """;


    public static String SELECT_THE_NUMBER = "Please, enter the action from menu:";
    public static String ENTER_THE_UNIQUE_NAME = "Enter the unique user name";
    public static String BOOK_BALANCE = "Your book balance : ";
    public static String CAN_TAKE = "You can take ";
    public static String BOOKS = " book(s). ";
    public static String SHELF_NUMBER = "\nPlease, enter the number of required shelf: ";
    public static String BOOK_NUMBER = "Please, enter the number of required book on shelf:";
    public static String BOOKS_FROM_READING_HALL = "Your books from Reading hall: ";
    public static String NUMBER_BOOK_FOR_EXCHANGE = "\nPlease, enter the number of required book for exchange:";
    public static String NUMBER_BOOK_EXCHANGE_HALL = "\nPlease, enter the number of required book from reading hall:";
    public static String YOUR_BOOKS = "Your books: ";
    public static String WHAT_ARE_DOING = "What are you doing here?, you have no book from reading hall. Please,take some books";
    public static String NOTHING_TO_EXCHANGE = "Sorry, nothing to exchange. Try again later.";
    public static String HOW_MUCH_SECONDS = "Enter the number of seconds after which you will return the book";
    public static String ENTER_THE_NUMBER = "Enter the NUMBER";

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void getStartMenu() {
        System.out.println(MENU_FOR_START);
    }
    public void getMainLogicMenu() {
        System.out.println(MENU_FOR_MAIN_LOGIC);
    }

    private int getNumber() throws IOException {
        int value = 0;
        try {
            value = Integer.parseInt(reader.readLine());
        }catch (NumberFormatException | IOException e){
            System.out.println(ENTER_THE_NUMBER);
           value = getNumber();
        }
        return value;
    }

    public int getRequiredNumber() throws IOException {
        System.out.println(SELECT_THE_NUMBER);
        return getNumber();
    }
    public String getUniqueForRegistrationName() throws IOException {
        System.out.println(ENTER_THE_UNIQUE_NAME);
        return reader.readLine();
    }

    public String getUniqueForEntranceName() throws IOException {
        System.out.println(ENTER_THE_UNIQUE_NAME);
        return reader.readLine();
    }

    public int getRequiredMainMenuNumber() throws IOException {
        System.out.println(SELECT_THE_NUMBER);
        return getNumber();
    }

    public int getShelfNumber() throws IOException {
        System.out.println(SHELF_NUMBER);
        return getNumber();
    }

    public int getBookNumber() throws IOException {
        System.out.println(BOOK_NUMBER);
        return getNumber();
    }
    public int getNumberBookForExchange() throws IOException {
        System.out.println(NUMBER_BOOK_FOR_EXCHANGE);
        return getNumber();
    }

    public int getNumberBookHallForExchange() throws IOException {
        System.out.println(NUMBER_BOOK_EXCHANGE_HALL);
        return getNumber();
    }

    public long getTime() throws IOException {
        System.out.println(HOW_MUCH_SECONDS);
        return (long) getNumber();
    }

}
