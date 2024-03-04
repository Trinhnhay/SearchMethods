import java.util.*;

public class Distance {

    private final CityMap cityMap ;

    public Distance(CityMap map){
        cityMap = map;
    }


    // Radius of the Earth in kilometers
    private static final double EARTH_RADIUS_KM = 6371.0;

    public double calculateDistance(String[] coord1, String[] coord2) {

        double lat1 = Math.toRadians(Double.parseDouble(coord1[0]));
        double lon1 = Math.toRadians(Double.parseDouble(coord1[1]));
        double lat2 = Math.toRadians(Double.parseDouble(coord2[0]));
        double lon2 = Math.toRadians(Double.parseDouble(coord2[1]));

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    // Calculate total distance along the route
    public double calculateTotalDistance(List<String> route) {
        double totalDistance = 0.0;

        for (int i = 0; i < route.size() - 1; i++) {
            String city1 = route.get(i);
            String city2 = route.get(i + 1);

            String[] coord1 = cityMap.cityCoordinate.get(city1);
            String[] coord2 = cityMap.cityCoordinate.get(city2);

            totalDistance += calculateDistance(coord1, coord2 );
        }

        return totalDistance;
    }

}
