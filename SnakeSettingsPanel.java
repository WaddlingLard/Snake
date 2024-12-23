import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SnakeSettingsPanel extends JComponent{

    private static final int PANEL_SIZE = 400;
    private static final int BORDER_GAP = 5;

    private ButtonGroup speeds;

    public SnakeSettingsPanel() {
        setupPanel();
    }

    public void setupPanel() {

        BorderLayout layout = new BorderLayout(BORDER_GAP, BORDER_GAP);
        JFrame frame = new JFrame();
        frame.setLayout(layout);
        frame.setTitle("Settings");

        JLabel header = new JLabel("Speed Setting");
        GridLayout settingsLayout = new GridLayout(4,1);
        JPanel panel = new JPanel();
        panel.setLayout(settingsLayout);

        JButton set = new JButton("Set Speed");
        set.addActionListener(new SetButtonListener());

        speeds = new ButtonGroup();
        
        JRadioButtonMenuItem low = new JRadioButtonMenuItem();
        JRadioButtonMenuItem med = new JRadioButtonMenuItem();
        JRadioButtonMenuItem high = new JRadioButtonMenuItem();
        low.setText("Low Speed");
        med.setText("Med Speed");
        high.setText("High Speed");

        speeds.add(low);
        speeds.add(med);
        speeds.add(high);

        panel.add(low);
        panel.add(med);
        panel.add(high);
        panel.add(set);

        frame.add(header, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setSize((int) (PANEL_SIZE / 1.5), PANEL_SIZE / 2);
        frame.setVisible(true);
    }

    private class SetButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("SET BUTTON PRESSED!");
        }

    }
}
