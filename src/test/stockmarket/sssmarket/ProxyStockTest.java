package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyStockTest {
    ProxyStock stock;

    @Before
    public void setUp() throws Exception {
        stock = new ProxyStock(60, "C", 100L, 50L);
    }

    @Test
    public void getDividendYield() throws Exception {
        assertEquals(stock.getDividendYield(10), 0., 0.);
    }

    @Test
    public void getVolumeWeightedPrice() throws Exception {
        assertEquals(stock.getVolumeWeightedPrice(), 0., 0.);
    }

    @Test
    public void getPeRatio() throws Exception {
        assertEquals(stock.getPeRatio(10), 0., 0.);
    }

    @Test
    public void setLastDividend() throws Exception {
        stock.setLastDividend(123);
        assertEquals(stock.getLastDividend(), (Long) 0L);
    }

}