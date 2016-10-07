package stockmarket.sssmarket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TradesQueueTest {

    TradesQueue queue;

    @Before
    public void setUp() throws Exception {
        queue = new TradesQueue(1);
        long time = System.currentTimeMillis();
        queue.add(new Trade(time-3000, 50, true, 500)); //excluded
        queue.add(new Trade(time-2000, 40, true, 400)); //excluded
        queue.add(new Trade(time-1000, 30, true, 300)); //at the edge but included
        queue.add(new Trade(time, 20, true, 200));
    }

    @Test
    public void getVolumeWeightedPrice() throws Exception {
        assertEquals(queue.getVolumeWeightedPrice(), 260., 0.);
        assertEquals(new TradesQueue(1).getVolumeWeightedPrice(), 1., 0.);
    }

    @Test(timeout = 100)
    public void addMany() throws Exception {
        TradesQueue q = new TradesQueue(1);
        for (int i=0; i<1000; i++) {
            q.add(new Trade(System.currentTimeMillis(), 1000000, false, 90000000));
        }
        assertEquals(q.getVolumeWeightedPrice() , 90000000., 0.);
    }

}