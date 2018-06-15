package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * {@link Database} for project.
 * Specify list of {@link TypeConverters} required.
 */
@Database(entities = {LocationEntry.class}, version = 1)
@TypeConverters(LocationConvertor.class)
public abstract class LocationDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "locations";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile LocationDatabase sInstance;

    // Getter for Dao
    public abstract LocationDao locationDao();

    public static LocationDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            LocationDatabase.class, LocationDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }
}
