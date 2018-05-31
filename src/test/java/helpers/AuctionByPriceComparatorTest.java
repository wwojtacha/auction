package helpers;

import models.Auction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuctionByPriceComparatorTest {

    @Test
    public void compareIfPrice1HigherThanPrice2() {
        Auction a1 = new Auction("name1", "description1", "user1", 300, 123, 11);
        Auction a2 = new Auction("name2", "description2", "user2", 150, 456, 11);

        AuctionByPriceComparator abpc = new AuctionByPriceComparator();
        int actual = abpc.compare(a1, a2);

        assertThat(actual).isEqualTo(1);

    }
}
