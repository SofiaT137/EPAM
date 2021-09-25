package com.epam.jwd.project3.view.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public interface UserMenu {

    public void getStartMenu();
    public void getMainLogicMenu();
    public int getRequiredNumber() throws IOException;
    public String getUniqueForRegistrationName() throws IOException;
    public String getUniqueForEntranceName() throws  IOException;
    public int getRequiredMainMenuNumber() throws IOException;
    public int getShelfNumber() throws IOException;
    public int getBookNumber() throws IOException;
    public int getNumberBookForExchange() throws IOException;
    public int getNumberBookHallForExchange() throws IOException;
    public long getTime() throws IOException;
}
