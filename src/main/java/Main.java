import controllers.AuctionController;
import controllers.UserController;
import exceptions.NoSuchUserException;
import exceptions.WrongPasswordException;
import helpers.FileHelper;
import models.Auction;
import models.TreeNode;
import models.User;
import views.AuctionView;
import views.TreeNodeView;
import views.UserView;

import java.util.*;

import static helpers.TreeNodeSetup.setupTree;
import static views.OtherViews.*;

public class Main {
    public enum State {
        INIT,
        REGISTER,
        LOGGING_IN,
        LOGGED_IN,
        EXIT
    }

    public static void main(String[] args) {
        State state = State.INIT;
        Scanner sc = new Scanner(System.in);
        UserController uc = new UserController();
        Map<String, User> users = new HashMap<>();
        final String PATHNAME_USERS = "src/main/resources/users.txt";
        final String PATHNAME_AUCTIONS = "src/main/resources/auctions.txt";
        User user = null;
        final TreeNode root = setupTree();

        Map<Integer, Auction> auctions = FileHelper.readFromFileAuction(PATHNAME_AUCTIONS);
        for (Auction auction : auctions.values()) {
            root.searchById(auction.getCategoryId()).addAuction(auction);
        }

        System.out.println("Welcome to SDAllegro!");
        while (state != State.EXIT) {
            switch (state) {
                case INIT:
                    users = FileHelper.readFromFile(PATHNAME_USERS);
                    initialMenuOptions();

                    String answer = sc.nextLine();

                    switch (answer) {
                        case ("1"):
                            state = State.LOGGING_IN;
                            break;

                        case ("2"):
                            state = State.REGISTER;
                            break;

                        case ("0"):
                            state = State.EXIT;
                            break;

                        default:
                            wrongAnswer();
                            break;
                    }
                    break;

                case REGISTER: {
                    UserView.giveLogin();
                    String login = sc.nextLine().trim();
                    if (uc.checkIfLoginPresent(login, users)) {
                        UserView.userExist();
                        break;
                    } else {
                        UserView.givePassword();
                        String password = sc.nextLine().trim();
                        User.addUser(login, password);
                        state = State.INIT;
                        break;
                    }
                }

                case LOGGING_IN: {
                    UserView.giveLogin();
                    String login = sc.nextLine();

                    UserView.givePassword();
                    String password = sc.nextLine();

                    try {
                        uc.verify(login, password, users);
                    } catch (NoSuchUserException e) {
                        UserView.noSuchUser();
                        state = State.INIT;
                        break;
                    } catch (WrongPasswordException e) {
                        UserView.wrongPassword();
                        state = State.INIT;
                        break;
                    }
                    user = new User(login, password);
                    state = State.LOGGED_IN;
                    break;
                }

                case LOGGED_IN: {
                    AuctionController ac = new AuctionController();
                    loggedInOptions();

                    answer = sc.nextLine();

                    switch (answer) {
                        case ("1"):
                            System.out.println();
                            AuctionView.viewAllAuctions(auctions);
                            System.out.println();
                            break;

                        case ("2"):
                            findingAuctionOptions();

                            answer = sc.nextLine();

                            switch (answer) {
                                case ("1"): {
                                    AuctionView.getAuctionByUser();
                                    String line = sc.nextLine();
                                    List<Auction> auctionsListByUser = AuctionController.getAuctionsByUser(line, auctions);
                                    AuctionView.printAuctionsBy(auctionsListByUser);
                                    break;
                                }

                                case ("2"): {
                                    AuctionView.getAuctionByAuctionName();
                                    String line = sc.nextLine();
                                    List<Auction> auctionsListByAuctionName = AuctionController.getAuctionsByAuctionName(line, auctions);
                                    AuctionView.printAuctionsBy(auctionsListByAuctionName);
                                    break;
                                }

                                case ("3"): {
                                    AuctionView.getAuctionByBeginningPrice();
                                    String beginningPrice = sc.nextLine();
                                    AuctionView.getAuctionByEndingPrice();
                                    String endingPrice = sc.nextLine();
                                    List<Auction> auctionsListByPrice =
                                            AuctionController.getAuctionsByPrice(beginningPrice, endingPrice, auctions);
                                    AuctionView.printAuctionsBy(auctionsListByPrice);
                                    break;
                                }

                                case ("4"): {
                                    Auction auction;
                                    AuctionView.getAuctionByAuctionId();
                                    String auctionId = sc.nextLine();
                                    auction = AuctionController.getAuctionById(Integer.parseInt(auctionId), auctions);
                                    System.out.println(auction);
                                    String price;

                                    assert user != null;
                                    if (auction.getSettingUser().equals(user.getLogin())) {
                                        System.out.println("U cannot bid your own auction");
                                        break;
                                    } else {
                                        System.out.println("To bid this auction enter your price");
                                        price = sc.nextLine();
                                        while (Integer.valueOf(price) <= auction.getPrice()) {
                                            System.out.println("Wrong price, try again");
                                            price = sc.nextLine();
                                        }
                                    }
                                    auction.setBiddingUser(user.getLogin());
                                    auction.setPrice(Integer.valueOf(price));
                                    FileHelper.overwriteAuctionById(auctionId, price, user.getLogin(), PATHNAME_AUCTIONS);

                                    break;
                                }

                                default: {
                                    wrongAnswer();
                                    break;
                                }
                            }
                            state = State.LOGGED_IN;
                            break;

                        case ("3"): {
                            AuctionView.giveAuctionName();
                            String auctionName = sc.nextLine();

                            AuctionView.giveAuctionDescription();
                            String auctionDescription = sc.nextLine();

                            AuctionView.givePrice();
                            int auctionPrice = Integer.valueOf(sc.nextLine());
                            int categoryId;
                            do {
                                AuctionView.getSpecificCategoryId();
                                categoryId = sc.nextInt();
                                String ignored = sc.nextLine();
                            } while (!root.searchById(categoryId).isLeaf());

                            assert user != null;
                            Auction auction =
                                    new Auction(auctionName, auctionDescription, user.getLogin(),
                                            auctionPrice, AuctionController.getAuctionNumber(), categoryId);
                            root.searchById(categoryId).addAuction(auction);

                            ac.addAuction(auctions, AuctionController.setAuctionNumber(), auction);
                            String input = FileHelper.toLine(auction);
                            FileHelper.writeToUsersFile(input, PATHNAME_AUCTIONS);
                            state = State.LOGGED_IN;
                            break;
                        }

                        case ("4"): {
                            TreeNodeView tnv = new TreeNodeView();
                            tnv.viewCategories(root);
                            System.out.println();
                            state = State.LOGGED_IN;
                            break;
                        }

                        case ("5"): {
                            AuctionView.getCategoryId();
                            try {
                                int categoryId = sc.nextInt();
                                TreeNode category = root.searchById(categoryId);
                                if (category == null) {
                                    category = root;
                                }
                                AuctionView.viewAuctions(category);
                            } catch (NullPointerException | InputMismatchException ignored) {
                            }
                            String ignored = sc.nextLine();

                            System.out.println();
                            state = State.LOGGED_IN;
                            break;
                        }

                        case ("0"):
                            state = State.EXIT;
                            break;

                        default:
                            wrongAnswer();
                            state = State.LOGGED_IN;
                            break;
                    }
                    break;
                }
                case EXIT:
                    break;
            }
        }
    }
}