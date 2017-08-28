/*

Noch zu erledigen ist das sich die Buttons Verk‰ufer, Einkauf, Abrechnung und Drucken - NICHT mit nach unten
verschieben. 

Habe bis jetzt leider keine Lˆsung gefunden und werde dies erstmal Pausieren.

*/
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolbarPanel extends JPanel implements ActionListener{

  private JButton menu;
  private JButton config;
  private NavigationsButtonPanel navigationsButtonPanel;
  private ConfigBtn configBtn;
  private int importcounter, configcounter, clickcounter;

  private JPanel panel = new JPanel();
  private GridBagConstraints grid = new GridBagConstraints();
  
  public ToolbarPanel() {
    
    // Button f¸r Auswahl Men¸ oder Bearbeiten
    menu = new JButton("Men¸");
    config = new JButton("Bearbeiten");
    
    menu.setPreferredSize(new Dimension(100, 25));
    config.setPreferredSize(new Dimension(100, 25));

    menu.addActionListener(this);
    config.addActionListener(this);
    
    menu.setSelected(true);
    
    // Counter zum z‰hlen der Klicks
    
    importcounter = 1;
    configcounter = 1;
    clickcounter = 0;
    
    panel.setLayout(new GridBagLayout());
    
    grid.weightx = 0;
    grid.weighty = 0.1;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.fill = GridBagConstraints.NONE;
    grid.insets = new Insets(0, 5, 0, 0);
    grid.gridx = 0;
    grid.gridy = 0;
    panel.add(menu, grid);
    
    grid.weightx = 0;
    grid.weighty = 0.1;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.fill = GridBagConstraints.NONE;
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridx = 1;
    grid.gridy = 0;
    panel.add(config, grid);
    
    grid.weightx = 0;
    grid.weighty = 0.1;
    grid.gridwidth = 8;
    grid.gridheight = 1;
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.insets = new Insets(0, 0, 0, 0);
    grid.gridx = 1;
    grid.gridy = 0;
    add(panel, grid);
  }

  // Pr¸ft ob eine/welche Aktion get‰tigt wurde - w‰hlt aus was der User sehen mˆchte

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    if(importcounter == 1) {
      if(clicked == menu) {
        navigationsButtonPanel = new NavigationsButtonPanel();
        //navigationsPanel.setBackground(Color.BLUE);
        importcounter++;
        configcounter = 1;
        if(clickcounter == 2) {
          panel.remove(configBtn);
          revalidate();
        }
        clickcounter = 1;
        
        // Positionierung der Navigations Button - f¸r das Men¸

        grid.weightx = 0;
        grid.weighty = 0.1;
        grid.gridwidth = 2;
        grid.gridheight = 1;
        grid.anchor = GridBagConstraints.NORTH;
        grid.insets = new Insets(5, 0, 0, 0);
        grid.gridx = 0;
        grid.gridy = 3;
        panel.add(navigationsButtonPanel, grid);
        revalidate();
        System.out.printf("Men¸ wurde bet‰tigt\n");
      }
    }
    
    // Bearbeiten Button -> ActionListener
    
    if(configcounter == 1) {
      if(clicked == config) {
        configBtn = new ConfigBtn();
        configcounter++;
        importcounter = 1;
        if(clickcounter == 1) {
          panel.remove(navigationsButtonPanel);
          revalidate();
        }
        clickcounter= 2;

        // Positionierung der Navigations Button - f√ºr die Bearbeitung

        grid.weightx = 0;
        grid.weighty = 0.1;
        grid.gridwidth = 2;
        grid.insets = new Insets(5, 0, 0, 0);
        grid.gridx = 0;
        grid.gridy = 1;
        panel.add(configBtn, grid);
        revalidate();
        System.out.printf("Bearbeiten wurde bet‰tigt\n");
      }
    }
  }

}
