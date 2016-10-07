package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleMarketTest{

    private Market market = new SimpleMarket();

    @Before
    public void setUp() throws Exception {

        market.createCommonStock("TEA", 100L, 0L);
        //market.createCommonStock("POP", 100L, 8L);
        //market.createCommonStock("ALE", 60L, 23L);
        market.createPreferredStock("GIN", 100L, 8L, 2.);
        //market.createCommonStock("JOE", 250L, 13L);

        market.recordTrade("TEA", 50, true, 90);
        market.recordTrade("TEA", 40, true, 80);
        market.recordTrade("TEA", 10, true, 120);
        //89

        market.recordTrade("GIN", 10, true, 90);
        market.recordTrade("GIN", 50, true, 80);
        market.recordTrade("GIN", 40, true, 120);

    }

    @Test(expected = StockCreationException.class)
    public void createDuplicateStock() throws Exception {
        market.createCommonStock("TEA", 200L, 10L);
    }

    @Test
    public void workWithUnknown() throws Exception {
        assertEquals(market.getDividendYield("BOO", 123), 0., 0.);
        assertEquals(market.getPeRatio("BOO", 123), 0., 0.);
        assertEquals(market.getVolumeWeightedPrice("BOO"), 0., 0.);
    }

    @Test
    public void getDividendYield() throws Exception {
        assertEquals(market.getDividendYield("TEA", 100), 0., 0.);
        assertEquals(market.getDividendYield("GIN", 50), 4., 0.);
    }

    @Test
    public void getPeRatio() throws Exception {
        assertNull(market.getPeRatio("TEA", 100));
        assertEquals(market.getPeRatio("GIN", 50), 12.5, 0);
    }

    @Test
    public void getVolumeWeightedPrice() throws Exception {
        assertEquals(market.getVolumeWeightedPrice("TEA"), (Double) 89.);
        assertEquals(market.getVolumeWeightedPrice("GIN"), (Double) 97.);
    }

    @Test
    public void getAllShareIndex() throws Exception {
        assertEquals(market.getAllShareIndex(), 92.9139, 0.0001);
    }

    @Test
    public void getAllShareIndexTimeout() throws Exception {
        long start = System.currentTimeMillis();
        int repeats = 100;
        int threads = 100;
        int trades = 1;
        Thread[] generators = new Thread[threads];

        for (int j=0; j<repeats; j++) {
            for (int i = 0; i < threads; i++) {
                generators[i] = new StockGenerator(j*threads + i, market, Math.random() < 0.5 ? 0 : 1, trades);
                generators[i].start();
            }
            for (int i = 0; i < threads; i++) {
                generators[i].join();
            }
        }

        System.out.println("Generation took " + (System.currentTimeMillis() - start) + " ms");

        long end = System.currentTimeMillis();
        double index = market.getAllShareIndex();
        long time = System.currentTimeMillis() - end;
        System.out.println("getAllShareIndex took " + time + " ms. Result is " + index);

        assertNotEquals(index, 0.);
        assertTrue("Time of calculation of getAllShareIndex is too long: " + time + "ms", time < 100);
    }


    private class StockGenerator extends Thread {

        Market market;
        String symbol;
        int maxPrices;
        int stockType;

        StockGenerator(Integer id, Market market, int stockType, int maxPrices) {
            this.market = market;
            this.maxPrices = maxPrices;
            this.stockType = stockType;
            symbol = id.toString();
        }

        @Override
        public void run() {
            try {
                //System.out.println("Created generator " + symbol);
                if (stockType == 0) {
                    market.createCommonStock(symbol, new Double(10000 * Math.random()).longValue(), new Double(10000 * Math.random()).longValue());
                } else {
                    market.createPreferredStock(symbol, new Double(10000 * Math.random()).longValue(), new Double(10000 * Math.random()).longValue(), 100 * Math.random());
                }
                //System.out.println("Initialized generator " + symbol);

                for (int i=0; i < maxPrices; i++) {
                    try {
                        market.recordTrade(symbol, new Double(10000 * Math.random()).intValue(), true, new Double(10000 * Math.random()).intValue());
                    } catch (TradeCreationException tex) {

                    }
                }

            } catch (StockCreationException ex) {

            }

            //System.out.println("Generator done " + symbol);
        }
    }

}