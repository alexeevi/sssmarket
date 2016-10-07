package stockmarket.sssmarket;

class CommonStock extends Stock {

    CommonStock(Integer queueTimeout, String symbol, Long parValue, Long lastDividend) throws StockCreationException {
        super(queueTimeout, symbol, parValue, lastDividend);
        if (lastDividend == null) {
            throw new StockCreationException("Field lastDividend is null");
        }
    }

    @Override
    public Double getDividendYield(int price) {
        return price <= 0 ? null : getLastDividend().doubleValue() / price;
    }

}
