package parameters;

import com.crazzyghost.alphavantage.parameters.OutputSize;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OutputSizeTest {

    @Test
    public void testCompact() {
        assertEquals(OutputSize.COMPACT.toString(), "compact");
    }

    @Test
    public void testFull() {
        assertEquals(OutputSize.FULL.toString(), "full");
    }
}