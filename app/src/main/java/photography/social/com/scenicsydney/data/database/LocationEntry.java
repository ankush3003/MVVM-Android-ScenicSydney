package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Object used for mapping by ROOM
 */
@Entity(tableName = "locations")
public class LocationEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private long latitude;
    private long longitude;
    private Date lastUpdated;
    private String notes;

    /**
     * Constructor used by LocationParser.
     *
     * @param name name of location
     * @param latitude lat
     * @param longitude long
     * @param lastUpdated last updated date
     * @param notes additional notes
     */
    @Ignore
    public LocationEntry(String name, long latitude, long longitude, Date lastUpdated, String notes) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
        this.notes = notes;
    }

    /**
     * Constructor used by ROOM for object mapping.
     *
     * @param name name of location
     * @param latitude lat
     * @param longitude long
     * @param lastUpdated last updated date
     * @param notes additional notes
     */
    public LocationEntry(int id, String name, long latitude, long longitude, Date lastUpdated, String notes) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastUpdated = lastUpdated;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getNotes() {
        return notes;
    }
}
