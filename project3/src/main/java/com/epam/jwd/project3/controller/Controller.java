package com.epam.jwd.project3.controller;

import com.epam.jwd.project3.model.composite.Book;
import com.epam.jwd.project3.model.composite.Library;
import com.epam.jwd.project3.model.user.User;
import com.epam.jwd.project3.repository.impl.UserRepositoryImpl;
import com.epam.jwd.project3.service.api.BookService;
import com.epam.jwd.project3.service.api.UserService;
import com.epam.jwd.project3.service.impl.BookServiceImpl;
import com.epam.jwd.project3.service.impl.UserServiceImpl;
import com.epam.jwd.project3.view.UserMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {


    public static void main(String[] args) throws IOException {
        BookService service = new BookServiceImpl(new Library("Sofia's library"));
        User currentUser = null;

        service.createGoBooksList();
        service.createJavaBooksList();
        service.createJavaScriptBooksList();
        service.createRubyBooksList();
        service.createPythonBooksList();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());

        while(true) {

            UserMenu.getStartMenu();
            String requiredNumber = reader.readLine();

            if (requiredNumber.equals("2")) {
                System.out.println("Enter the unique user name");
                String name = reader.readLine();
                currentUser = new User(name, false);
                userService.registration(currentUser);
            } else if (requiredNumber.equals("1")) {
                System.out.println("Enter the unique user name");
                String name = reader.readLine();
                currentUser = userService.signIn(name);
            } else{
                break;
            }
            int currentUserBookShelfSize = currentUser.getReaderShelfSize();
            System.out.println("Your book balance : " + currentUserBookShelfSize);
            currentUser.showUserShelf();
            System.out.println("You can take " + (2 - currentUserBookShelfSize) + " book(s). ");

            UserMenu.getMainLogicMenu();

            String requiredNumber_2 = reader.readLine();

            if (requiredNumber_2.equals("1")) {
                service.printLibrary();
                System.out.println("Please, enter the number of required shelf and required book on it");
                //считать шелл индекс
               Book book =  service.getBookFromLibrary();
               userService.takeTheBook(book);

            }
            else if(requiredNumber_2.equals("2")){

            }
            else if (requiredNumber_2.equals("3")){

            }





        }

    }
}
