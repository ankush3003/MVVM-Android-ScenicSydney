package photography.social.com.scenicsydney.data.network;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import photography.social.com.scenicsydney.data.database.LocationEntry;

/**
 * Parser for Location Data.
 */
final class LocationDataParser {
    private static final String LOCATION_ARRAY_KEY = "locations";
    private static final String LAST_UPDATED_KEY = "updated";

    private static final String LOCATION_NAME = "name";
    private static final String LOCATION_LAT = "lat";
    private static final String LOCATION_LONG = "lng";

    private static LocationEntry[] fromJson(final JSONObject jsonObject) throws JSONException {
        JSONArray jsonLocationArray = jsonObject.getJSONArray(LOCATION_ARRAY_KEY);

        LocationEntry[] locationEntries = new LocationEntry[jsonLocationArray.length()];

        for (int i = 0; i < jsonLocationArray.length(); i++) {
            JSONObject jsonLocationObject = jsonLocationArray.getJSONObject(i);
            locationEntries[i] = new LocationEntry(
                    jsonLocationObject.optString(LOCATION_NAME, ""),
                    jsonLocationObject.optLong(LOCATION_LAT, 0),
                    jsonLocationObject.optLong(LOCATION_LONG, 0),
                    "");
        }
        return locationEntries;
    }
}