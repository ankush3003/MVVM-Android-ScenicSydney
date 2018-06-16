package photography.social.com.scenicsydney.ui.main;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import photography.social.com.scenicsydney.R;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.ui.detail.DetailActivity;
import photography.social.com.scenicsydney.utils.InjectorUtils;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        LocationsAdapterOnItemClickHandler {

    private GoogleMap mMap;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.sliding_layout) SlidingUpPanelLayout slidingUpPanelLayout;

    private LocationsAdapter mLocationsAdapter;
    private final int mDeatilActivityRequestCode = 101;
    private Marker mLastCustomMarker = null;

    // Reference location (will be user's current location) hardcoded for now
    // Used for sorting list based on distance from reference point
    double mCurrentLatitude = -33.9045681462;
    double mCurrentLongitude = 151.1956366896;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * Adds markers as per db and populates list sorted by distance
     *
     * @param googleMap map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        populateList();
    }

    /**
     * Handles back press event.
     * Closes Slideuppanel if expanded.
     */
    @Override
    public void onBackPressed() {
        if (slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return;
        }
        super.onBackPressed();
    }

    /**
     * Handles Map long click - adds new marker
     *
     * @param latLng target marker's coordinates
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        mLastCustomMarker = mMap.addMarker(new MarkerOptions().position(latLng));
        navigateToDetailActivity(latLng.latitude, latLng.longitude, true);
    }

    /**
     * Handles marker click - Opens DetailActivity
     *
     * @param marker source
     * @return result
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        navigateToDetailActivity(marker.getPosition().latitude, marker.getPosition().longitude, false);
        return false;
    }

    /**
     * Handles RecyclerView click - Opens DetailActivity
     * @param locationEntry source object
     */
    @Override
    public void onItemClick(LocationEntry locationEntry) {
        navigateToDetailActivity(locationEntry.getLocation().getLatitude(), locationEntry.getLocation().getLongitude(), false);
    }

    /**
     * populates recyclerview as well as markers on map - as per data from db.
     */
    private void populateList() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mLocationsAdapter = new LocationsAdapter(this, this);
        mRecyclerView.setAdapter(mLocationsAdapter);

        Location refLocation = new Location("");
        refLocation.setLatitude(mCurrentLatitude);
        refLocation.setLongitude(mCurrentLongitude);

        // Get ViewModel and start observing data.
        getViewModel().getLocations().observe(this, locationEntries -> {
            if (locationEntries != null && locationEntries.size() != 0) {
                mMap.clear();

                Collections.sort(locationEntries, new Comparator<LocationEntry>() {
                    @Override
                    public int compare(LocationEntry locationEntry1, LocationEntry locationEntry2) {
                        float distance1 =  refLocation.distanceTo(locationEntry1.getLocation());
                        locationEntry1.setDistance(distance1);
                        float distance2 =  refLocation.distanceTo(locationEntry2.getLocation());
                        locationEntry2.setDistance(distance2);
                        if (distance1 == distance2)
                            return 0;
                        else if (distance1 > distance2)
                            return 1;
                        else
                            return -1;
                    }
                });

                // refresh list
                mLocationsAdapter.reloadLocations(locationEntries);

                for (LocationEntry locationEntry: locationEntries) {
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(locationEntry.getLocation().getLatitude(),
                                    locationEntry.getLocation().getLongitude()))
                            .title(locationEntry.getName()));
                }
            }
        });
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLatitude, mCurrentLongitude), 11.5f));
    }

    /**
     * Gets ViewModel from MainActivityViewModelFactory instantiated using dependency injection
     *
     * @return MainActivityViewModel
     */
    private MainActivityViewModel getViewModel() {
        MainActivityViewModelFactory factory = InjectorUtils.provideMainActivityViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
    }

    /**
     * Navigates to DetailActivity with params.
     *
     * @param lat lat
     * @param lng lng
     * @param isNewMarker boolean
     */
    private void navigateToDetailActivity(double lat, double lng, boolean isNewMarker) {
        Intent detailActivityIntent = new Intent(this, DetailActivity.class);
        detailActivityIntent.putExtra(DetailActivity.INTENT_EXTRA_LAT_KEY, lat);
        detailActivityIntent.putExtra(DetailActivity.INTENT_EXTRA_LNG_KEY, lng);
        detailActivityIntent.putExtra(DetailActivity.INTENT_EXTRA_IS_NEW_MARKER, isNewMarker);
        startActivityForResult(detailActivityIntent, mDeatilActivityRequestCode);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    /**
     * onActivityResult callback
     *
     * @param requestCode requestCode
     * @param resultCode resultCode
     * @param data data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == mDeatilActivityRequestCode) {
            if (resultCode == Activity.RESULT_CANCELED && mLastCustomMarker != null) {
                mLastCustomMarker.remove();
            }
        }
    }
}
