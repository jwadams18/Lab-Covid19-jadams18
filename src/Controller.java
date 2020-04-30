//    James Adams
//    Lab-COVID-jadams18
//    Controller.java

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
}
