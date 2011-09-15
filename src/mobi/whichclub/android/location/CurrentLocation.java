package mobi.whichclub.android.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * A static class to keep track of the current location.
 * @author camrdale
 */
public final class CurrentLocation implements LocationListener {

    /** The number of milliseconds to keep an old GPS reading before falling back to Network location. */
    private static final long RETAIN_GPS_MILLIS = 10000L;

    /** The one instantiation of this class. */
    private static CurrentLocation singleton = null;

    /** The location manager to request updates from. */
    private LocationManager locationManager = null;
    /** The current location. */
    private Location lastLocation = null;
    /** Whether we have a location. */
    private boolean mHaveLocation;
    /** The uptime in milliseconds when the last GPS reading was made. */
    private long mLastGpsFixTime;
    /** The last reading from the Network location provider. */
    private Location mNetworkLocation;
    /** Whether the GPS provider is available. */
    private boolean mGpsAvailable;
    /** Whether the Network provider is available. */
    private boolean mNetworkAvailable;
    
    /**
     * Instantiate the singleton instance of this class.
     * @param context the application context to use
     */
    private CurrentLocation(final Context context) {
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
    }
    
    /**
     * Initialize the class, if it hasn't already been done.
     * You must call this method before any other.
     * @param context the application context to use
     */
    public static synchronized void initialize(final Context context) {
        if (singleton == null) {
            singleton = new CurrentLocation(context);
        }
        singleton.onLocationChanged(singleton.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        singleton.onLocationChanged(singleton.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        singleton.locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 1, singleton);
        singleton.locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 1, singleton);
    }
    
    /**
     * Stop getting location updates.
     * After calling this method you must call initialize() before calling any other methods.
     */
    public static synchronized void close() {
        if (singleton != null) {
            if (singleton.locationManager != null) {
                singleton.locationManager.removeUpdates(singleton);
                singleton.locationManager = null;
            }
            singleton = null;
        }
    }
    
    /**
     * Calculate the distance from the current location to the target.
     * @param latitude the target's latitude
     * @param longitude the target's longitude
     * @return the distance to the target, in meters
     */
    public static Double getDistance(final Double latitude, final Double longitude) {
        if (singleton == null || singleton.locationManager == null) {
            throw new IllegalStateException("CurrentLocation has not been initialized.");
        }
        if (singleton.lastLocation == null || latitude == null || longitude == null) {
            return null;
        }
        Location target = new Location(LocationManager.GPS_PROVIDER);
        target.setLatitude(latitude);
        target.setLongitude(longitude);
        return Double.valueOf(singleton.lastLocation.distanceTo(target));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void onLocationChanged(final Location location) {
        if (location == null) {
            return;
        }
        if (!mHaveLocation) {
            mHaveLocation = true;
        }

        long now = SystemClock.uptimeMillis();
        if (location.getTime() < System.currentTimeMillis()) {
            now -= System.currentTimeMillis() - location.getTime();
        }
        boolean useLocation = false;
        final String provider = location.getProvider();
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            // Use GPS if available
            mLastGpsFixTime = now;
            useLocation = true;
        } else if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
            // Use network provider if GPS is getting stale
            useLocation = now - mLastGpsFixTime > RETAIN_GPS_MILLIS;
            if (mNetworkLocation == null) {
                mNetworkLocation = new Location(location);
            } else {
                mNetworkLocation.set(location);
            }
            
            mLastGpsFixTime = 0L;
        }
        if (useLocation) {
            if (lastLocation == null) {
                lastLocation = new Location(location);
            } else {
                lastLocation.set(location);
            }
//            mMyLocationLat = location.getLatitude();
//            mMyLocationLon = location.getLongitude();
//    
//            mDistance = GeoUtils.distanceKm(mMyLocationLat, mMyLocationLon, mTargetLat,
//                    mTargetLon);
//    
//            mBearing = GeoUtils.bearing(mMyLocationLat, mMyLocationLon, mTargetLat,
//                    mTargetLon);
//    
//            updateDistance(mDistance);
        }
    }

    @Override
    public void onProviderDisabled(final String provider) {
    }

    @Override
    public void onProviderEnabled(final String provider) {
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            switch (status) {
            case LocationProvider.AVAILABLE:
                mGpsAvailable = true;
                break;
            case LocationProvider.OUT_OF_SERVICE:
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                mGpsAvailable = false;
                if (mNetworkLocation != null && mNetworkAvailable) {
                    // Fallback to network location
                    mLastGpsFixTime = 0L;
                    onLocationChanged(mNetworkLocation);
                } else {
                    handleUnknownLocation();
                }
                break;
            default:
                // do nothing
            }

        } else if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
            switch (status) {
            case LocationProvider.AVAILABLE:
                mNetworkAvailable = true;
                break;
            case LocationProvider.OUT_OF_SERVICE:
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                mNetworkAvailable = false;
                
                if (!mGpsAvailable) {
                    handleUnknownLocation();
                }
                break;
            default:
                // do nothing
            }
        }
    }

    /**
     * Handle the case where no location provider is available.
     */
    private void handleUnknownLocation() {
        // TODO Auto-generated method stub
        
    }

}
