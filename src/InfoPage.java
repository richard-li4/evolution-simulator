import javax.swing.*;
import java.awt.*;

public class InfoPage extends JFrame {

    public static int infoPageWidth = 665;
    public static int infoPageHeight = 300;

    InfoPage(){

        this.setPreferredSize(new Dimension(infoPageWidth,infoPageHeight));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setVisible(false);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        DrawPanel drawPanel = new DrawPanel();
        this.add(drawPanel,BorderLayout.CENTER);
        this.pack();
    }

    public static class DrawPanel extends JPanel{

        DrawPanel(){
            setBackground(Color.LIGHT_GRAY);
            setPreferredSize(new Dimension(infoPageWidth,infoPageHeight));
        }

        public void paint(Graphics g){
            super.paint(g);
            g.setFont(new Font("Comic Sans MS", Font.BOLD,24));
            g.drawString("Evolution mechanisms",200,33);
            g.setFont(new Font("Comic Sans MS", Font.PLAIN,16));
            g.drawString("• Simulation is in rounds",5,60);
            g.drawString("• Each round, an organism is allowed 1 action",5,90);
            g.drawString("• Action priority list:",5,120);
            g.drawString("  eat nearby food > run away from predator > go towards food > random movement",30,150);
            g.drawString("• Organism actions will also be based off of their trait values: speed and sensory radius",5,180);
            g.drawString("• Eating will cause the organism to produce its off spring",5,210);
            g.drawString("• Each time an off spring is produced, it will be mutated according to mutation settings",5,240);
        }

    }
}
