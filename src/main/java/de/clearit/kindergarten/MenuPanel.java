package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

  private JPanel menuPanel;
  private ToolbarPanel toolbar;

  public MenuPanel() {
    menuPanel = new JPanel();
    toolbar = new ToolbarPanel();
    menuPanel.add(toolbar, BorderLayout.NORTH);
    
    add(menuPanel);
  }
  
}
