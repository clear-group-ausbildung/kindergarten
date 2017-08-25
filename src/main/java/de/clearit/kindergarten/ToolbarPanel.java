package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolbarPanel extends JPanel implements ActionListener{

  private JButton menu;
  private JButton config;
  private NavigationsPanel navigationsPanel;
  private ConfigBtn configBtn;
  private int importcounter, configcounter, clickcounter;

  private JPanel panel = new JPanel();
  
  public ToolbarPanel() {
    
    panel.setBackground(Color.BLACK);
    
    /// Button f¸r Auswahl Men¸ oder Bearbeiten ///
    menu = new JButton("Men¸");
    config = new JButton("Bearbeiten");
    
    menu.setPreferredSize(new Dimension(100, 25));
    config.setPreferredSize(new Dimension(100, 25));

    menu.addActionListener(this);
    config.addActionListener(this);
    
    menu.setSelected(true);
    
    /// Counter zum z‰hlen der Klicks ///
    
    importcounter = 1;
    configcounter = 1;
    clickcounter = 0;
    
    
    panel.add(menu);
    panel.add(config);
    add(panel, BorderLayout.NORTH);
  }

  /// Pr¸ft ob eine/welche Aktion get‰tigt wurde - w‰hlt aus was der User sehen mˆchte ///

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    if(importcounter == 1) {
      if(clicked == menu) {
        navigationsPanel = new NavigationsPanel();
        navigationsPanel.setBackground(Color.BLUE);
        importcounter++;
        configcounter = 1;
        if(clickcounter == 2) {
          remove(configBtn);
          revalidate();
        }
        clickcounter = 1;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        /// Positionierung der Navigations Button - f¸r das Men¸///

        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.gridwidth = 2;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 1;
        add(navigationsPanel, gc);
        revalidate();
        System.out.printf("Men¸ wurde bet‰tigt\n");
      }
    }
    
    /// Bearbeiten Button -> ActionListener///
    
    if(configcounter == 1) {
      if(clicked == config) {
        configBtn = new ConfigBtn();
        configcounter++;
        importcounter = 1;
        if(clickcounter == 1) {
          remove(navigationsPanel);
          revalidate();
        }
        clickcounter= 2;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        /// Positionierung der Navigations Button - f√ºr die Bearbeitung///

        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.gridwidth = 2;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 1;
        add(configBtn, gc);
        revalidate();
        System.out.printf("Bearbeiten wurde bet‰tigt\n");
      }
    }
  }

}
