package photography.social.com.scenicsydney.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;

/**
 * Factory to create {@link MainActivityViewModel}
 */
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final ScenicSydneyRepository mRepository;

    public MainActivityViewModelFactory(ScenicSydneyRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
