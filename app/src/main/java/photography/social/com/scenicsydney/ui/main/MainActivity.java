package photography.social.com.scenicsydney.ui.main;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import photography.social.com.scenicsydney.R;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng milsons = new LatLng(-33.850750, 151.212519);
        LatLng bondi = new LatLng(-33.889967, 151.276440);
        LatLng circular = new LatLng(-33.860178, 151.212706);
        LatLng manly = new LatLng(-33.797151, 151.288784);
        LatLng darling = new LatLng(-33.873379, 151.200940);

        Marker mMilsons = mMap.addMarker(new MarkerOptions()
                .position(milsons)
                .title("Melbourne")
                /*.snippet("wkjebfc lew cqwnc wcnapwn weqiha nxawpocj e wdjcpx qwmqd")*/);

        Marker mbondi = mMap.addMarker(new MarkerOptions()
                .position(bondi)
                .title("bondi")
                /*.snippet("wkjebfc lew cqwnc wcnapwn weqiha nxawpocj e wdjcpx qwmqdewq qwl c")*/);

        Marker mcircular = mMap.addMarker(new MarkerOptions()
                .position(circular)
                .title("circular")
                /*.snippet("wkjebfc lew cqwnc wcnapwn weqiha nxawpocj e wdjcpx qwmqd")*/);

        Marker mmanly = mMap.addMarker(new MarkerOptions()
                .position(manly)
                .title("Melbourne")
                /*.snippet("wkjebfc lew cqwnc wcnapwn weqiha nxawpocj e wdjcpx qwmqd sle clae cilc;mwamce ac[m caec j")*/);

        Marker mdarling = mMap.addMarker(new MarkerOptions()
                .position(darling)
                .title("darling")
                /*.snippet("wkjebfc lew cqwnc wcnapwn weqiha nxawpocj e wdjcpx qwmqd")*/);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(bondi));
        mMap.animateCamera(CameraUpdateFactory.zoomTo( 11.0f ) );

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        //lstLatLngs.add(point);
        //mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "" + marker.getSnippet(), Toast.LENGTH_SHORT).show();
    }
}
