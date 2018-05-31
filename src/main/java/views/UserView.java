package views;

public class UserView {

    public static void userExist() {
        System.out.println("Login is taken! Please try again.");
    }

    public static void noSuchUser() {
        System.out.println("No such user or wrong login/password");
    }

    public static void wrongPassword() {
        System.out.println("Wrong password");
    }

    public static void giveLogin() {
        System.out.println("Enter your login");
    }

    public static void givePassword() {
        System.out.println("Enter your password");
    }
}