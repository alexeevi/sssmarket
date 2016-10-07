package stockmarket.sssmarket;

import java.math.BigInteger;

class Trade {
    private long timestamp;
    private long quantity;
    private boolean buyOrSell;
    private long price;
    private BigInteger weight;

    Trade(long timestamp, long quantity, boolean buyOrSell, long price) throws TradeCreationException {
        if (timestamp <= 0) {
            throw new TradeCreationException("Field timestamp is not positive");
        }
        if (quantity <= 0) {
            throw new TradeCreationException("Field quantity is not positive");
        }
        if (price <= 0) {
            throw new TradeCreationException("Field price is not positive");
        }
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.buyOrSell = buyOrSell;
        this.price = price;
        this.weight = BigInteger.valueOf(quantity).multiply(BigInteger.valueOf(price));
    }

    long getTimestamp() {
        return timestamp;
    }

    long getQuantity() {
        return quantity;
    }

    boolean isBuyOrSell() {
        return buyOrSell;
    }

    long getPrice() {
        return price;
    }

    BigInteger getWeight() {
        return weight;
    }
}
