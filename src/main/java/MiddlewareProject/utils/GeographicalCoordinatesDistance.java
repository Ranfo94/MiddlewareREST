package MiddlewareProject.utils;


public class GeographicalCoordinatesDistance {

    /**
     * This method calculates the distance between two points which have both geografical coordinates. It takes
     * latitude and longitude of both the points, then transforms this value in values calculated in meters; then
     * it calculates the distance between two points.
     * @param lat1 the latitude of the first point
     * @param lat2 the latitude of the second point
     * @param lon1 the longitude of the first point
     * @param lon2 the longitude of the second point
     * @return the distance calculated in km
     */
    public double distanceFromGeogCoordToMeters(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c; //measure in km

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
