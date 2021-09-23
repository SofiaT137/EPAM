package com.epam.jwd.project3.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserMenu {

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

    public void getStartMenu() {
        System.out.println(MENU_FOR_START);
    }
    public void getMainLogicMenu() {
        System.out.println(MENU_FOR_MAIN_LOGIC);
    }
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

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public int getRequiredNumber() throws IOException {
        System.out.println(SELECT_THE_NUMBER);
       return Integer.parseInt(reader.readLine());
    }

    public String getUniqueName() throws IOException {
        System.out.println(ENTER_THE_UNIQUE_NAME);
        return reader.readLine();
    }

    public int getRequiredMainMenuNumber() throws IOException {
        System.out.println(SELECT_THE_NUMBER);
        return Integer.parseInt(reader.readLine());
    }

    public int getShelfNumber() throws IOException {
        System.out.println(SHELF_NUMBER);
        return Integer.parseInt(reader.readLine());
    }

    public int getBookNumber() throws IOException {
        System.out.println(BOOK_NUMBER);
        return Integer.parseInt(reader.readLine());
    }
    public int getNumberBookForExchange() throws IOException {
        System.out.println(NUMBER_BOOK_FOR_EXCHANGE);
        return Integer.parseInt(reader.readLine());
    }

    public int getNumberBookHallForExchange() throws IOException {
        System.out.println(NUMBER_BOOK_EXCHANGE_HALL);
        return Integer.parseInt(reader.readLine());
    }

    
    
    

}
