package view;

import javax.swing.*;
import java.awt.*;

public class CenteredWhitePanel extends JPanel {

    public CenteredWhitePanel(int width, int height) {
        setBackground(Color.WHITE);
        setLayout(null);

        setPreferredSize(new Dimension(width, height));

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
        setBounds(x, y, width, height);
    }
}
