import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu extends JPanel {

    public SettingsMenu(){
        this.setPreferredSize(new Dimension(main.windowWidth/6,4*(main.windowHeight/5)));
        this.setBackground(Color.GRAY);
        JPanel settingsPanel = new JPanel();
        JButton infoButton = new JButton();
        JLabel infoLabel = new JLabel("    Info");

        infoLabel.setFont(new Font("Comic Sans MS", Font.PLAIN,33));

        settingsPanel.setBackground(Color.GRAY);
        infoButton.setBackground(Color.LIGHT_GRAY);
        infoButton.setPreferredSize(new Dimension(main.windowWidth/6 - 10, 54));
        infoButton.add(infoLabel);

        GenerationSettings generationSettings = new GenerationSettings();
        InitValueSettings initValueSettings = new InitValueSettings();
        MutationSettings mutationSettings = new MutationSettings();

        settingsPanel.add(generationSettings);
        settingsPanel.add(initValueSettings);
        settingsPanel.add(mutationSettings);
        settingsPanel.add(infoButton);

        InfoPage infoPage = new InfoPage();

        infoButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                infoPage.setVisible(!infoPage.isVisible());
                // Alternates the visibility of info page when clicked
            }
        });


        this.setLayout(new BorderLayout(3,3));
        this.add(settingsPanel, BorderLayout.CENTER);
    }

}
