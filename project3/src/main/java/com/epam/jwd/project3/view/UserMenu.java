package com.epam.jwd.project3.view;

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

    public static void getStartMenu() {
        System.out.println(MENU_FOR_START);
    }
    public static void getMainLogicMenu() {
        System.out.println(MENU_FOR_MAIN_LOGIC);
    }

}
