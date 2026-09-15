import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MutationSettings extends JPanel {

    public MutationSettings(){

        this.setPreferredSize(new Dimension(main.windowWidth/6 - 10, 175));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        JSlider mutationRates = new JSlider(0,100,70);
        JSlider speedCap = new JSlider(1,6,4);
        JSlider sensoryCap = new JSlider(2,10,6);

        mutationRates.setBounds(0,60,main.windowWidth/6 - 10,20);
        speedCap.setBounds(0,105,main.windowWidth/6 - 10,20);
        sensoryCap.setBounds(0,150,main.windowWidth/6 - 10,20);

        mutationRates.setBackground(Color.LIGHT_GRAY);
        speedCap.setBackground(Color.LIGHT_GRAY);
        sensoryCap.setBackground(Color.LIGHT_GRAY);

        this.add(mutationRates);
        this.add(speedCap);
        this.add(sensoryCap);

        JTextField mutationRatesText = new JTextField();
        JTextField speedCapText = new JTextField();
        JTextField sensoryCapText = new JTextField();

        mutationRatesText.setBounds(150,35,30,25);
        speedCapText.setBounds(150,80,30,25);
        sensoryCapText.setBounds(150,125,30,25);

        mutationRatesText.setEditable(false);
        speedCapText.setEditable(false);
        sensoryCapText.setEditable(false);

        mutationRatesText.setHorizontalAlignment(SwingConstants.CENTER);
        speedCapText.setHorizontalAlignment(SwingConstants.CENTER);
        sensoryCapText.setHorizontalAlignment(SwingConstants.CENTER);

        mutationRatesText.setText(String.valueOf(SimulationBoard.mutationRate));
        speedCapText.setText(String.valueOf(SimulationBoard.mutationSpeedCap));
        sensoryCapText.setText(String.valueOf(SimulationBoard.mutationSensoryCap));

        mutationRates.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.mutationRate = mutationRates.getValue();
                mutationRatesText.setText(String.valueOf(SimulationBoard.mutationRate));
            }
        });
        speedCap.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.mutationSpeedCap = speedCap.getValue();
                speedCapText.setText(String.valueOf(SimulationBoard.mutationSpeedCap));
            }
        });
        sensoryCap.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SimulationBoard.mutationSensoryCap = sensoryCap.getValue();
                sensoryCapText.setText(String.valueOf(SimulationBoard.mutationSensoryCap));
            }
        });

        this.add(mutationRatesText);
        this.add(speedCapText);
        this.add(sensoryCapText);
    }

    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        g.drawString("Mutations",50,20);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,16));
        g.drawString("Mutation rate % :",10,50);
        g.drawString("Speed cap :",10,95);
        g.drawString("Sensory cap :",10,140);
    }
}
