package photography.social.com.scenicsydney.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import photography.social.com.scenicsydney.data.database.LocationEntry;


/**
 * Exposes a list of Locations to a {@link RecyclerView}.
 */
class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationAdapterViewHolder> {

    private final Context mContext;
    private final LocationsAdapterOnItemClickHandler mClickHandler;

    private List<LocationEntry> mLocations;

    /**
     * Creates LocationsAdapter.
     *
     * @param context
     * @param clickHandler
     */
    LocationsAdapter(@NonNull Context context, LocationsAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    @Override
    public LocationAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);
        return new LocationAdapterViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at specified position.
     *
     * @param locationAdapterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(LocationAdapterViewHolder locationAdapterViewHolder, int position) {
        LocationEntry locationEntry = mLocations.get(position);
        // TODO: locationAdapterViewHolder

    }

    /**
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == mLocations) return 0;
        return mLocations.size();
    }

    /**
     * Reloads locations list as specified
     *
     * @param locations new list
     */
    void reloadLocations(List<LocationEntry> locations) {
        mLocations = locations;
        notifyDataSetChanged();
    }

    public interface LocationsAdapterOnItemClickHandler {
        void onItemClick(LocationEntry locationEntry);
    }

    class LocationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        final TextView locationName;
//        final TextView locationDescription;
//        final TextView distanceFromUser;

        LocationAdapterViewHolder(View view) {
            super(view);

//            locationName = view.findViewById(R.id.weather_icon);
//            locationDescription = view.findViewById(R.id.date);
//            distanceFromUser = view.findViewById(R.id.weather_description);

            view.setOnClickListener(this);
        }

        /**
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {
            mClickHandler.onItemClick(mLocations.get(getAdapterPosition()));
        }
    }
}