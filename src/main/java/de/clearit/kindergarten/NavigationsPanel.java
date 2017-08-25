package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class NavigationsPanel extends JPanel {
  private NavigationsButtonPanel naviBtn;
  
  public NavigationsPanel() {
    naviBtn = new NavigationsButtonPanel();
    add(naviBtn, BorderLayout.SOUTH);
  }
}
