package com.epam.jwd.repository.connection_pool.main;


import com.epam.jwd.repository.impl.AccountDAO;
import com.epam.jwd.repository.impl.RoleDAO;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws InterruptedException, SQLException {

//        RoleDAO roleDAO = new RoleDAO();
//        roleDAO.save(roleDAO.createRole("HEADMAN"));

        AccountDAO accountDAO = new AccountDAO();
        accountDAO.save(accountDAO.createAccount("HEADMAN","Headman6","1116"));





    }


}
