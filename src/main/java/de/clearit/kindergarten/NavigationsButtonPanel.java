package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationsButtonPanel extends JPanel implements ActionListener {

  private JButton verkaufer;
  private JButton einkauf;
  private JButton abrechnung;
  private JButton drucken;
  private VerkaeuferErfassungsPanel vk;
  private int clicker;

  public NavigationsButtonPanel() {
    JPanel panel = new JPanel();
    
    /// Erstellung der verschiedenen Reiter ///
    
    verkaufer = new JButton("Verkäufer");
    einkauf = new JButton("Einkauf");
    abrechnung = new JButton("Abrechnung");
    drucken = new JButton("Drucken");

    verkaufer.setPreferredSize(new Dimension(200, 35));
    einkauf.setPreferredSize(new Dimension(200, 35));
    abrechnung.setPreferredSize(new Dimension(200, 35));
    drucken.setPreferredSize(new Dimension(200, 35));

    verkaufer.addActionListener(this);
    einkauf.addActionListener(this);
    abrechnung.addActionListener(this);
    drucken.addActionListener(this);
    
    
    
    /// Positionierung der Reiter untereinander ///
    
    panel.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    
    grid.weightx = 1;
    grid.weighty = 0.1;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.fill = GridBagConstraints.NONE;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 0;
    panel.add(verkaufer, grid);

    grid.weightx = 1;
    grid.weighty = 0.1;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 1;
    panel.add(einkauf, grid);

    grid.weightx = 1;
    grid.weighty = 0.1;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 2;
    panel.add(abrechnung, grid);
    
    grid.weightx = 1;
    grid.weighty = 0.1;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.gridwidth = 1;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 3;
    panel.add(drucken, grid);
    add(panel, BorderLayout.NORTH);

    clicker = 0;
  }
 
  
  /// ActionListener der Reiter Button ///
  
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
     //remove(alles andere von anderen Buttons falls vorhanden);
      
     if(clicked == verkaufer) {
       if(clicker != 1) {
        vk = new VerkaeuferErfassungsPanel();
        clicker = 1;
        
        add(vk, BorderLayout.SOUTH);
        revalidate();
        
        System.out.println("Verkäufer übersicht!");
       }
     }
     if(clicked == einkauf) {
       if(clicker != 2) {
        remove(vk);
        clicker = 2;
        revalidate();
        System.out.println("Einkauf übersicht!");
      }
     }
     if(clicker != 3) {
      if(clicked == abrechnung) {
        clicker = 3;
        
        System.out.println("Abrechnung übersicht!");
      }
     }
     if(clicker != 4) {
      if(clicked == drucken) {
        clicker = 4;
        System.out.println("Drucken!");
      }
    }
  }
}
