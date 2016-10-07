package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StockTest {

    Stock stock;

    @Before
    public void setUp() throws Exception {
        stock = new CommonStock(60, "C", 100L, 50L);

        long time = System.currentTimeMillis();
        stock.recordTrade(new Trade(time-100000, 200, true, 10)); // out of queue
        stock.recordTrade(new Trade(time-40, 100, true, 10)); // 1000
        stock.recordTrade(new Trade(time-30, 90, false, 20)); // 1800
        stock.recordTrade(new Trade(time-20, 80, true, 30));  // 2400
        stock.recordTrade(new Trade(time-10, 70, false, 40)); // 2800
        stock.recordTrade(new Trade(time, 50, true, 50));  // 2500
        //weight=10500, quantity=390
    }

    @Test(expected = StockCreationException.class)
    public void Stock() throws Exception {
        new CommonStock(-1, "X", 100L, 10L);
    }

    @Test
    public void getSymbol() throws Exception {
        assertEquals(stock.getSymbol(), "C");
    }

    @Test
    public void getParValue() throws Exception {
        assertEquals(stock.getParValue(), (Long) 100L);
    }

    @Test
    public void getLastDividend() throws Exception {
        assertEquals(stock.getLastDividend(), (Long) 50L);
    }

    @Test
    public void getVolumeWeightedPrice() throws Exception {
        assertEquals(stock.getVolumeWeightedPrice(), 10500./390, 0.0001);
    }

}