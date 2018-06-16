package photography.social.com.scenicsydney.data.database;

import android.arch.persistence.room.TypeConverter;
import android.location.Location;

import java.util.Locale;

/**
 * {@link TypeConverter} for String to {@link Location}
 * <p>
 * This stores the Location as String in the database, but returns it as a {@link Location}
 */
class LocationConvertor {
    @TypeConverter
    public static String fromLocation(Location location) {
        if (location==null) {
            return(null);
        }

        return(String.format(Locale.US,
                "%f,%f",
                location.getLatitude(),
                location.getLongitude()));
    }

    @TypeConverter
    public static Location toLocation(String latlon) {
        if (latlon==null) {
            return(null);
        }

        String[] pieces=latlon.split(",");
        Location result=new Location("");

        result.setLatitude(Double.parseDouble(pieces[0]));
        result.setLongitude(Double.parseDouble(pieces[1]));

        return(result);
    }
}
