package stockmarket.sssmarket;

import java.math.BigDecimal;

class ProxyStock extends Stock {

    ProxyStock(Integer queueTimeout, String symbol, Long parValue, Long lastDividend) throws StockCreationException {
        super(queueTimeout, symbol, 0L, 0L);
    }

    @Override
    public Double getDividendYield(int price) {
        return 0.;
    }

    @Override
    public Double getVolumeWeightedPrice() {
        return 0.;
    }

    @Override
    public void recordTrade(Trade trade) {

    }

    @Override
    public Double getPeRatio(int price) {
        return 0.;
    }

    @Override
    void setLastDividend(long lastDividend) {

    }
}
