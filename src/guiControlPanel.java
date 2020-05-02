//    James Adams
//    Lab-COVID-jadams18
//    guiControlPanel.java

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class guiControlPanel extends JPanel {

    private JSlider dateSlider;
    private JLabel currentDate, currentDisplay;
    private JRadioButton cases, deaths;
    private ButtonGroup buttonGroup;
    private GridBagConstraints constraints;
    private Controller c;
    private int oldSliderValue = 0, deltaSlider;

    /**
     * Contains slider and radiobuttons that control what is displayed
     *
     * @param c
     */
    public guiControlPanel(Controller c) {

        this.c = c;
        constraints = new GridBagConstraints();
        currentDate = new JLabel();
        currentDate.setText("Current Date: " + c.getFirstDate());

        constraints.gridx = 1;
        this.add(currentDate, constraints);

        dateSlider = new JSlider(0, c.getNumDays(), 0);
        constraints.gridx = 0;
        this.add(dateSlider, constraints);

        dateSlider.addChangeListener(event -> {
            //Getting the number of days to change by, should be either +1 or -1
            deltaSlider = dateSlider.getValue() - oldSliderValue;
            oldSliderValue = dateSlider.getValue();
            //If the date hasnt been set, then it must be the first date
            if (c.getRelativeDate() == null) {
                c.setRelativeDate(c.getFirstDate());
            }

            String oldDate = c.getRelativeDate();
            //Specifying date format that matches the given date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            try {
                //Setting the date to the given date
                cal.setTime(sdf.parse(oldDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Number of Days to add
            cal.add(Calendar.DAY_OF_MONTH, deltaSlider);
            //Date after adding the days to the given date
            String newDate = sdf.format(cal.getTime());
            //Displaying the new Date after addition of Days
            c.setRelativeDate(newDate);
            currentDate.setText("Current Date: " + newDate);
            c.getCounties().forEach((k, v) -> v.setCircleRadius(c.getRelativeDate(), c.getDisplay()));
            c.viewPanelRepaint();
        });

        currentDisplay = new JLabel("Currently displaying:  ");

        constraints.gridx = 2;
        this.add(currentDisplay, constraints);

        cases = new JRadioButton("cases", true);
        deaths = new JRadioButton("deaths");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(cases);
        buttonGroup.add(deaths);

        constraints.gridx = 3;
        this.add(cases, constraints);

        constraints.gridx = 4;
        this.add(deaths, constraints);

        //On action will change to correct display and update the circle for each county, and repaint

        cases.addActionListener(event -> {
            c.setDisplay(0);
            c.getCounties().forEach((k, v) -> v.setCircleRadius(c.getRelativeDate(), c.getDisplay()));
            this.repaint();
        });

        deaths.addActionListener(event -> {
            c.setDisplay(1);
            c.getCounties().forEach((k, v) -> v.setCircleRadius(c.getRelativeDate(), c.getDisplay()));
            this.repaint();
        });

    }
}
