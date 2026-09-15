
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverviewPanel extends JPanel implements ActionListener {

    public static int timeCounter = 10000;
    public static JButton startStopButton = new JButton();
    public static JLabel startStopLabel = new JLabel("START");
    public static Icon startImage = new ImageIcon(OverviewPanel.class.getResource("/start.png"));
    public static Icon stopImage = new ImageIcon(OverviewPanel.class.getResource("/stop.png"));
    public static JLabel roundCount = new JLabel("Round: 0");

    public static XYSeries rabbitSpeed = new XYSeries("Rabbit avg speed");
    public static XYSeries rabbitSensory = new XYSeries("Rabbit avg sensory");
    public static XYSeries wolfSpeed = new XYSeries("Wolf avg speed");
    public static XYSeries wolfSensory = new XYSeries("Wolf avg sensory");

    public static JSlider playBackSpeed = new JSlider(0,2,0);


    public OverviewPanel(){
        this.setPreferredSize(new Dimension(main.windowWidth,main.windowHeight/5));
        this.setBackground(Color.GRAY);

        JPanel graphPanel = new JPanel();
        JPanel startStopPanel = new JPanel();

        startStopPanel.setBackground(Color.GRAY);
        startStopPanel.setPreferredSize(new Dimension(main.windowWidth/6,main.windowHeight/5));

        graphPanel.setBackground(Color.GRAY);
        graphPanel.setPreferredSize(new Dimension(5*main.windowWidth/6 - 7,main.windowHeight/5));

        startStopLabel.setFont(new Font("SansSerif", Font.BOLD,48));
        startStopLabel.setForeground(new Color(99,153,103));

        startStopButton.setPreferredSize(new Dimension(main.windowWidth/6,95));
        startStopButton.addActionListener(this);
        startStopButton.setIcon(startImage);
        startStopButton.add(startStopLabel);
        startStopPanel.add(startStopButton);

        playBackSpeed.setPreferredSize(new Dimension(main.windowWidth/6,20));
        playBackSpeed.setBackground(Color.GRAY);
        playBackSpeed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(SimulationBoard.paused) {
                    if (playBackSpeed.getValue() == 0) {
                        SimulationBoard.movementVelocity = 1;
                        SimulationBoard.roundPeriod = 1000;
                    } else if (playBackSpeed.getValue() == 1) {
                        SimulationBoard.movementVelocity = 2;
                        SimulationBoard.roundPeriod = 500;
                    } else {
                        SimulationBoard.movementVelocity = 3;
                        SimulationBoard.roundPeriod = 334;
                    }
                }
            }
        });

        startStopPanel.add(playBackSpeed);

        JPanel counterPanel = new JPanel();
        JPanel graph = new JPanel();

        counterPanel.setPreferredSize(new Dimension(150,main.windowHeight/5 - 10));
        counterPanel.setBackground(Color.LIGHT_GRAY);

        roundCount.setHorizontalAlignment(SwingConstants.CENTER);
        roundCount.setFont(new Font("Comic Sans MS", Font.PLAIN,30));
        counterPanel.setLayout(new BorderLayout());
        counterPanel.add(roundCount,BorderLayout.CENTER);

        graph.setPreferredSize(new Dimension(833,main.windowHeight/5 - 10));
        graph.setBackground(Color.LIGHT_GRAY);
        graph.setLayout(new BorderLayout(0,0));

        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(rabbitSpeed);
        data.addSeries(rabbitSensory);
        data.addSeries(wolfSpeed);
        data.addSeries(wolfSensory);

        JFreeChart chart = ChartFactory.createXYLineChart(
                null,
                "Time in rounds",
                "Trait values",
                data,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(833, main.windowHeight/5 - 10));
        graph.add(chartPanel,BorderLayout.CENTER);



        graphPanel.add(counterPanel);
        graphPanel.add(graph);

        this.setLayout(new BorderLayout(3,3));
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(startStopPanel, BorderLayout.LINE_END);
    }

    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // When the timer is active, the following code will be executed every 10 milliseconds.
            if(timeCounter < SimulationBoard.roundPeriod) {
                timeCounter += 10;
                SimulationBoard.moveToTargetLocation();
            }
            else{
                SimulationBoard.checkPositions();
                SimulationBoard.startRound();
                SimulationBoard.decideAction();
                SimulationBoard.checkOverlaps();
                SimulationBoard.hungerCheck();
                SimulationBoard.generateNewPlants();
                roundCount.setText("Round: "+SimulationBoard.generationCounter);
                plotGraph();
            }
        }
    });

    @Override
    public void actionPerformed(ActionEvent e) {
        // Starts the timer if paused, pauses the timer if started
        if(SimulationBoard.paused){
            SimulationBoard.paused = false;
            timer.start();
            startStopButton.setIcon(stopImage);
            startStopLabel.setForeground(new Color(159,89,89));
            startStopLabel.setText("PAUSE");
            playBackSpeed.setEnabled(false);
        }
        else{
            SimulationBoard.paused = true;
            timer.stop();
            startStopButton.setIcon(startImage);
            startStopLabel.setForeground(new Color(99,153,103));
            startStopLabel.setText("START");
            playBackSpeed.setEnabled(true);
        }
    }

    public static void plotGraph(){
        rabbitSpeed.add(SimulationBoard.generationCounter, SimulationBoard.getRabbitAvgSpeed());
        rabbitSensory.add(SimulationBoard.generationCounter, SimulationBoard.getRabbitAvgSensory());
        wolfSpeed.add(SimulationBoard.generationCounter, SimulationBoard.getWolfAvgSpeed());
        wolfSensory.add(SimulationBoard.generationCounter, SimulationBoard.getWolfAvgSensory());
    }

    public static void reset(){
        SimulationBoard.generationCounter = 0;
        roundCount.setText("Round: "+SimulationBoard.generationCounter);
        rabbitSpeed.clear();
        rabbitSensory.clear();
        wolfSpeed.clear();
        wolfSensory.clear();
    }


    public void paint(Graphics g){
        super.paint(g);
        g.setFont(new Font("Comic Sans MS", Font.PLAIN,16));
        g.drawString("slow",1000,139);
        g.drawString("fast",1085,139);
        g.drawString("fastest",1145,139);
    }
}
