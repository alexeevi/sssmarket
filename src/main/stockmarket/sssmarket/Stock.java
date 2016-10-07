package stockmarket.sssmarket;

import java.util.ArrayList;
import java.util.List;

abstract class Stock implements Tradeable {

    private String symbol;
    private Long parValue;
    private Long lastDividend;
    private List<Trade> trades = new ArrayList<Trade>();
    private TradesQueue tradesQueue;

    Stock(Integer queueTimeout, String symbol, Long parValue, Long lastDividend) throws StockCreationException {
        if (symbol == null || "".equals(symbol)) {
            throw new StockCreationException("Field symbol is null or empty");
        }
        if (queueTimeout == null || queueTimeout <= 0) {
            throw new StockCreationException("Field queueTimeout is null or negative");
        }
        this.tradesQueue = new TradesQueue(queueTimeout);
        this.symbol = symbol;
        this.parValue = parValue;
        this.lastDividend = lastDividend;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock that = (Stock) o;
        return symbol.equals(that.symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    String getSymbol() {
        return symbol;
    }

    Long getParValue() {
        return parValue;
    }

    Long getLastDividend() {
        return lastDividend;
    }

    void setLastDividend(long lastDividend) {
        this.lastDividend = lastDividend;
    }

    public Double getPeRatio(int price) {
        Double dividend = getDividendYield(price);
        return dividend == 0. ? null : price / dividend;
    }

    public void recordTrade(Trade trade) {
        trades.add(trade);
        tradesQueue.add(trade);
    }

    public Double getVolumeWeightedPrice() {
        return tradesQueue.getVolumeWeightedPrice();
    }

}
