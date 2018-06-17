package photography.social.com.scenicsydney.data;

import android.location.Location;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;

import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.ui.main.MainActivity;
import photography.social.com.scenicsydney.utils.InjectorUtils;

import static junit.framework.Assert.*;

/**
 * Test class for {@link ScenicSydneyRepository}
 *
 * @TestedClass ScenicSydneyRepository
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ScenicSydneyRepositoryTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Tests all methods in ScenicSydneyRepository
     */
    @Test
    public void testScenicSydneyRepository() {
        ScenicSydneyRepository repository = InjectorUtils.provideRepository(mainActivityActivityTestRule.getActivity());
        sleepInSeconds(2);

        // verify ScenicSydneyRepository.getLocationEntry()
        Location location = new Location("");
        location.setLatitude(-33.850750);
        location.setLongitude(151.212519);
        repository.getLocationEntry(location).observe(mainActivityActivityTestRule.getActivity(), locationEntry -> {
            assertEquals(locationEntry.getLocation().getLatitude(), location.getLatitude());
            assertEquals(locationEntry.getLocation().getLongitude(), location.getLongitude());
        });

        // verify ScenicSydneyRepository.getLocations()
        repository.getLocations().observe(mainActivityActivityTestRule.getActivity(), locationEntries -> {
            boolean found = false;
            for (LocationEntry locationEntry: locationEntries) {
                if (locationEntry.getName().equalsIgnoreCase("Milsons Point")) {
                    found = true;
                }
            }
            assertTrue(found);
        });
    }

    private void sleepInSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
