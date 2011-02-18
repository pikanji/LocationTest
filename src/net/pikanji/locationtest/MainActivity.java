
package net.pikanji.locationtest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * This program demonstrates the usage of LocationManager. It shows that
 * location update can be requested to multiple location providers, with one
 * LocationListener.<br>
 * The listener implemented in this program uses both locations obtained by GPS
 * and network if they are available. It's purpose is to demonstrate that both
 * can be used at the same time and with one listener. However, usually it is
 * practical to use only one of them, since their accuracies are probably
 * different.<br>
 * This also shows that the update condition is AND of minimum time interval and
 * minimum distance change. onLocationChanged is invoked only when the time
 * elapsed more than then minimum interval and also the distance change is more
 * than the minimum distance.
 * 
 * @author kanji
 */
public class MainActivity extends Activity implements LocationListener, OnClickListener {
    private final int MIN_TIME = 1000; // minimum time interval in millisecond
    private final int MIN_DISTANCE = 20; // minimum distance in meter

    private final String NEW_LINE = System.getProperty("line.separator");

    private LocationManager mLocationManager;

    private TextView mTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTextView = (TextView) findViewById(R.id.text_result);
        Button button = (Button) findViewById(R.id.button_clear);
        button.setOnClickListener(this);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mLocationManager) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME,
                    MIN_DISTANCE, this);
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,
                    MIN_DISTANCE, this);

            Location locGps = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locNet = mLocationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if ((null == locGps) && (null == locNet)) {
                return;
            }

            String toAppend;
            if ((null != locGps) && (null != locNet)) {
                if (locGps.getTime() > locNet.getTime()) {
                    toAppend = "GPS - lat: " + locGps.getLatitude();
                    toAppend += ", lon: " + locGps.getLongitude();
                } else {
                    toAppend = "NET - lat: " + locNet.getLatitude();
                    toAppend += ", lon: " + locNet.getLongitude();
                }
            } else if (null != locGps) {
                toAppend = "GPS - lat: " + locGps.getLatitude();
                toAppend += ", lon: " + locGps.getLongitude();
            } else {
                toAppend = "NET - lat: " + locNet.getLatitude();
                toAppend += ", lon: " + locNet.getLongitude();
            }
            mTextView.setText(NEW_LINE + toAppend);
        }
    }

    @Override
    protected void onPause() {
        if (null != mLocationManager) {
            mLocationManager.removeUpdates(this);
        }
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {
        String toAppend;
        if (LocationManager.GPS_PROVIDER.equals(location.getProvider())) {
            toAppend = "GPS - ";
        } else if (LocationManager.NETWORK_PROVIDER.equals(location.getProvider())) {
            toAppend = "NET - ";
        } else {
            toAppend = "UNKNOWN - ";
        }
        toAppend += "lat: " + location.getLatitude();
        toAppend += ", lon: " + location.getLongitude();
        mTextView.append(NEW_LINE + toAppend);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        mTextView.setText(R.string.default_text);
    }
}
