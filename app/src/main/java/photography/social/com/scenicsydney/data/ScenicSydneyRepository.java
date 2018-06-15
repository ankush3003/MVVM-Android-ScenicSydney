package photography.social.com.scenicsydney.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

import photography.social.com.scenicsydney.AppExecutors;
import photography.social.com.scenicsydney.data.database.LocationDao;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.data.network.LocationDataParser;

/**
 * Handles data operations in project.
 */
public class ScenicSydneyRepository {
    private static final String TAG = ScenicSydneyRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ScenicSydneyRepository sInstance;
    private final LocationDao mLocationDao;
    private final LocationDataParser mLocationDataParser;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private ScenicSydneyRepository(LocationDao locationDao,
                                   LocationDataParser locationDataParser,
                                   AppExecutors executors) {
        mLocationDao = locationDao;
        mLocationDataParser = locationDataParser;
        mExecutors = executors;

        mExecutors.diskIO().execute(() -> {
            // App running for first time or data reset
            if(mLocationDao.getLocationsCount() == 0) {
                // Insert our new locations data into database
                mLocationDao.bulkInsert(mLocationDataParser.parseFromFile());
                Log.d(TAG, "New values inserted");
            }
        });
    }

    public synchronized static ScenicSydneyRepository getInstance(
            LocationDao locationDao, LocationDataParser locationDataParser,
            AppExecutors executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ScenicSydneyRepository(locationDao, locationDataParser,
                        executors);
            }
        }
        return sInstance;
    }

    /**
     * return data entries from db
     *
     * @return LiveData<List<LocationEntry>> list of entries from db
     */
    public LiveData<List<LocationEntry>> getLocations() {
        return mLocationDao.getAllLocations();
    }
}