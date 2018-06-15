package photography.social.com.scenicsydney.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import photography.social.com.scenicsydney.R;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.utils.InjectorUtils;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String INTENT_EXTRA_LAT_KEY = "intent_extra_lat";
    public static final String INTENT_EXTRA_LNG_KEY = "intent_extra_lng";

    @BindView(R.id.locationName) EditText locationName;
    @BindView(R.id.locationCoordinates) EditText locationCoordinates;
    @BindView(R.id.locationNotes) EditText locationNotes;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setOnClickListener(this);

        populateData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    private void populateData() {
        double lat = getIntent().getDoubleExtra(DetailActivity.INTENT_EXTRA_LAT_KEY, 0);
        double lng = getIntent().getDoubleExtra(DetailActivity.INTENT_EXTRA_LNG_KEY, 0);
        Location location = new Location("");
        location.setLatitude(lat);
        location.setLongitude(lng);

        // Get target object from db and populate
        getViewModel().getLocationEntry(location).observe(this, locationEntry -> {
            if (locationEntry != null) {
                locationName.setText(locationEntry.getName());

                String cordinatesVal = lat + ", " + lng;
                locationCoordinates.setText(cordinatesVal);

                locationNotes.setText(locationEntry.getNotes());
            }
        });
    }

    /**
     * Gets ViewModel from DetailActivityViewModelFactory instantiated using dependency injection
     *
     * @return DetailActivityViewModel
     */
    private DetailActivityViewModel getViewModel() {
        DetailActivityViewModelFactory factory = InjectorUtils.provideDetailActivityViewModelFactory(this);
        return ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);
    }
}
