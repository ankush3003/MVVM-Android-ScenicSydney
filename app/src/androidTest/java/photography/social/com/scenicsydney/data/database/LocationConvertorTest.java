package photography.social.com.scenicsydney.data.database;

import android.location.Location;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.*;

/**
 * Test class for {@link LocationConvertor}
 *
 * @TestedClass LocationConvertor
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class LocationConvertorTest {

    @Test
    public void testFromLocation() {
        Location testLocation = null;

        // fromLocation should return null when location is null
        assertNull(LocationConvertor.fromLocation(testLocation));

        // verify returned value
        testLocation = new Location("");
        testLocation.setLatitude(-33.100000);
        testLocation.setLongitude(151.100000);

        assertEquals("-33.100000,151.100000", LocationConvertor.fromLocation(testLocation));
    }

    @Test
    public void testToLocation() {
        String testLocation = null;

        // fromLocation should return null when location is null
        assertNull(LocationConvertor.toLocation(testLocation));

        // verify returned value
        String value = "-33.100000,151.100000";
        Location location = LocationConvertor.toLocation(value);
        assertEquals(location.getLatitude(), -33.100000);
        assertEquals(location.getLongitude(), 151.100000);
    }
}
