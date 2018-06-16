package photography.social.com.scenicsydney.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;
import photography.social.com.scenicsydney.data.database.LocationEntry;

/**
 * ViewModel for {@link DetailActivity}
 */
class DetailActivityViewModel extends ViewModel {
    private final ScenicSydneyRepository mScenicSydneyRepository;

    /**
     * constructor
     * @param scenicSydneyRepository repository
     */
    DetailActivityViewModel(ScenicSydneyRepository scenicSydneyRepository) {
        this.mScenicSydneyRepository = scenicSydneyRepository;
    }

    /**
     * Retrieves LocationEntry matching location.
     *
     * @param location source location
     * @return LiveData<LocationEntry> result
     */
    LiveData<LocationEntry> getLocationEntry(Location location) {
        return mScenicSydneyRepository.getLocationEntry(location);
    }

    /**
     * Inserts data: if new OR updates data: if modified
     *
     * @param locationEntry target object
     */
    void insertOrUpdateData(LocationEntry locationEntry) {
        mScenicSydneyRepository.insertOrUpdateData(locationEntry);
    }
}
