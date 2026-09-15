import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class InitValueSettings extends JPanel {

    public InitValueSettings(){

        this.setPreferredSize(new Dimension(main.windowWidth/6 - 10, 225));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        JSlider rabbitSpeed = new JSlider(1,4,1);
        JSlider rabbitSensory = new JSlider(1,8,3);
        JSlider wolfSpeed = new JSlider(1,4,2);
        JSlider wolfSensory = new JSlider(1,8,5);

        rabbitSpeed.setBounds(0,60,main.windowWidth/6 - 10,20);
        rabbitSensory.setBounds(0,105,main.windowWidth/6 - 10,20);
        wolfSpeed.setBounds(0,150,main.windowWidth/6 - 10,20);
        wolfSensory.setBounds(0,195,main.windowWidth/6 - 10,20);

        rabbitSpeed.setBackground(Color.LIGHT_GRAY);
        rabbitSensory.setBackground(Color.LIGHT_GRAY);
        wolfSpeed.setBackground(Color.LIGHT_GRAY);
        wolfSensory.setBackground(Color.LIGHT_GRAY);

        this.add(rabbitSpeed);
        this.add(rabbitSensory);
        this.add(wolfSpeed);
        this.add(wolfSensory);

        JTextField rabbitSpeedText = new JTextField();
        JTextField rabbitSensoryText = new JTextField();
        JTextField wolfSpeedText = new JTextField();
        JTextField wolfSensoryText = new JTextField();

        rabbitSpeedText.setBounds(150,35,30,25);
        rabbitSensoryText.setBounds(155,80,25,25);
        wolfSpeedText.setBounds(150,125,30,25);
        wolfSensoryText.setBounds(150,170,30,25);

        rabbitSpeedText.setEditable(false);
        rabbitSensoryText.setEditable(false);
        wolfSpeedText.setEditable(false);
        wolfSensoryText.setEditable(false);

        rabbitSpeedText.setHorizontalAlignment(SwingConstants.CENTER);
        rabbitSensoryText.setHorizontalAlignment(SwingConstants.CENTER);
        wolfSpeedText.setHorizontalAlignment(SwingConstants.CENTER);
        wolfSensoryText.setHorizontalAlignment(SwingConstants.CENTER);

        rabbitSpeedText.setText(String.valueOf(SimulationBoard.initialRabbitSpeed));
        rabbitSensoryText.setText(String.valueOf(SimulationBoard.initialRabbitSensoryRadius));
        wolfSpeedText.setText(String.valueOf(SimulationBoard.initialWolfSpeed));
        wolfSensoryText.setText(String.valueOf(SimulationBoard.initialWolfSensoryRadius));

        rabbitSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.initialRabbitSpeed = rabbitSpeed.getValue();
                rabbitSpeedText.setText(String.valueOf(SimulationBoard.initialRabbitSpeed));
            }
        });
        rabbitSensory.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.initialRabbitSensoryRadius = rabbitSensory.getValue();
                rabbitSensoryText.setText(String.valueOf(SimulationBoard.initialRabbitSensoryRadius));
            }
        });
        wolfSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.initialWolfSpeed = wolfSpeed.getValue();
                wolfSpeedText.setText(String.valueOf(SimulationBoard.initialWolfSpeed));
            }
        });
        wolfSensory.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.initialWolfSensoryRadius = wolfSensory.getValue();
                wolfSensoryText.setText(String.valueOf(SimulationBoard.initialWolfSensoryRadius));
            }
        });

        this.add(rabbitSpeedText);
        this.add(rabbitSensoryText);
        this.add(wolfSpeedText);
        this.add(wolfSensoryText);
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        g.drawString("Initial Traits",35,20);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,16));
        g.drawString("Rabbit init speed :",10,50);
        g.drawString("Rabbit init sensory:",10,95);
        g.drawString("Wolf init speed :",10,140);
        g.drawString("Wolf init sensory :",10,185);
    }
}
