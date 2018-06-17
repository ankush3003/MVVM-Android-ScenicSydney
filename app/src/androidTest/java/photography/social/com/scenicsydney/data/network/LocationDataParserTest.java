package photography.social.com.scenicsydney.data.network;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.ui.main.MainActivity;

import static junit.framework.Assert.*;

/**
 * Test class for {@link LocationDataParser}
 *
 * @TestedClass LocationDataParser
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocationDataParserTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void testParseFromFile() {
        LocationDataParser locationDataParser = LocationDataParser.getInstance(mainActivityActivityTestRule.getActivity());
        LocationEntry[] parsedArray = locationDataParser.parseFromFile();

        // verify data from 'sample_data.json' file
        assertEquals(5, parsedArray.length);

        assertEquals("Milsons Point", parsedArray[0].getName());
    }
}
