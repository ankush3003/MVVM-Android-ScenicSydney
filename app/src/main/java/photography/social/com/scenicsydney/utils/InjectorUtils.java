package photography.social.com.scenicsydney.utils;

import android.content.Context;

import photography.social.com.scenicsydney.AppExecutors;
import photography.social.com.scenicsydney.data.ScenicSydneyRepository;
import photography.social.com.scenicsydney.data.database.LocationDatabase;
import photography.social.com.scenicsydney.data.network.LocationDataParser;
import photography.social.com.scenicsydney.ui.detail.DetailActivityViewModelFactory;
import photography.social.com.scenicsydney.ui.main.MainActivityViewModelFactory;

/**
 * Provides static methods to inject the various classes
 */
public class InjectorUtils {

    /**
     * Provides Repository - which handles DB/Network APIs. {@link ScenicSydneyRepository}
     *
     * @param context context
     * @return ScenicSydneyRepository
     */
    public static ScenicSydneyRepository provideRepository(Context context) {
        LocationDatabase database = LocationDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        LocationDataParser locationDataParser = LocationDataParser.getInstance(context);
        return ScenicSydneyRepository.getInstance(database.locationDao(), locationDataParser, executors);
    }

    /**
     * Provided DetailActivityViewModelFactory
     *
     * @param context context
     * @return DetailActivityViewModelFactory
     */
    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context) {
        ScenicSydneyRepository repository = provideRepository(context.getApplicationContext());
        return new DetailActivityViewModelFactory(repository);
    }

    /**
     * Provided MainActivityViewModelFactory
     *
     * @param context context
     * @return MainActivityViewModelFactory
     */
    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        ScenicSydneyRepository repository = provideRepository(context);
        return new MainActivityViewModelFactory(repository);
    }

}