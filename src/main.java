import javax.swing.*;
import java.awt.*;

public class main{

    public static int windowWidth = 1200;
    public static int windowHeight = 750;

    public static void main(String[] args) {

        // GUI setup

        JFrame frame = new JFrame();

        OverviewPanel overviewPanel = new OverviewPanel();
        SimulationBoard simulationPanel = new SimulationBoard();
        SettingsMenu settingsMenu = new SettingsMenu();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(windowWidth,windowHeight));
        frame.add(simulationPanel,BorderLayout.CENTER);
        frame.add(settingsMenu,BorderLayout.LINE_END);
        frame.add(overviewPanel,BorderLayout.PAGE_END);

        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

}
