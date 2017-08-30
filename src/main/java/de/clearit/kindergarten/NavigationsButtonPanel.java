package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationsButtonPanel extends JPanel implements ActionListener {
  
  private JButton verkaufer;
  private JButton einkauf;
  private JButton abrechnung;
  private JButton drucken;
  private VerkaeuferErfassungsPanel vk;
  private EinkaufsErfassungsPanel ek;
  private int clicker;
  
  private JPanel panel = new JPanel();
  private JPanel showPanel = new JPanel();
  private GridBagConstraints grid = new GridBagConstraints();
  
  public NavigationsButtonPanel() {
    
    panel.setLayout(new GridBagLayout());
    
    // Erstellung der verschiedenen Reiter 
    
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
    
    
    // Positionierung der Reiter untereinander
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.fill = GridBagConstraints.NONE;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 0;
    
    panel.add(verkaufer, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 1;
    
    panel.add(einkauf, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 2;
    
    panel.add(abrechnung, grid);
    
    grid.weightx = 1;
    grid.weighty = 0;
    
    grid.anchor = GridBagConstraints.FIRST_LINE_START;
    grid.gridwidth = 2;
    grid.gridheight = 1;
    grid.gridx = 0;
    grid.gridy = 3;
    
    panel.add(drucken, grid);
    
    panel.setBorder(BorderFactory.createEtchedBorder());
    
    grid.weightx = 0;
    grid.weighty = 0.1;
    grid.gridwidth = 6;
    grid.gridheight = 1;
    grid.anchor = GridBagConstraints.LINE_START;
    grid.gridx = 0;
    grid.gridy = 1;
    add(panel, grid);
    clicker = 0;
  }
 
  
  // ActionListener der Reiter Button
  
  @Override
  public void actionPerformed(ActionEvent e) {
    JButton clicked = (JButton)e.getSource();
    //showPanel.setBackground(Color.ORANGE);
     //remove(alles andere von anderen Buttons falls vorhanden);
      
     if(clicked == verkaufer) {
       if(clicker != 1) {
        vk = new VerkaeuferErfassungsPanel();
        showPanel.removeAll();
        clicker = 1;
        
        showPanel.add(vk, BorderLayout.CENTER);
        
        grid.weightx = 1;
        grid.weighty = 0.1;
        
        grid.anchor = GridBagConstraints.LINE_START;
        grid.gridwidth = 4;
        grid.gridheight = 8;
        grid.gridx = 3;
        grid.gridy = 0;
        panel.add(showPanel, grid);
        
        revalidate();
        
        System.out.println("Verkäufer übersicht!");
       }
     }
     
     // EINKAUFSERFASSUNG 
     if(clicked == einkauf) {
       if(clicker != 2) {
        ek = new EinkaufsErfassungsPanel();
        clicker = 2;
        showPanel.removeAll();
        
        showPanel.add(ek, BorderLayout.CENTER);
        
        grid.weightx = 1;
        grid.weighty = 0.1;
        
        grid.anchor = GridBagConstraints.LINE_START;
        grid.gridwidth = 4;
        grid.gridheight = 8;
        grid.gridx = 3;
        grid.gridy = 0;
        panel.add(showPanel, grid);
        
        revalidate();
        System.out.println("REMOVE");
       }
        System.out.println("Einkauf übersicht!");
     }
     
     // ABRECHNUNSÜBERSICHT
     if(clicker != 3) {
      if(clicked == abrechnung) {
        showPanel.removeAll();
        clicker = 3;
        // abrechnungsPanel = new AbrechnunsPanel();
        revalidate();
        System.out.println("Abrechnung übersicht!");
      }
     }
     
     //DRUCKOPTION
     if(clicker != 4) {
      if(clicked == drucken) {
        showPanel.removeAll();
        clicker = 4;
        revalidate();
        System.out.println("Drucken!");
      }
    }
  }
}
