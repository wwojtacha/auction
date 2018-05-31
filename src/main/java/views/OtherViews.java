package views;

public class OtherViews {
    public static void wrongAnswer() {
        System.out.println("Wrong answer!");
    }

    public static void initialMenuOptions() {
        System.out.println("Pick one:");
        System.out.println("1 - Log in");
        System.out.println("2 - Register");
        System.out.println("0 - Quit");
    }

    public static void findingAuctionOptions() {
        System.out.println("1 - By username");
        System.out.println("2 - By auction name");
        System.out.println("3 - By auction price");
        System.out.println("4 - By auction Id");
    }

    public static void loggedInOptions() {
        System.out.println("1 - View all auctions");
        System.out.println("2 - Find auction");
        System.out.println("3 - Create an auction");
        System.out.println("4 - View category tree");
        System.out.println("5 - View all auctions in category");
        System.out.println("0 - Quit");
    }
}
