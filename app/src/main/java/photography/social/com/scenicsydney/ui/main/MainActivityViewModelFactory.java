package photography.social.com.scenicsydney.ui.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import photography.social.com.scenicsydney.data.ScenicSydneyRepository;

/**
 * Factory to create {@link MainActivityViewModel}
 */
public class MainActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final ScenicSydneyRepository mRepository;

    /**
     * constructor
     *
     * @param repository repository
     */
    public MainActivityViewModelFactory(ScenicSydneyRepository repository) {
        this.mRepository = repository;
    }

    /**
     * creates ViewModel
     *
     * @param modelClass ViewModel class
     * @param <T>
     * @return ViewModel
     */
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainActivityViewModel(mRepository);
    }
}
