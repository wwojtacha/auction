package helpers;

import models.Auction;

import java.util.Comparator;

public class AuctionByPriceComparator implements Comparator<Auction> {

    @Override
    public int compare(Auction o1, Auction o2) {
        return Integer.compare(o1.getPrice(), o2.getPrice());
    }
}
