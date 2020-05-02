//    James Adams
//    Lab-COVID-jadams18
//    County.java

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class County {

    private Integer FIPSID, population;
    private Coord[] coords;
    private HashMap<String, Integer> cases; // < Date string, Num cases >
    private HashMap<String, Integer> deaths;// < Date string, Num deaths >
    private Color color;
    private int radius;
    private Coord center;
    private Circle circle;

    /**
     * Used to store the information loaded from the census data (location) and from the GitHub repo (Virus data)
     *
     * @param FIPSID     the ID which will be used to get specific county
     * @param population
     * @param coords     An array of coord objects which store Latitude and Longitude
     * @param color      a random color to group states visually
     * @param center     the midpoint of the county to center the circle around
     */
    public County(Integer FIPSID, Integer population, Coord[] coords, Color color, Coord center) {

        this.FIPSID = FIPSID;
        this.population = population;
        this.coords = coords;
        this.cases = new HashMap<>();
        this.deaths = new HashMap<>();
        this.color = color;
        this.center = center;

        this.circle = new Circle(center);

    }

    public Integer getID() {
        return this.FIPSID;
    }

    /**
     * Based on the current display, will get the cases or deaths for the current date
     *
     * @param currentDate a string that will be used to get cases/deaths
     * @param display     0/1 - Cases/Deaths
     */
    public void setCircleRadius(String currentDate, int display) {
        Integer virusData;
        if (display == 0) {
            virusData = this.cases.get(currentDate);
        } else {
            virusData = this.deaths.get(currentDate);
        }
        this.circle.setRadius(virusData);
    }


    public Map<String, Integer> getCases() {
        return this.cases;
    }

    public Map<String, Integer> getDeaths() {
        return this.deaths;
    }

    /**
     * Draws the county outline, and fills with color based on state
     * @param g
     * @param display 0/1 - Cases/Deaths
     * @param currentDate a string used to get data YYYY-MM-dd
     */
    public void draw(Graphics g, int display, String currentDate) {

        int[] xPoints = new int[coords.length];
        int[] yPoints = new int[coords.length];

        //Draws county outline
        for (int i = 0; i < coords.length; i++) {
            xPoints[i] = (int) coords[i].getLongitude();
            yPoints[i] = (int) coords[i].getLatitude();
        }

        g.setColor(this.color);
        g.fillPolygon(xPoints, yPoints, xPoints.length);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, xPoints.length);

        //Draws circle outline
        Integer virusData;
        if (display == 0) {
            virusData = this.cases.get(currentDate);
        } else {
            virusData = this.deaths.get(currentDate);
        }

        this.circle.draw(g, virusData);
    }
}
