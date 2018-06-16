package photography.social.com.scenicsydney.data;

import android.arch.lifecycle.LiveData;
import android.location.Location;

import java.util.List;

import photography.social.com.scenicsydney.AppExecutors;
import photography.social.com.scenicsydney.data.database.LocationDao;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.data.network.LocationDataParser;

/**
 * Handles data operations in project.
 */
public class ScenicSydneyRepository {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static ScenicSydneyRepository sInstance;

    private final LocationDao mLocationDao;
    private final LocationDataParser mLocationDataParser;
    private final AppExecutors mExecutors;

    /**
     * private constructor for singleton initialization
     *
     * @param locationDao dao
     * @param locationDataParser parser
     * @param executors executor
     */
    private ScenicSydneyRepository(LocationDao locationDao,
                                   LocationDataParser locationDataParser,
                                   AppExecutors executors) {
        mLocationDao = locationDao;
        mLocationDataParser = locationDataParser;
        mExecutors = executors;

        // DB operations on separate thread
        mExecutors.diskIO().execute(() -> {

            // App running for first time or data reset
            if(mLocationDao.getLocationsCount() == 0) {

                // Insert our new locations (from json file) data into database
                mLocationDao.bulkInsert(mLocationDataParser.parseFromFile());
            }
        });
    }

    /**
     * Get the singleton for this class
     *
     * @param locationDao dao
     * @param locationDataParser parser
     * @param executors executor
     * @return ScenicSydneyRepository instance
     */
    public synchronized static ScenicSydneyRepository getInstance(LocationDao locationDao,
                                                                  LocationDataParser locationDataParser,
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

    /**
     * return data entry matching criteria
     *
     * @param location source location.
     * @return LiveData<LocationEntry> target entry from db
     */
    public LiveData<LocationEntry> getLocationEntry(Location location) {
        return mLocationDao.getLocationEntry(location);
    }

    /**
     * Inserts data - if new OR
     * Updates data - if conflict happens
     *
     * @param locationEntry target locationEntry to add/update
     */
    public void insertOrUpdateData(LocationEntry locationEntry) {
        mExecutors.diskIO().execute(() -> {
            mLocationDao.bulkInsert(locationEntry);
        });
    }
}