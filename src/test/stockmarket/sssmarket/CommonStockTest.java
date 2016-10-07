package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonStockTest {
    CommonStock stock;

    @Before
    public void setUp() throws Exception {
        stock = new CommonStock(60, "C", 100L, 50L);
    }

    @Test
    public void getDividendYield() throws Exception {
        assertEquals("Dividend: 50/10=5", stock.getDividendYield(10), (Double) 5.);
        assertNull("Dividend ful 0 price", stock.getDividendYield(0));
    }

    @Test
    public void getPeRatio() throws Exception {
        assertEquals("P/E Ratio 10/5=2: ", stock.getPeRatio(10), (Double) 2.);
    }



}