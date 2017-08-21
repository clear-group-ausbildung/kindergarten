package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Toolbar extends JPanel implements ActionListener{

  private JButton menu;
  private JButton config;
  private NavigationsBtn naviBtn;
  private ConfigBtn configBtn;
  private int importcounter, configcounter, clickcounter;

  public Toolbar() {
    menu = new JButton("Menü");
    config = new JButton("Bearbeiten");
    importcounter = 1;
    configcounter = 1;
    clickcounter = 0;

    menu.setPreferredSize(new Dimension(100, 25));
    config.setPreferredSize(new Dimension(100, 25));

    menu.addActionListener(this);
    config.addActionListener(this);

    menu.setSelected(true);

    add(menu);
    add(config);
  }

  /// Prüft ob eine/welche Aktion getätigt wurde - wählt aus was der User sehen möchte ///

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    if(importcounter == 1) {
      if(clicked == menu) {
        naviBtn = new NavigationsBtn();
        importcounter++;
        configcounter = 1;
        if(clickcounter == 2) {
          remove(configBtn);
          revalidate();
        }
        clickcounter = 1;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        /// Positionierung der Navigations Button - fÃ¼r das MenÃ¼///

        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.gridwidth = 200;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 1;
        add(naviBtn, gc);
        revalidate();
        System.out.printf("Menü wurde betätigt\n");
      }
    }
    if(configcounter == 1) {
      if(clicked == config) {
        configBtn = new ConfigBtn();
        configcounter++;
        importcounter = 1;
        if(clickcounter == 1) {
          remove(naviBtn);
          revalidate();
        }
        clickcounter= 2;

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        /// Positionierung der Navigations Button - fÃ¼r die Bearbeitung///

        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.gridwidth = 200;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridx = 0;
        gc.gridy = 1;
        add(configBtn, gc);
        revalidate();
        System.out.printf("Bearbeiten wurde betätigt\n");
      }
    }
  }

}
