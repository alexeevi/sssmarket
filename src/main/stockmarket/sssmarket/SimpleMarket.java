package stockmarket.sssmarket;

import java.util.*;

/**
 * <p>An object that keeps all data about the stock market.
 * Implements {@link Market} interface and provides "entry point" methods to the Super Simple Stock Market package:
 * <li>{@link #createCommonStock(String stockSymbol, Long parValue, Long lastDividend)}</li>
 * <li>{@link #createPreferredStock(String stockSymbol, Long parValue, Long lastDividend, Double fixedDividend)}</li>
 * <li>{@link #recordTrade(String stockSymbol, long quantity, boolean buyOrSell, long price)}</li>
 * <li>{@link #getDividendYield(String stockSymbol, int price)}</li>
 * <li>{@link #getPeRatio(String stockSymbol, int price)}</li>
 * <li>{@link #getVolumeWeightedPrice(String stockSymbol)}</li>
 * <li>{@link #getAllShareIndex()}</li>
 * </p>
 *
 * <p>The object is intended to live as singleton.</p>
 *
 * <p>Throws <tt>StockCreationException</tt> at the moment of creation of duplicated stocks or when some creation arguments are invalid (depending on stock type).
 * On the other hand, public methods do not throw any exception in case of using stock symbol, which doesn't exist in the market. Instead zero values are returned for such calls.</p>
 *
 * @author Ivan Alekseev
 */
public class SimpleMarket implements Market {

    // Number of fraction digits to be used in rounding operations after divisions
    static final int FRACTIONS_PRECISION = 4;

    // Duration of aggregation interval (seconds)
    static final int VOLUME_WEIGHTED_PRICE_PERIOD_SECONDS = 5 * 60;

    static private volatile SimpleMarket instance;
    private Map<String, Tradeable> stocks = Collections.synchronizedMap(new HashMap<String, Tradeable>());
    private Stock proxyStock;
    private final Object stocksLock = new Object();

    SimpleMarket() {
        try {
            proxyStock = new ProxyStock(VOLUME_WEIGHTED_PRICE_PERIOD_SECONDS, "Proxy", 0L, 0L);
        } catch (StockCreationException ex) {

        }
    }

    static public SimpleMarket getInstance() {
        if (instance == null) {
            synchronized (SimpleMarket.class) {
                if (instance == null) {
                    instance = new SimpleMarket();
                }
            }
        }
        return instance;
    }

    private Tradeable getStock(String stockSymbol) {
        Tradeable stock = stocks.get(stockSymbol);
        return stock != null ? stock : proxyStock;
    }

    private void checkForExistingSymbol(String stockSymbol) throws StockCreationException {
        if (stocks.get(stockSymbol) != null) {
            throw new StockCreationException("Stock with symbol already exists: " + stockSymbol);
        }
    }

    private void recordTrade(long timestamp, String stockSymbol, long quantity, boolean buyOrSell, long price) throws TradeCreationException {
        getStock(stockSymbol).recordTrade(new Trade(timestamp, quantity, buyOrSell, price));
    }


    /********** Entry Point methods below ***********/

    public void createCommonStock(String stockSymbol, Long parValue, Long lastDividend) throws StockCreationException {
        synchronized (stocksLock) {
            checkForExistingSymbol(stockSymbol);
            stocks.put(stockSymbol, new CommonStock(VOLUME_WEIGHTED_PRICE_PERIOD_SECONDS, stockSymbol, parValue, lastDividend));
        }
    }

    public void createPreferredStock(String stockSymbol, Long parValue, Long lastDividend, Double fixedDividend) throws StockCreationException {
        synchronized (stocksLock) {
            checkForExistingSymbol(stockSymbol);
            stocks.put(stockSymbol, new PreferredStock(VOLUME_WEIGHTED_PRICE_PERIOD_SECONDS, stockSymbol, parValue, lastDividend, fixedDividend));
        }
    }

    public void recordTrade(String stockSymbol, long quantity, boolean buyOrSell, long price) throws TradeCreationException {
        recordTrade(System.currentTimeMillis(), stockSymbol, quantity, buyOrSell, price);
    }

    public Double getDividendYield(String stockSymbol, int price) {
        return getStock(stockSymbol).getDividendYield(price);
    }

    public Double getPeRatio(String stockSymbol, int price) {
        return getStock(stockSymbol).getPeRatio(price);
    }

    public Double getVolumeWeightedPrice(String stockSymbol) {
        return getStock(stockSymbol).getVolumeWeightedPrice();
    }

    public Double getAllShareIndex() {
        if (stocks.size() == 0) {
            return 0.;
        }
        synchronized (stocksLock) {
            Double power = 1. / stocks.values().size();
            Double m = 1.;
            for (Tradeable stock : stocks.values()) {
                m *= Math.pow(stock.getVolumeWeightedPrice(), power);
            }
            return m;
        }
    }
}
