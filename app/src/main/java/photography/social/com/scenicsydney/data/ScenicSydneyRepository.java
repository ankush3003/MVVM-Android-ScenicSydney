package photography.social.com.scenicsydney.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import photography.social.com.scenicsydney.AppExecutors;
import photography.social.com.scenicsydney.data.database.LocationDao;
import photography.social.com.scenicsydney.data.database.LocationEntry;

/**
 * Handles data operations in project.
 */
public class ScenicSydneyRepository {
    private static final String TAG = ScenicSydneyRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ScenicSydneyRepository sInstance;
    private final LocationDao mLocationDao;
    private final WeatherNetworkDataSource mWeatherNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private ScenicSydneyRepository(LocationDao locationDao,
                                   WeatherNetworkDataSource weatherNetworkDataSource,
                                   AppExecutors executors) {
        mLocationDao = locationDao;
        mWeatherNetworkDataSource = weatherNetworkDataSource;
        mExecutors = executors;

        LiveData<WeatherEntry[]> networkData = mWeatherNetworkDataSource.getCurrentWeatherForecasts();
        networkData.observeForever(newForecastsFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Insert our new weather data into Sunshine's database
                mWeatherDao.bulkInsert(newForecastsFromNetwork);
                Log.d(TAG, "New values inserted");
            });
        });
    }

    public synchronized static ScenicSydneyRepository getInstance(
            LocationDao locationDao, WeatherNetworkDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ScenicSydneyRepository(weatherDao, weatherNetworkDataSource,
                        executors);
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    public synchronized void initializeData() {
        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {//CODE ON DISK I/O THREAD HERE})}
            startFetchWeatherService();
        });

    }

    /**
     * Database related operation
     */
    private void startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }

    /**
     * initialize and return data entries
     *
     * @return LiveData<List<LocationEntry>> list of entries from db
     */
    public LiveData<List<LocationEntry>> getLocations() {
        initializeData();
        return mLocationDao.getAllLocations();
    }
}