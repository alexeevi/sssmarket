package stockmarket.sssmarket;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Deque;
import java.util.LinkedList;

import static stockmarket.sssmarket.SimpleMarket.FRACTIONS_PRECISION;

class TradesQueue {
    private BigInteger sum = BigInteger.ZERO;
    private BigInteger quantity = BigInteger.ZERO;
    private int timeLimit = 0;
    private int sizeThreshold = 0;
    private Deque<Trade> queue = new LinkedList<Trade>();
    private final Object lock = new Object();

    TradesQueue(int timeLimitSeconds) {
        this.timeLimit = (timeLimitSeconds < 1 ? 1 : timeLimitSeconds) * 1000;
        this.sizeThreshold = this.timeLimit;
    }

    private void clearOld() {
        long currentTime = System.currentTimeMillis();
        while (queue.size() > 0 && currentTime - queue.peekLast().getTimestamp() > timeLimit) {
            Trade last = queue.pollLast();
            sum = sum.subtract(last.getWeight());
            quantity = quantity.subtract(BigInteger.valueOf(last.getQuantity()));
        }
    }

    void add(Trade trade) {
        synchronized (lock) {
            queue.addFirst(trade);
            sum = sum.add(trade.getWeight());
            quantity = quantity.add(BigInteger.valueOf(trade.getQuantity()));

            if (queue.size() > sizeThreshold) {
                clearOld();
            }
        }
    }

    Double getVolumeWeightedPrice() {
        synchronized (lock) {
            if (queue.size() == 0) {
                return 1.;
            }
            clearOld();
            if (queue.size() == 0) {
                return 1.;
            }
            return new BigDecimal(sum).divide(new BigDecimal(quantity), FRACTIONS_PRECISION, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }
}
