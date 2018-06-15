package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Object used for mapping by ROOM
 */
@Entity(tableName = "locations", indices = {@Index(value = {"location"}, unique = true)})
public class LocationEntry {

    @NonNull
    @PrimaryKey
    private Location location;

    private String name;
    private String notes;

    /**
     * Constructor.
     *
     * @param name name of location
     * @param location coordinates of location
     * @param notes additional notes
     */
    public LocationEntry(String name, Location location, String notes) {
        this.name = name;
        this.location = location;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getNotes() {
        return notes;
    }
}
