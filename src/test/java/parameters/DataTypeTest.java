package parameters;

import com.crazzyghost.alphavantage.parameters.DataType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataTypeTest {

    @Test
    public void testJson() {
        assertEquals(DataType.JSON.toString(), "json");
    }

    @Test
    public void testCSV() {
        assertEquals(DataType.CSV.toString(), "csv");
    }

}
