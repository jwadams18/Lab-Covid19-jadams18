//    James Adams
//    Lab-COVID-jadams18
//    ViewPanel.java

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ViewPanel extends JPanel {

    private Controller c;
    private HashMap<Integer, County> countyList;

    /**
     * Used to display the lower 48 states
     */
    public ViewPanel() {
        this.c = new Controller();
        c.setViewPanel(this);
        c.load();
        countyList = c.getCounties();

    }

    public Controller getController() {
        return this.c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        countyList.forEach((fips, county) -> county.draw(g, c.getDisplay(), c.getRelativeDate()));
    }
}
