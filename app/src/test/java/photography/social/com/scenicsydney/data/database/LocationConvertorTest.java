package photography.social.com.scenicsydney.data.database;


import android.location.Location;

import org.junit.Test;
import org.mockito.Mock;


import static junit.framework.Assert.*;

/**
 * Local unit test for {@link LocationConvertor} class.
 *
 * @TestedClasses LocationConvertor
 */

public class LocationConvertorTest {

    @Test
    public void testFromLocation() {
        Location testLocation = null;

        // fromLocation should return null when location is null
        assertNull(LocationConvertor.fromLocation(testLocation));
    }

    @Test
    public void testToLocation() {
        String testLocation = null;

        // fromLocation should return null when location is null
        assertNull(LocationConvertor.toLocation(testLocation));
    }
}
