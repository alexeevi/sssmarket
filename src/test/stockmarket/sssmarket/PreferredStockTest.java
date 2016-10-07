package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreferredStockTest {

    PreferredStock stock;

    @Before
    public void setUp() throws Exception {
        stock = new PreferredStock(60, "P", 100L, 50L, 2.);
    }

    @Test
    public void getDividendYield() throws Exception {
        assertEquals("Dividend: 2.*100/10=20", stock.getDividendYield(10), (Double) 20.);
    }

    @Test
    public void getFixedDividend() throws Exception {
        assertEquals(stock.getFixedDividend(), (Double) 2.);
    }

    @Test
    public void getPeRatio() throws Exception {
        assertEquals("P/E Ratio 10/20=0.5: ", stock.getPeRatio(10), (Double) 0.5);
    }
}