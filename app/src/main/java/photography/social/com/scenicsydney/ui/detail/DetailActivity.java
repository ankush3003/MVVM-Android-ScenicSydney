package photography.social.com.scenicsydney.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
    public static final String INTENT_EXTRA_IS_NEW_MARKER = "intent_extra_is_new_marker";

    private double mLat;
    private double mLng;
    private boolean mIsNewMarker;

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

        mLat = getIntent().getDoubleExtra(DetailActivity.INTENT_EXTRA_LAT_KEY, 0);
        mLng = getIntent().getDoubleExtra(DetailActivity.INTENT_EXTRA_LNG_KEY, 0);
        mIsNewMarker = getIntent().getBooleanExtra(DetailActivity.INTENT_EXTRA_IS_NEW_MARKER, true);

        populateData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if(!TextUtils.isEmpty(locationName.getText())) {
                    insertOrUpdateData();
                    finishActivityWithResult(Activity.RESULT_OK);
                } else {
                    locationName.setError(getString(R.string.detail_activity_location_name_required));
                }
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finishActivityWithResult(Activity.RESULT_CANCELED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void finishActivityWithResult(int result) {
        Intent returnIntent = new Intent();
        setResult(result, returnIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onBackPressed() {
        finishActivityWithResult(Activity.RESULT_CANCELED);
    }

    private void populateData() {
        String cordinatesVal = mLat + ", " + mLng;
        locationCoordinates.setText(cordinatesVal);

        // If not a new marker then populate details
        if(!mIsNewMarker) {
            Location location = new Location("");
            location.setLatitude(mLat);
            location.setLongitude(mLng);

            // Get target object from db and populate
            getViewModel().getLocationEntry(location).observe(this, locationEntry -> {
                if (locationEntry != null) {
                    getSupportActionBar().setTitle(locationEntry.getName());
                    locationName.setText(locationEntry.getName());
                    locationNotes.setText(locationEntry.getNotes());
                }
            });
        } else {
            getSupportActionBar().setTitle(getString(R.string.detail_activity_unnamed_location));
        }
    }

    private void insertOrUpdateData() {
        Location location = new Location("");
        location.setLatitude(mLat);
        location.setLongitude(mLng);

        LocationEntry locationEntry = new LocationEntry(
                locationName.getText().toString(),
                location,
                locationNotes.getText().toString()
        );

        getViewModel().insertOrUpdateData(locationEntry);
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
