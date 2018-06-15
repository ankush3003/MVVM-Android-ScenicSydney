package photography.social.com.scenicsydney.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import photography.social.com.scenicsydney.R;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.utils.InjectorUtils;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        LocationsAdapter.LocationsAdapterOnItemClickHandler {

    private GoogleMap mMap;
    private RecyclerView mRecyclerView;
    private LocationsAdapter mLocationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mRecyclerView = findViewById(R.id.recycler_view);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        populateList();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //lstLatLngs.add(point);
        //mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "" + marker.getSnippet(), Toast.LENGTH_SHORT).show();
    }

    private void populateList() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mLocationsAdapter = new LocationsAdapter(this, this);
        mRecyclerView.setAdapter(mLocationsAdapter);

        // Get ViewModel and start observing data.
        getViewModel().getLocations().observe(this, locationEntries -> {
            if (locationEntries != null && locationEntries.size() != 0) {
                // refresh list
                mLocationsAdapter.reloadLocations(locationEntries);

                // refresh list
                LatLng latLng = null;

                for (LocationEntry locationEntry: locationEntries) {
                    latLng = new LatLng(locationEntry.getLocation().getLatitude(), locationEntry.getLocation().getLongitude());

                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(locationEntry.getName()));
                }

                if(latLng != null) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo( 11.0f ) );
                }
            }

        });
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

    @Override
    public void onItemClick(LocationEntry locationEntry) {

    }
}
