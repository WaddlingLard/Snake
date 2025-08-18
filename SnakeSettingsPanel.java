import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SnakeSettingsPanel extends JComponent {

    private static final int PANEL_SIZE = 400;
    private static final int BORDER_GAP = 5;

    private ButtonGroup speeds;
    private double[] speedDelay;
    private double[] unconfirmedSpeed;

    public SnakeSettingsPanel(double[] selection) {
        this.speedDelay = selection;
        this.unconfirmedSpeed = new double[1];
        setupPanel();
    }

    public void setupPanel() {

        BorderLayout layout = new BorderLayout(BORDER_GAP, BORDER_GAP);
        JFrame frame = new JFrame();
        frame.setLayout(layout);
        frame.setTitle("Settings");

        JLabel header = new JLabel("Speed Setting");
        GridLayout settingsLayout = new GridLayout(4, 1);
        JPanel panel = new JPanel();
        panel.setLayout(settingsLayout);

        JButton set = new JButton("Set Speed");
        set.addActionListener(new SetButtonListener(speedDelay, unconfirmedSpeed));

        speeds = new ButtonGroup();

        JRadioButtonMenuItem low = new JRadioButtonMenuItem();
        JRadioButtonMenuItem med = new JRadioButtonMenuItem();
        JRadioButtonMenuItem high = new JRadioButtonMenuItem();
        low.addActionListener(new SpeedButtonListener(.99, unconfirmedSpeed));
        med.addActionListener(new SpeedButtonListener(.95, unconfirmedSpeed));
        high.addActionListener(new SpeedButtonListener(.9, unconfirmedSpeed));
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

    public double getSpeed() {
        return this.speedDelay[0];
    }

    private class SpeedButtonListener implements ActionListener {

        private double speedValue;
        private double[] speedSelection;

        public SpeedButtonListener(double value, double[] unconfirmedSpeed) {
            super();
            this.speedValue = value;
            this.speedSelection = unconfirmedSpeed;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.speedSelection[0] = speedValue;
        }

    }

    private class SetButtonListener implements ActionListener {

        private double[] confirmedSpeedVal;
        private double[] speedSelection;

        public SetButtonListener(double[] speedValue, double[] speedSelection) {
            super();
            this.confirmedSpeedVal = speedValue;
            this.speedSelection = speedSelection;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            confirmedSpeedVal[0] = speedSelection[0];
            System.out.println("SET BUTTON PRESSED!");
            System.out.printf("Speed set to %f\n", confirmedSpeedVal[0]);

        }

    }
}
