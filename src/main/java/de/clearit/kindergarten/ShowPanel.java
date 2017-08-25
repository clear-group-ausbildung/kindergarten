package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowPanel extends JPanel {

  public JFrame frame;
  private MainPanel mainPanel;

  public ShowPanel() {
    frame = new JFrame("Kassenprogramm V. 1.0.0");
    
    Container c = frame.getContentPane();
    c.setLayout(new BorderLayout());
    mainPanel = new MainPanel();
    c.add(mainPanel, BorderLayout.WEST);

    
    frame.setSize(1000, 900);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }  
}
