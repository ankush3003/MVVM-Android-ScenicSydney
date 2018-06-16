package photography.social.com.scenicsydney;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    private final Executor diskIO;
    private final Executor mainThread;
    private final Executor networkIO;

    /**
     * private constructor for singleton init
     *
     * @param diskIO disk thread
     * @param networkIO network thread
     * @param mainThread main thread
     */
    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    /**
     * constructor
     *
     * @return AppExecutors instance
     */
    public static AppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutors(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    /**
     * Disk thread.
     * @return Executor
     */
    public Executor diskIO() {
        return diskIO;
    }

    /**
     * Main Thread.
     * @return Executor
     */
    public Executor mainThread() {
        return mainThread;
    }

    /**
     * Network Thread.
     * @return Executor
     */
    public Executor networkIO() {
        return networkIO;
    }

    /**
     * Gets Looper from mainThread and executes tasks.
     */
    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}