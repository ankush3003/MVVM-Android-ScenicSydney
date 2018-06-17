package photography.social.com.scenicsydney.utils;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;
import photography.social.com.scenicsydney.ui.detail.DetailActivityViewModelFactory;
import photography.social.com.scenicsydney.ui.main.MainActivityViewModelFactory;

import static junit.framework.Assert.*;

/**
 * Test class for {@link InjectorUtils}
 *
 * @TestedClass InjectorUtils
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class InjectorUtilsTest extends AndroidJUnitRunner{

    @Test
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("photography.social.com.scenicsydney", appContext.getPackageName());
    }

    @Test
    public void testProvideRepository() {
        // verify repository creation
        ScenicSydneyRepository scenicSydneyRepository = InjectorUtils.provideRepository(InstrumentationRegistry.getTargetContext());
        assertNotNull(scenicSydneyRepository);

        // test singleton
        assertEquals(scenicSydneyRepository, InjectorUtils.provideRepository(InstrumentationRegistry.getTargetContext()));
    }

    @Test
    public void testProvideDetailActivityViewModelFactory() {
        DetailActivityViewModelFactory instance = InjectorUtils.provideDetailActivityViewModelFactory(InstrumentationRegistry.getTargetContext());
        assertNotNull(instance);
    }

    @Test
    public void testProvideMainActivityViewModelFactory() {
        MainActivityViewModelFactory instance = InjectorUtils.provideMainActivityViewModelFactory(InstrumentationRegistry.getTargetContext());
        assertNotNull(instance);
    }
}
