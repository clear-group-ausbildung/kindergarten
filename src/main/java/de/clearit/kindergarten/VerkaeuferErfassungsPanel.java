package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VerkaeuferErfassungsPanel extends JPanel implements ActionListener{
  private JButton neu;
  private JButton config;
  private JButton delete;
  private JButton save;
  private JPanel verkaeuferErfassungsPanel;
  
  private String textVorname;
  private String textNachname;
  private Integer nummer;
  private String textzusatz;
  
  private final JLabel vorname = new JLabel("Vorname:");
  private JTextField textFieldVorname = new JTextField(15);
  private final JLabel nachname = new JLabel("Familienname:");
  private JTextField textFieldNachname = new JTextField(15);
  private final JLabel telefon = new JLabel("Telefon:");
  private JTextField telefonField = new JTextField(15);
  private final JLabel zusatz = new JLabel("Zusatz:");
  private JTextField zusatzField = new JTextField(15);
  
  private JTable tabelle;
  private DefaultTableModel dataTabelle;
  private JPanel tablePanel;
  
    public VerkaeuferErfassungsPanel() {
      
      verkaeuferErfassungsPanel = new JPanel();
      verkaeuferErfassungsPanel.setBackground(Color.LIGHT_GRAY);
      
      verkaeuferErfassungsPanel.setLayout(new GridBagLayout());
      GridBagConstraints gc = new GridBagConstraints();
      
      /// Tabelle für die Anzeige der Erfassten Verkäufer ///
      
      tablePanel = new JPanel();
      
      String [] spalten = {"Vorname", "Familienname", "Telefon", "Zusatz"};
      
      Object gruppeA[][] = {
          {"Kevin", "Adam", new Integer(473391), "keinen"},
          {"Kenan", "Horoz", new Integer(475691), "keinen"},
          {"Kevin", "Dauemler", new Integer(56789), "keinen"}
      };
      
      dataTabelle = new DefaultTableModel(gruppeA, spalten);
      
      tabelle = new JTable(dataTabelle);
      
      tablePanel.add(new JScrollPane(tabelle));
      
      /// Buttons ///
      
      neu = new JButton("Neu");
      config = new JButton("Bearbeiten");
      delete = new JButton("Löschen");
      save = new JButton("Speichern");
      
      neu.addActionListener(this);
      config.addActionListener(this);
      delete.addActionListener(this);
      save.addActionListener(this);
      
      neu.setPreferredSize(new Dimension(100, 20));
      config.setPreferredSize(new Dimension(100, 20));
      delete.setPreferredSize(new Dimension(100, 20));
      save.setPreferredSize(new Dimension(100, 20));
      
      // Grid aufbau
      
      gc.weightx = 1;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.HORIZONTAL;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 4;
      gc.gridheight = 1;
      gc.insets = new Insets(0, 0, 0, 0);
      gc.gridx = 0;
      gc.gridy = 0;
      verkaeuferErfassungsPanel.add(tablePanel, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 0;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(neu, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 1;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(config, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 2;
      gc.gridy = 1;
      verkaeuferErfassungsPanel.add(delete, gc);
      
      /// Setze Save Button hinter delete! ///
      verkaeuferErfassungsPanel.add(save, gc);
      
      add(verkaeuferErfassungsPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton clicked = (JButton)e.getSource();
      JPanel erfassungsPanel = new JPanel();
      
      
      
      if(clicked == neu) {
        
        /// Labels + Save Button ///
        
        erfassungsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        /// 4 Elemente in erster Reihe ///
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        erfassungsPanel.add(vorname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        erfassungsPanel.add(textFieldVorname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 2;
        gc.gridy = 2;
        erfassungsPanel.add(nachname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 3;
        gc.gridy = 2;
        erfassungsPanel.add(textFieldNachname, gc);
        
        /// 4 Elemente in zweiter Reihe ///
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 3;
        erfassungsPanel.add(telefon, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 3;
        erfassungsPanel.add(telefonField, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 2;
        gc.gridy = 3;
        erfassungsPanel.add(zusatz, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 3;
        gc.gridy = 3;
        erfassungsPanel.add(zusatzField, gc);
        
        remove(save);
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 4;
        erfassungsPanel.add(save, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 3;
        gc.gridheight = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        verkaeuferErfassungsPanel.add(erfassungsPanel, gc);
        
        revalidate();
        
        System.out.println("Neu anlegen wurde gestartet!");
      }
      if(clicked == save) {
        textVorname = textFieldVorname.getText();
        textNachname = textFieldNachname.getText();
        nummer = Integer.parseInt(telefonField.getText());
        textzusatz = zusatzField.getText();
        
        /*verkaeuferErfassungsPanel.remove(save);
        verkaeuferErfassungsPanel.remove(vorname);
        verkaeuferErfassungsPanel.remove(textFieldVorname);
        verkaeuferErfassungsPanel.remove(nachname);
        verkaeuferErfassungsPanel.remove(textFieldNachname);
        verkaeuferErfassungsPanel.remove(telefon);
        verkaeuferErfassungsPanel.remove(telefonField);
        verkaeuferErfassungsPanel.remove(zusatz);
        verkaeuferErfassungsPanel.remove(zusatzField);
        */
        // JTable
        
        
        
        revalidate();
        System.out.println("Verkäufer wurde gespeichert!");
      }
    }
}