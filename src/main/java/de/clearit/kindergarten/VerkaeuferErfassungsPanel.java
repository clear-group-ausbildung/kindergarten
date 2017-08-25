package de.clearit.kindergarten;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class VerkaeuferErfassungsPanel extends JPanel implements ActionListener{
  private JButton neu;
  private JButton config;
  private JButton delete;
  private JButton save;
  private JTextArea txtArea;
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
  
  private ArrayList<Verkaeufer> data = new ArrayList<>();
  
    public VerkaeuferErfassungsPanel() {
      
      verkaeuferErfassungsPanel = new JPanel();
      verkaeuferErfassungsPanel.setBackground(Color.LIGHT_GRAY);
      
      txtArea = new JTextArea();
      txtArea.setPreferredSize(new Dimension(600, 400));
      
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
      
      verkaeuferErfassungsPanel.setLayout(new GridBagLayout());
      GridBagConstraints gc = new GridBagConstraints();
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.gridwidth = 4;
      gc.gridheight = 5;
      gc.fill = GridBagConstraints.BOTH;
      gc.anchor = GridBagConstraints.CENTER;
      gc.insets = new Insets(0, 0, 0, 0);
      gc.gridx = 1;
      gc.gridy = 0;
      verkaeuferErfassungsPanel.add(txtArea, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 1;
      gc.gridy = 9;
      verkaeuferErfassungsPanel.add(neu, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 2;
      gc.gridy = 9;
      verkaeuferErfassungsPanel.add(config, gc);
      
      gc.weightx = 0;
      gc.weighty = 0.1;
      gc.fill = GridBagConstraints.NONE;
      gc.anchor = GridBagConstraints.LINE_START;
      gc.gridwidth = 1;
      gc.gridheight = 1;
      gc.insets = new Insets(5, 0, 0, 0);
      gc.gridx = 3;
      gc.gridy = 9;
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
        gc.gridx = 1;
        gc.gridy = 10;
        erfassungsPanel.add(vorname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 2;
        gc.gridy = 10;
        erfassungsPanel.add(textFieldVorname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 3;
        gc.gridy = 10;
        erfassungsPanel.add(nachname, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 4;
        gc.gridy = 10;
        erfassungsPanel.add(textFieldNachname, gc);
        
        /// 4 Elemente in zweiter Reihe ///
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 11;
        erfassungsPanel.add(telefon, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 5);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 2;
        gc.gridy = 11;
        erfassungsPanel.add(telefonField, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 3;
        gc.gridy = 11;
        erfassungsPanel.add(zusatz, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 4;
        gc.gridy = 11;
        erfassungsPanel.add(zusatzField, gc);
        
        remove(save);
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 12;
        erfassungsPanel.add(save, gc);
        
        gc.weightx = 0;
        gc.weighty = 0.1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(5, 0, 0, 0);
        gc.gridwidth = 3;
        gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 12;
        verkaeuferErfassungsPanel.add(erfassungsPanel, gc);
        
        revalidate();
        
        System.out.println("Neu anlegen wurde gestartet!");
      }
      if(clicked == save) {
        textVorname = textFieldVorname.getText();
        textNachname = textFieldNachname.getText();
        nummer = Integer.parseInt(telefonField.getText());
        textzusatz = zusatzField.getText();
        
        remove(save);
        remove(vorname);
        remove(textFieldVorname);
        remove(nachname);
        remove(textFieldNachname);
        remove(telefon);
        remove(telefonField);
        remove(zusatz);
        remove(zusatzField);
        
        data.add(new Verkaeufer(textVorname, textNachname, nummer, textzusatz));
        
        revalidate();
        System.out.println("Verkäufer wurde gespeichert!");
      }
    }
}