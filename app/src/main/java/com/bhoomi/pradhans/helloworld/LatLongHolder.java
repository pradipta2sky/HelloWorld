package com.bhoomi.pradhans.helloworld;

/**
 * Created by Rinky on 15-10-2017.
 */

public class LatLongHolder {
    private String latitude;
    private String longitude;
    private int locationNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatLongHolder)) return false;

        LatLongHolder that = (LatLongHolder) o;

        if (!latitude.equals(that.latitude)) return false;
        return longitude.equals(that.longitude);

    }
    public LatLongHolder(String lat, String longitude){
        this.latitude = lat;
        this.longitude = longitude;
    }
    @Override
    public int hashCode() {
        int result = latitude.hashCode();
        result = 31 * result + longitude.hashCode();
        return result;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getLocationNumber() {
        return locationNumber;
    }

    public void setLocationNumber(int locationNumber) {
        this.locationNumber = locationNumber;
    }
}
