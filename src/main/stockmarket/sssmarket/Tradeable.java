package stockmarket.sssmarket;

interface Tradeable {

    Double getDividendYield(int price);
    Double getPeRatio(int price);
    void recordTrade(Trade trade);
    Double getVolumeWeightedPrice();

}
