import javax.swing.*;

public class SnakeSettingsPanel extends JPanel {

    private static final int PANEL_SIZE = 200;

    public SnakeSettingsPanel() {

        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        frame.add(panel);
        frame.setSize(PANEL_SIZE, PANEL_SIZE);
        frame.setVisible(true);
        
    }


}
