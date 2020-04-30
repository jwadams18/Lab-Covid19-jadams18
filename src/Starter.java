//    James Adams
//    Lab-COVID-jadams18
//    Starter.java

import javax.swing.*;
import java.awt.*;

public class Starter {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Covid tracker - Jadams18");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setVisible(true);
        frame.setLayout(new GridLayout());

        ViewPanel vpanel = new ViewPanel();
        vpanel.setMinimumSize(new Dimension(925, 925));
        guiControlPanel panel = new guiControlPanel(vpanel.getController());

        JSplitPane spane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vpanel, panel);
        spane.setResizeWeight(1);
        spane.setDividerSize(5);


        frame.add(spane);
        frame.pack();

    }
}
