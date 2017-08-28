package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JPanel {

  public JFrame frame;
  private ToolbarPanel menuPanel;

  public MainFrame() {
    frame = new JFrame("Kassenprogramm V. 1.0.0");
    
    Container c = frame.getContentPane();
    c.setLayout(new BorderLayout());
    menuPanel = new ToolbarPanel();
    menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    menuPanel.setBackground(Color.BLACK);
    c.add(menuPanel, BorderLayout.NORTH);

    
    frame.setSize(1000, 900);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }  
}
