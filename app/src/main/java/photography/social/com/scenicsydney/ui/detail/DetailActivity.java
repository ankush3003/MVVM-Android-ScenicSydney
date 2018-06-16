package photography.social.com.scenicsydney.ui.detail;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import photography.social.com.scenicsydney.R;
import photography.social.com.scenicsydney.data.database.LocationEntry;
import photography.social.com.scenicsydney.utils.InjectorUtils;

/**
 * This activity displays data for addition/update of location markers.
 */
public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    // Intent Extras
    public static final String INTENT_EXTRA_LAT_KEY = "intent_extra_lat";
    public static final String INTENT_EXTRA_LNG_KEY = "intent_extra_lng";
    public static final String INTENT_EXTRA_IS_NEW_MARKER = "intent_extra_is_new_marker";

    private double mLat;
    private double mLng;
    private boolean mIsNewMarker;

    // view references
    @BindView(R.id.locationName) EditText etLocationName;
    @BindView(R.id.locationCoordinates) EditText etLocationCoordinates;
    @BindView(R.id.locationNotes) EditText etLocationNotes;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    /**
     *  get intent extras and populate data accordingly.
     * @param savedInstanceState savedInstanceState
     */
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

    /**
     * Handles view click
     * @param view target
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if(!TextUtils.isEmpty(etLocationName.getText())) {
                    insertOrUpdateData();
                    Toast.makeText(this, getString(R.string.detail_activity_data_saved), Toast.LENGTH_SHORT).show();
                    finishActivityWithResult(Activity.RESULT_OK);
                } else {
                    etLocationName.setError(getString(R.string.detail_activity_location_name_required));
                }
                break;

            default:
                break;
        }
    }

    /**
     * listener
     * @param item source item
     * @return boolean result
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finishActivityWithResult(Activity.RESULT_CANCELED);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles back press event
     */
    @Override
    public void onBackPressed() {
        finishActivityWithResult(Activity.RESULT_CANCELED);
    }

    /**
     * finishes activity with specified result
     * @param result resultCode
     */
    void finishActivityWithResult(int result) {
        Intent returnIntent = new Intent();
        setResult(result, returnIntent);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    /**
     * populates fields
     */
    private void populateData() {
        String coordinatesVal = mLat + ", " + mLng;
        etLocationCoordinates.setText(coordinatesVal);

        // If not a new marker then populate details else blank fields
        if(!mIsNewMarker) {
            Location location = new Location("");
            location.setLatitude(mLat);
            location.setLongitude(mLng);

            // Get target object from db and populate
            getViewModel().getLocationEntry(location).observe(this, locationEntry -> {
                if (locationEntry != null) {
                    getSupportActionBar().setTitle(locationEntry.getName());
                    etLocationName.setText(locationEntry.getName());
                    etLocationNotes.setText(locationEntry.getNotes());
                }
            });
        } else {
            getSupportActionBar().setTitle(getString(R.string.detail_activity_unnamed_location));
        }
    }

    /**
     * Insert (if new marker) or update (if modified data) in db.
     */
    private void insertOrUpdateData() {
        // location is primary key in db
        Location location = new Location("");
        location.setLatitude(mLat);
        location.setLongitude(mLng);

        LocationEntry locationEntry = new LocationEntry(
                etLocationName.getText().toString(),
                location,
                etLocationNotes.getText().toString()
        );

        // insertOrUodate
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
