package helpers;

import controllers.AuctionController;
import models.Auction;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHelper {
    private static final String SEPARATOR = ";";

    public static void writeToUsersFile(String text, String filePath) {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            writer.print(text + "\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void overwriteAuctionById(String id, String price, String biddingUser, String filePath) {
        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            List<String> list = new ArrayList<>();
            while (true) {

                line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    break;
                }

                for (String lines : line.split(SEPARATOR)) {
                    if (lines.contains(id)) {
                        list.add(line);
                    }
                }
                String auctionString = list.get(list.size() - 1);
                Auction auction = new Auction(parseEntry(auctionString)[1], parseEntry(auctionString)[2],
                        parseEntry(auctionString)[3], Integer.valueOf(parseEntry(auctionString)[5]),
                        Integer.valueOf(parseEntry(auctionString)[6]) - 1000, Integer.valueOf(parseEntry(auctionString)[7]));
                auction.setPrice(Integer.valueOf(price));
                auction.setBiddingUser(biddingUser);
                AuctionController.setAuctionNumber(Integer.valueOf(parseEntry(auctionString)[0]));
                String string = toLine(auction);
                writer.print(string + "\n");
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toLine(User user) {
        String result = "";
        result += user.getLogin() + SEPARATOR;
        result += user.getPassword();
        return result;
    }

    public static String toLine(Auction auction) {
        String result = "";
        result += AuctionController.getAuctionNumber() + SEPARATOR;
        result += auction.getName() + SEPARATOR;
        result += auction.getDescription() + SEPARATOR;
        result += auction.getSettingUser() + SEPARATOR;
        result += auction.getBiddingUser() + SEPARATOR;
        result += auction.getPrice() + SEPARATOR;
        result += auction.getAuctionId() + SEPARATOR;
        result += auction.getCategoryId();
        return result;
    }

    public static Map<String, User> readFromFile(String filepath) {
        File file = new File(filepath);
        Map<String, User> users = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    break;
                }
                User user = new User(parseEntry(line)[0], parseEntry(line)[1]);
                users.put(user.getLogin(), user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }


    public static Map<Integer, Auction> readFromFileAuction(String filepath) {
        File file = new File(filepath);
        Map<Integer, Auction> auctions = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    break;
                }
                Auction auction = new Auction(parseEntry(line)[1], parseEntry(line)[2],
                        parseEntry(line)[3], Integer.valueOf(parseEntry(line)[5]),
                        Integer.valueOf(parseEntry(line)[6]) - 1000, Integer.valueOf(parseEntry(line)[7]));
                auction.setBiddingUser(parseEntry(line)[4]);
                auctions.put(Integer.parseInt(parseEntry(line)[0]), auction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auctions;
    }

    public static Integer readBiggestAuctionNumber(String filepath) {
        File file = new File(filepath);
        int biggestNumber = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    bufferedReader.close();
                    break;
                }
                if (Integer.valueOf(parseEntry(line)[0]) > biggestNumber) {
                    biggestNumber = Integer.valueOf(parseEntry(line)[0]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return biggestNumber;
    }

    private static String[] parseEntry(String line) {
        return line.split(SEPARATOR);
    }
}
