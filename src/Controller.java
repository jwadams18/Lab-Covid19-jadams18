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

    /**
     * The current date of the slider
     *
     * @param relativeDate the date which the slider represents, a string
     */
    public void setRelativeDate(String relativeDate) {
        m.relativeDate = relativeDate;
    }

    /**
     * Gets the date to display, next to the slider and visually
     * @return a String in the form YYYY-MM-dd
     */
    public String getRelativeDate() {
        if (m.relativeDate == null) {
            return m.firstDate;
        }
        return m.relativeDate;
    }


    /**
     * Returns a HashMap containing all the counties in the lower 48, <FIPSID (Integer), County (County)>
     * @return Hashmap
     */
    public HashMap<Integer, County> getCounties() {
        return m.countiesList;
    }

    /**
     * Reads and store all the data from the census data and the GitHub repo to draw the county outlines and display cases/deaths
     */
    public void load() {
        m.loadCounties();
        m.loadVirusData();
    }

    /**
     * @return Returns a string in the form YYYY-MM-dd which the github repo has the first entry at
     */
    public String getFirstDate() {
        return m.firstDate;
    }

    /**
     * @return difference between the first date and most recent entry
     */
    public int getNumDays() {
        m.calcNumDays();
        return m.numDays;
    }

    /**
     * @param display 0 - displays cases 1 - displays deaths
     */
    public void setDisplay(int display) {
        m.currentDisplay = display;
    }

    /**
     * Gets what should be displayed, cases or deaths
     * @return 0 or 1
     */
    public int getDisplay() {
        return m.currentDisplay;
    }

    /**
     * Allows for the viewPanel, which contains the drawings, to be access and repainted to update circles
     * @param vp
     */
    public void setViewPanel(JPanel vp) {
        m.viewPanel = vp;
    }

    /**
     * Function to repaint viewPanel so the circles are updated when the slider or radiobuttons are changed
     */
    public void viewPanelRepaint() {
        m.viewPanel.repaint();
    }
}
