package photography.social.com.scenicsydney.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;
import photography.social.com.scenicsydney.ui.main.MainActivityViewModel;

/**
 * Factory to create {@link DetailActivityViewModel}
 */
public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final ScenicSydneyRepository mRepository;

    public DetailActivityViewModelFactory(ScenicSydneyRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository);
    }
}
