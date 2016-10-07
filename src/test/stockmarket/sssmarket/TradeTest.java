package stockmarket.sssmarket;

import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class TradeTest {

    @Test(expectedExceptions = TradeCreationException.class)
    public void TradeTestConstructor() throws Exception {
        new Trade(-100, 100, true, 100);
        new Trade(100, 0, false, 100);
        new Trade(100, 100, true, -5);
    }

}