package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import java.util.Date;

/**
 * Object used for mapping by ROOM
 */
@Entity(tableName = "locations", indices = {@Index(value = {"location"}, unique = true)})
public class LocationEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private Location location;
    private String notes;

    /**
     * Constructor used by LocationParser.
     *
     * @param name name of location
     * @param location coordinates of location
     * @param notes additional notes
     */
    @Ignore
    public LocationEntry(String name, Location location, String notes) {
        this.name = name;
        this.location = location;
        this.notes = notes;
    }

    /**
     * Constructor used by ROOM for object mapping.
     *
     * @param name name of location
     * @param location coordinates
     * @param notes additional notes
     */
    public LocationEntry(int id, String name, Location location, String notes) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.notes = notes;
    }

    public int getId() {
        return id;
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
