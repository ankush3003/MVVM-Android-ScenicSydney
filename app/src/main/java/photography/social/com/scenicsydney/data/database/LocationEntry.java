package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

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

    // distance need not be stored in db and is calculated dynamically
    @Ignore
    private float distance;

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

    /**
     * Getter for name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for location
     *
     * @return Location
     */
    @NonNull
    public Location getLocation() {
        return location;
    }

    /**
     * Getter for notes
     * @return String
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Getter for distance
     * @return float
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Setter for distance
     * @param distance target
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }
}
