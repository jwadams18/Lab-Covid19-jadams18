//    James Adams
//    Lab-COVID-jadams18
//    Controller.java

import javax.swing.*;
import java.util.HashMap;

public class Controller {

    private Model m;

    public Controller() {
        this.m = new Model();

    }

    public void setRelativeDate(String relativeDate) {
        m.relativeDate = relativeDate;
    }

    public String getRelativeDate() {
        if (m.relativeDate == null) {
            return m.firstDate;
        }
        return m.relativeDate;
    }


    public HashMap<Integer, County> getCounties() {
        return m.countiesList;
    }

    public void load() {
        m.loadCounties();
        m.loadVirusData();
    }

    public String getFirstDate() {
        return m.firstDate;
    }

    public int getNumDays() {
        m.calcNumDays();
        return m.numDays;
    }

    public void setDisplay(int display) {
        m.currentDisplay = display;
    }

    public int getDisplay() {
        return m.currentDisplay;
    }

    public void setViewPanel(JPanel vp) {
        m.viewPanel = vp;
    }

    public void viewPanelRepaint() {
        m.viewPanel.repaint();
    }
}
