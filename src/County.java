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

    public County(Integer FIPSID, Integer population, Coord[] coords, Color color, Coord center) {

        this.FIPSID = FIPSID;
        this.population = population;
        this.coords = coords;
        this.cases = new HashMap<>();
        this.deaths = new HashMap<>();
        this.color = color;
        this.center = center;

    }

    public Integer getID() {
        return this.FIPSID;
    }

    public Integer getPopulation() {
        return this.population;
    }

    public Map<String, Integer> getCases() {
        return this.cases;
    }

    public Map<String, Integer> getDeaths() {
        return this.deaths;
    }

    public void draw(Graphics g, int display, String currentDate) {

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


        if (display == 0) {
            setRadius(this.cases, currentDate);
        } else {
            setRadius(this.deaths, currentDate);
        }
        g.setColor(new Color(255, 0, 0, 127));
        g.fillOval((int) this.center.getLatitude(), (int) this.center.getLongitude(), getRadius(), getRadius());
        g.setColor(Color.black);
        g.drawOval((int) this.center.getLatitude(), (int) this.center.getLongitude(), getRadius(), getRadius());


    }

    //TODO finish getRadius so that I can draw the circle, consider date, and cases/deaths
    public void setRadius(HashMap<String, Integer> data, String currentDate) {

    }

    public int getRadius() {
        return 1;
    }

}
