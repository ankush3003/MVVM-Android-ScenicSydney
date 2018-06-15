package photography.social.com.scenicsydney.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.location.Location;

import java.util.List;

/**
 * {@link Dao} provides APIs for all data operations.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(LocationEntry... locationEntries);

    @Query("SELECT * FROM locations")
    LiveData<List<LocationEntry>> getAllLocations();

    @Query("SELECT COUNT(*) FROM locations")
    int getLocationsCount();

    @Query("SELECT * FROM locations where location = :location")
    LiveData<LocationEntry> getLocationEntry(Location location);

    @Update
    void updateLocation(LocationEntry... locationEntries);
}
