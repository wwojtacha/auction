package models;

import helpers.FileHelper;

public class User {
    private static FileHelper fc = new FileHelper();
    private String login;
    private String password;
    private static final String PATHNAME = "src/main/resources/users.txt";

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static void addUser(String login, String password) {
        User user = new User(login, password);

        String input = fc.toLine(user);
        FileHelper.writeToUsersFile(input, PATHNAME);
    }
}
