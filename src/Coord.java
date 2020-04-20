//    James Adams
//    Lab-COVID-jadams18
//    Coord.java

public class Coord {

    private double longitude;
    private double latitude;


    public Coord(double longitude, double latitude) {

        this.longitude = longitude;
        this.latitude = latitude;

    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return this.longitude + "," + latitude;
    }
}
