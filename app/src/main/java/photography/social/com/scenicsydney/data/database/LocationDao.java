package photography.social.com.scenicsydney.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * {@link Dao} provides APIs for all data operations.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(LocationEntry... locationEntries);

    @Query("SELECT * FROM locations")
    LiveData<LocationEntry> getAllLocations();
}
