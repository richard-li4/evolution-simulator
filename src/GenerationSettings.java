import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenerationSettings extends JPanel{

    public GenerationSettings(){

        this.setPreferredSize(new Dimension(main.windowWidth/6 - 10, 125));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);

        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(50,90,100,30);
        generateButton.setFocusPainted(false);
        this.add(generateButton);

        JTextField rabbitNum = new JTextField();
        JTextField wolfNum = new JTextField();
        rabbitNum.setBounds(110,33,50,23);
        wolfNum.setBounds(110,60,50,23);
        rabbitNum.setText(String.valueOf(SimulationBoard.generateRabbitNum));
        wolfNum.setText(String.valueOf(SimulationBoard.generateWolfNum));
        this.add(rabbitNum);
        this.add(wolfNum);

        generateButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                OverviewPanel.reset();
                try{
                    if(Integer.parseInt(rabbitNum.getText()) >= 0)
                        SimulationBoard.generateRabbitNum = Integer.parseInt(rabbitNum.getText());
                    else
                        rabbitNum.setText(String.valueOf(SimulationBoard.generateRabbitNum));
                    // Prevents numbers below 0 to be entered

                }catch (Exception e1){
                    // Prevents anything other than integers to be entered
                    rabbitNum.setText(String.valueOf(SimulationBoard.generateRabbitNum));
                }
                try{
                    if(Integer.parseInt(wolfNum.getText()) >= 0)
                        SimulationBoard.generateWolfNum = Integer.parseInt(wolfNum.getText());
                    else
                        wolfNum.setText(String.valueOf(SimulationBoard.generateWolfNum));
                    // Prevents numbers below 0 to be entered

                }catch (Exception e1){
                    // Prevents anything other than integers to be entered
                    wolfNum.setText(String.valueOf(SimulationBoard.generateWolfNum));
                }
                SimulationBoard.generateGrid(SimulationBoard.generateRabbitNum,SimulationBoard.generateWolfNum,SimulationBoard.simulationGrid);
                SimulationBoard.generateOrganismArrayAccordingToGird(SimulationBoard.simulationGrid,SimulationBoard.organisms);
                OverviewPanel.plotGraph();
            }
        });

    }

    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,20));
        g.drawString("Generation",45,20);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,16));
        g.drawString("Rabbits :",30,50);
        g.drawString("Wolves :",30,77);
    }
}

