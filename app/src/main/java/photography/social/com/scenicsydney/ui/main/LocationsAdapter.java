package photography.social.com.scenicsydney.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import photography.social.com.scenicsydney.R;
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
     * @param context context
     * @param clickHandler handler
     */
    LocationsAdapter(@NonNull Context context, LocationsAdapterOnItemClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * onCreateViewHolder callback
     *
     * @param viewGroup viewgroup
     * @param viewType type
     * @return LocationAdapterViewHolder
     */
    @Override
    public LocationAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_view, viewGroup, false);
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

        locationAdapterViewHolder.locationName.setText(locationEntry.getName());

        // Update locationDescription if provided, "No Description" if none.
        if(TextUtils.isEmpty(locationEntry.getNotes())) {
            locationAdapterViewHolder.locationDescription.setText(mContext.getResources().getString(R.string.no_description_provided));
            locationAdapterViewHolder.locationDescription.setTextColor(mContext.getResources().getColor(R.color.darkGrey));
        } else {
            locationAdapterViewHolder.locationDescription.setText(locationEntry.getNotes());
            locationAdapterViewHolder.locationDescription.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }

        // Update distance in Kms
        int distanceInMeters = Math.round(locationEntry.getDistance());
        String distanceString = "" ;
        if(distanceInMeters > 1000) {
            distanceString = new DecimalFormat("#.##").format(distanceInMeters * 0.001) + " km";
        } else {
            distanceString = distanceInMeters+ " meters";
        }
        locationAdapterViewHolder.distanceFromUser.setText(distanceString);
    }

    /**
     * @return The number of items available
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

    /**
     * view holder class
     */
    class LocationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView locationName;
        final TextView locationDescription;
        final TextView distanceFromUser;

        /**
         * constructor
         * @param view view
         */
        LocationAdapterViewHolder(View view) {
            super(view);

            locationName = view.findViewById(R.id.title);
            locationDescription = view.findViewById(R.id.subtitle);
            distanceFromUser = view.findViewById(R.id.distance);

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