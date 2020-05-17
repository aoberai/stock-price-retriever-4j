package parameters;

import com.crazzyghost.alphavantage.parameters.SeriesType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeriesTypeTest {

    @Test
    public void testHigh() {
        assertEquals(SeriesType.HIGH.toString(), "high");
    }

    @Test
    public void testLow() {
        assertEquals(SeriesType.LOW.toString(), "low");
    }

    @Test
    public void testOpen() {
        assertEquals(SeriesType.OPEN.toString(), "open");
    }

    @Test
    public void testClose() {
        assertEquals(SeriesType.CLOSE.toString(), "close");
    }
}
