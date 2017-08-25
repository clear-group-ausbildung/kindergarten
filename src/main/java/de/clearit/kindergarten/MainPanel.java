package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
  // Aufruf Menü Panel //
  private JPanel mainPanel;
  private MenuPanel menuPanel;
  
  public MainPanel() {
    mainPanel = new JPanel();
    menuPanel = new MenuPanel();
    mainPanel.setBackground(Color.GREEN);
    mainPanel.add(menuPanel, new FlowLayout(FlowLayout.LEFT));
    add(mainPanel);
  }
  
}
