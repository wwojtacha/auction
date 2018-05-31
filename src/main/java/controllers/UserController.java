package controllers;

import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import models.User;

import java.util.Map;

public class UserController {

    public boolean checkIfLoginPresent(String string, Map<String, User> users) {
        return users.containsKey(string);
    }

    public boolean checkIfLoginAndPasswordAreConnected(String login, String password, Map<String, User> users) {
        boolean isConnected = false;
        try {
            isConnected = users.get(login).getPassword().equals(password);
        } catch (NullPointerException npe) {
            System.out.println("Login is not present.");
        }

        return isConnected;
    }

    public void verify(String login, String password, Map<String, User> users) throws NoSuchUserException, WrongPasswordException {

        //zmienic na jeden wyjatek, zeby nie bylo widac, ze konto istnieje
       if(!checkIfLoginPresent(login,users)){
           throw new NoSuchUserException();
       }
       if (!checkIfLoginAndPasswordAreConnected(login, password, users)) {
            throw new WrongPasswordException();
        }
    }
}