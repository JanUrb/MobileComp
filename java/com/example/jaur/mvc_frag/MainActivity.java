package com.example.jaur.mvc_frag;

import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Controller der App
 *
 * @author jaur
 */
public class MainActivity extends AppCompatActivity{ 

    private static final String TAG = "jaur6386MainActivity";

    private LocationModel locationModel;
    private CourseFragment courseFragment;
    private MapFragment mapFragment;
    private SpeedFragment speedFragment;

    private LocationManager locManager;
    private LocationListener locListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        //Model fuer Datentransfer zwischen Fragments etc.
        this.locationModel = new LocationModel();

        this.courseFragment = new CourseFragment();
        this.mapFragment = new MapFragment();
        this.speedFragment = new SpeedFragment();

        this.locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        this.locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "locManager.onLocationChanged");
                locationModel.setCurrentLocation(location);
                //locationModel benachrichtigt nun die Observer, in unserem Fall immer nur ein Fragment.
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.v(TAG, "onStatusChanged(provider: " + provider + " status: " + status + " extras: " + extras.toString());
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d(TAG, "onProviderEnabled(provider: " + provider);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d(TAG, "locListener.onProviderDisabled: " + provider);
            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);




        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        Log.v(TAG, "toolbar initiated");
        setSupportActionBar(myToolbar);

        if (savedInstanceState != null) {
            return;
        }

        //Starte die App im Map view
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment).commit(); //mapFragment onCreateView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.fragment_container, mapFragment);
            fragTransaction.addToBackStack(null);                               //! !a 8 ! HFragDynamic
            fragTransaction.commit();

            return true;
        } else if (id == R.id.action_course) {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.fragment_container, courseFragment);
            fragTransaction.addToBackStack(null);                               //! !a 8 ! HFragDynamic
            fragTransaction.commit();

            return true;
        } else if (id == R.id.action_speed) {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.fragment_container, speedFragment);
            fragTransaction.addToBackStack(null);                               //! !a 8 ! HFragDynamic
            fragTransaction.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public LocationModel getLocationModel(){
        return this.locationModel;
    }
}
