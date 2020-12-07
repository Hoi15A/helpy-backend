package ch.zhaw.pm3.helpy.model.location;

import lombok.experimental.UtilityClass;

/**
 * Utilities for working with Locations
 */
@UtilityClass
public class LocationUtil {

    /**
     * Calculates the distance between two locations
     * @param geolocation1 Coordinates of a location
     * @param geolocation2 Coordinates of a location
     * @return approximate distance in km
     */
    public double calcDistance(String geolocation1, String geolocation2) {
        double lat1 = Double.parseDouble(geolocation1.split(",")[0]);
        double long1 = Double.parseDouble(geolocation1.split(",")[1]);
        double lat2 = Double.parseDouble(geolocation2.split(",")[0]);
        double long2 = Double.parseDouble(geolocation2.split(",")[1]);

        double theta = long1 - long2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
