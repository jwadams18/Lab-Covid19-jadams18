/*
        James Adams
        Lab-COVID-jadams18
        Circle.java
        @username jwadams18
         */

import java.awt.*;

public class Circle {

    private Coord center;
    private int radius, alpha;

    /**
     * Used to display cases/deaths which can be updated based on the current date
     *
     * @param center
     */
    public Circle(Coord center) {

        this.center = center;
        this.radius = 0;
        //Set the color to be 50% transparent, so when the
        //circle hits a certain radius it will stop growing to preserve
        // visibility, and instead will "grow" darker with more cases/deaths
        this.alpha = 127;

    }

    /**
     * Sets the radius of the circle, if null the radius is set to 0, then if it is less than some arbitrary value then it will be the count
     * of cases/deaths once it passes that number the circle will become more red instead of expanding to prevent massive overlapping reducing visibility
     *
     * @param virusData
     */
    public void setRadius(Integer virusData) {
        if (virusData == null) {
            this.radius = 0;
            return;
        }
        //Set in top of Model to a value that still allows other states to be visible
        if (virusData >= Model.MAX_RADIUS) {
            if (this.alpha == 255) {
                return;
            }
            this.alpha++;
            return;
        }
        // current radius just set to number of deaths/cases
        this.radius = virusData;
    }

    //Draws the circle with the radius, which is set based on the current date's cases/deaths for each county
    public void draw(Graphics g, Integer virusData) {

        setRadius(virusData);
        g.setColor(new Color(127, 0, 0, 127));
        g.fillOval((int) (1900 + this.center.getLatitude() * 15) - getRadius() / 2, (int) (1300 - this.center.getLongitude() * 25) - getRadius() / 2, getRadius(), getRadius());
    }

    public int getRadius() {
        return this.radius;
    }
}
