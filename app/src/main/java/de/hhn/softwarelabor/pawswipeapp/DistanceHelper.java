package de.hhn.softwarelabor.pawswipeapp;

public class DistanceHelper {
    public static void main(String[] args) {
        DistanceHelper distanceHelper = new DistanceHelper();
        distanceHelper.getShelter(50, 49.13662231551046, 8.898404339694421);
        System.out.println(distanceHelper.getShelter(50, 49.16000069301587, 9.201400418450476));
    }

    private boolean getShelter(int distance, double lat, double lon){


        double currentLat = 49.138384142862975;
        double currentLong = 8.86117270276183;

        double earthRadius = 6371; // Radius der Erde in Kilometern
        double dLat = Math.toRadians(lat - currentLat);
        double dLon = Math.toRadians(lon - currentLong);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(currentLat)) * Math.cos(Math.toRadians(lat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInKm = earthRadius * c;

        // Überprüfung, ob die Distanz innerhalb des angegebenen Radius liegt
        return distanceInKm <= distance;
    }

}
