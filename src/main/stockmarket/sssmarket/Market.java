package stockmarket.sssmarket;

import java.math.BigDecimal;

/**
 * Interface of an instance that keeps all data about the stock market.
 */

public interface Market {

    /**
     * Create stock of "Common" type.
     * @param stockSymbol  string symbol, name to be assigned as an ID of new stock
     * @param parValue     numeric "par value" (in pennies) of new stock
     * @param lastDividend numeric value (in pennies) of last dividend of new stock
     * @throws StockCreationException if stock with same symbol already exists or values of given arguments are not correct.
     */
    void createCommonStock(String stockSymbol, Long parValue, Long lastDividend) throws StockCreationException;

    /**
     * Create stock of "Preferred" type.
     * @param stockSymbol   string symbol, name to be assigned as an ID of new stock
     * @param parValue      numeric "par value" of new stock
     * @param lastDividend  numeric value (in pennies) of last dividend of new stock
     * @param fixedDividend numeric value (as percentage) of fixed dividend
     * @throws StockCreationException if stock with same symbol already exists or values of given arguments are not correct
     */
    void createPreferredStock(String stockSymbol, Long parValue, Long lastDividend, Double fixedDividend) throws StockCreationException;

    /**
     * Register a new trade for a given stock.
     * @param stockSymbol symbol of stock
     * @param quantity    quantity of traded stocks
     * @param buyOrSell   buy (true) or sell (false) flag
     * @param price       price (in pennies) for once stock
     * @throws TradeCreationException if parameters of trade are invalid (typically negative)
     */
    void recordTrade(String stockSymbol, long quantity, boolean buyOrSell, long price) throws TradeCreationException;

    /**
     * Get Dividend Yield
     * @param stockSymbol symbol of stock
     * @param price       offered price (in pennies) for one stock
     * @return dividend yield depending on offered price and type of stock, or null when price is zero
     */
    Double getDividendYield(String stockSymbol, int price);

    /**
     * Get Price-Earnings Ratio
     * @param stockSymbol symbol of stock
     * @param price       offered price (in pennies) for one stock
     * @return Price-Earnings Ratio depending on offered price and type of stock, or null when earnings component is zero.
     */
    Double getPeRatio(String stockSymbol, int price);

    /**
     * Get Volume Weighted Stock Price based on trades in past <tt>VOLUME_WEIGHTED_PRICE_PERIOD_SECONDS</tt> seconds
     * @param stockSymbol symbol of stock
     * @return volume weighted stock price
     */
    Double getVolumeWeightedPrice(String stockSymbol);

    /**
     * Get market's All Share Index as geometric mean of the Volume Weighted Stock Price for all stocks
     * @return all share index
     */
    Double getAllShareIndex();
}
