//    James Adams
//    Lab-COVID-jadams18
//    ViewPanel.java

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ViewPanel extends JPanel {

    private Controller c;
    private HashMap<Integer, County> countyList;

    public ViewPanel() {
        this.setPreferredSize(new Dimension(1000, 1000));
        this.setMaximumSize(new Dimension(1000, 1000));
        this.c = new Controller();
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
