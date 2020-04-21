//    James Adams
//    Lab-COVID-jadams18
//    County.java

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class County {

    private Integer FIPSID, population;
    private Coord[] coords;
    private Map<String, Integer> cases; // < Date string, Num cases >
    private Map<String, Integer> deaths;// < Date string, Num deaths >
    private Color color;

    public County(Integer FIPSID, Integer population, Coord[] coords, Color color) {

        this.FIPSID = FIPSID;
        this.population = population;
        this.coords = coords;
        this.cases = new HashMap<>();
        this.deaths = new HashMap<>();
        this.color = color;

    }

    public Integer getID() {
        return this.FIPSID;
    }

    public Map<String, Integer> getCases() {
        return this.cases;
    }

    public Map<String, Integer> getDeaths() {
        return this.deaths;
    }

    public void draw(Graphics g) {

        int[] xPoints = new int[coords.length];
        int[] yPoints = new int[coords.length];


        for (int i = 0; i < coords.length; i++) {
            xPoints[i] = (int) coords[i].getLongitude();
            yPoints[i] = (int) coords[i].getLatitude();
        }

        g.setColor(this.color);
        g.fillPolygon(xPoints, yPoints, xPoints.length);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, xPoints.length);

    }

}
