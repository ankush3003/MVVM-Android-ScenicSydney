package photography.social.com.scenicsydney.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;
import photography.social.com.scenicsydney.data.database.LocationEntry;

/**
 * ViewModel for {@link DetailActivity}
 */
public class DetailActivityViewModel extends ViewModel {
    private final ScenicSydneyRepository mScenicSydneyRepository;

    public DetailActivityViewModel(ScenicSydneyRepository scenicSydneyRepository) {
        this.mScenicSydneyRepository = scenicSydneyRepository;
    }

    public LiveData<LocationEntry> getLocationEntry(Location location) {
        return mScenicSydneyRepository.getLocationEntry(location);
    }
}
