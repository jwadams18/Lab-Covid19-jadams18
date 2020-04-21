//    James Adams
//    Lab-COVID-jadams18
//    guiControlPanel.java

import javax.swing.*;
import java.awt.*;

public class guiControlPanel extends JPanel {

    private JSlider dateSlider;
    private JLabel currentDate, currentDisplay;
    private JRadioButton cases, deaths;
    private ButtonGroup buttonGroup;
    private GridBagConstraints constraints;

    public guiControlPanel() {

        constraints = new GridBagConstraints();
        currentDate = new JLabel("Current date");

        constraints.gridx = 1;
        this.add(currentDate, constraints);

        dateSlider = new JSlider(0, 100, 50);
        constraints.gridx = 0;
        this.add(dateSlider, constraints);

        dateSlider.addChangeListener(event -> {
            currentDate.setText(dateSlider.getValue() + " temp");
        });

        currentDisplay = new JLabel("Currently displaying ");

        constraints.gridx = 2;
        this.add(currentDisplay, constraints);

        cases = new JRadioButton("cases");
        deaths = new JRadioButton("deaths");

        buttonGroup = new ButtonGroup();
        buttonGroup.add(cases);
        buttonGroup.add(deaths);

        constraints.gridx = 3;
        this.add(cases, constraints);

        constraints.gridx = 4;
        this.add(deaths, constraints);

    }
}
