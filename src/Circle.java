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

    public Circle(Coord center) {

        this.center = center;
        this.radius = 0;
        this.alpha = 127;

    }

    public void setRadius(Integer virusData, Integer population) {
        if (virusData == null) {
            this.radius = 0;
            return;
        }
        if (virusData >= 350) {
            if (this.alpha == 255) {
                return;
            }
            this.alpha++;
            return;
        }
//        System.out.println(virusData+" "+population);
//        this.radius = (virusData*100 / (population/1000));
        // current radius just set to number of deaths/cases
        this.radius = virusData;
    }

    public void draw(Graphics g, Integer virusData, Integer population) {

        setRadius(virusData, population);
        g.setColor(new Color(127, 0, 0, 127));
        g.fillOval((int) (1900 + this.center.getLatitude() * 15) - getRadius() / 2, (int) (1400 - this.center.getLongitude() * 25) - getRadius() / 2, getRadius(), getRadius());
//        if(getRadius() > 1);
//        System.out.println("Drawing circle at "+this.center.toString()+" with radius of "+getRadius());
//        g.setColor(Color.black);
//        g.drawOval((int) this.center.getLatitude(), (int) this.center.getLongitude(), getRadius(), getRadius());
    }

    public int getRadius() {
        return this.radius;
    }
}
