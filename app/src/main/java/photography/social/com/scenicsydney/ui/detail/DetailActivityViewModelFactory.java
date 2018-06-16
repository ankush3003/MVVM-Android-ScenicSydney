package photography.social.com.scenicsydney.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;

/**
 * Factory to create {@link DetailActivityViewModel}
 */
public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final ScenicSydneyRepository mRepository;

    /**
     * constructor
     *
     * @param repository repository
     */
    public DetailActivityViewModelFactory(ScenicSydneyRepository repository) {
        this.mRepository = repository;
    }

    /**
     *  creates ViewModel
     *
     * @param modelClass target
     * @param <T>
     * @return ViewModel
     */
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository);
    }
}
