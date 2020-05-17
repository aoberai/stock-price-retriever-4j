package forex;

import com.crazzyghost.alphavantage.forex.response.ForexResponse;
import org.junit.Before;
import org.junit.Test;
import util.TestUtils;

import java.io.IOException;

import static org.junit.Assert.*;
import static util.TestUtils.error;
import static util.TestUtils.json;

public class ForexResponseTest {

    @Before
    public void setUp() {
        TestUtils.forDirectory("forex");
    }

    @Test
    public void testForexResponse() throws IOException {
        ForexResponse response = ForexResponse.of(json("daily"));
        assertNotNull(response.getMetaData());
        assertEquals(response.getForexUnits().size(), 2);
        assertTrue(response.toString().matches("(.*), errorMessage='null'(.*)"));
        assertNotEquals(response.getMetaData().getFromSymbol(), "");
        assertNotEquals(response.getMetaData().getInformation(), "");
        assertNotEquals(response.getMetaData().getInterval(), "");
        assertNotEquals(response.getMetaData().getLastRefreshed(), "");
        assertNotEquals(response.getMetaData().getOutputSize(), "");
        assertNotEquals(response.getMetaData().getTimeZone(), "");
        assertNotEquals(response.getMetaData().getToSymbol(), "");

    }

    @Test
    public void testForexResponseError() throws IOException {
        ForexResponse response = ForexResponse.of(error());
        assertNotNull(response.getErrorMessage());
        assertFalse(response.toString().matches("(.*), errorMessage='null'(.*)"));
    }
}