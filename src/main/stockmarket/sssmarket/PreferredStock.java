package stockmarket.sssmarket;

class PreferredStock extends Stock {

    private Double fixedDividend;

    PreferredStock(Integer queueTimeout, String symbol, Long parValue, Long lastDividend, Double fixedDividend) throws StockCreationException {
        super(queueTimeout, symbol, parValue, lastDividend);
        if (fixedDividend == null) {
            throw new StockCreationException("Field fixedDividend is null");
        }
        if (parValue == null) {
            throw new StockCreationException("Field parValue is null");
        }
        this.fixedDividend = fixedDividend;
    }

    @Override
    public Double getDividendYield(int price) {
        return price <= 0 ? null : fixedDividend * getParValue() / price;
    }

    public Double getFixedDividend() {
        return fixedDividend;
    }
}
