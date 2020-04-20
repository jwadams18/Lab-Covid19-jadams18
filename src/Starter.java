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

        ViewPanel vpanel = new ViewPanel();

    }
}
