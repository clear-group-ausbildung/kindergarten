package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class NavigationComponent extends AbstractComponent {

  private JButton verkaeuferButton;
  private JButton einkaufButton;
  private JButton abrechnungButton;
  private JButton druckenButton;
  private VerkaeuferHomeComponent verkaeuferHomeComponent;
  private JPanel contentPanel;
  private ActionListener clickCountListener;
  private int clickCount;

  // Instance Creation ******************************************************

  public NavigationComponent() {
    this.clickCount = 0;
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();
    initActionHandling();

    contentPanel.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 0;
    contentPanel.add(verkaeuferButton, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 1;
    contentPanel.add(einkaufButton, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 2;
    contentPanel.add(abrechnungButton, grid);

    grid.weightx = 1;
    grid.weighty = 0;
    grid.gridx = 0;
    grid.gridy = 3;
    contentPanel.add(druckenButton, grid);

    return contentPanel;
  }

  // Initialization *********************************************************

  private void initComponents() {
    verkaeuferButton = new JButton("Verkaeufer");
    verkaeuferButton.setPreferredSize(new Dimension(200, 35));

    einkaufButton = new JButton("Einkauf");
    einkaufButton.setPreferredSize(new Dimension(200, 35));

    abrechnungButton = new JButton("Abrechnung");
    abrechnungButton.setPreferredSize(new Dimension(200, 35));

    druckenButton = new JButton("Drucken");
    druckenButton.setPreferredSize(new Dimension(200, 35));

    verkaeuferHomeComponent = new VerkaeuferHomeComponent();

    contentPanel = new JPanel();
  }

  private void initActionHandling() {
    clickCountListener = new ClickCountListener();
    verkaeuferButton.addActionListener(clickCountListener);
    einkaufButton.addActionListener(clickCountListener);
    abrechnungButton.addActionListener(clickCountListener);
    druckenButton.addActionListener(clickCountListener);
  }

  private final class ClickCountListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton clicked = (JButton) e.getSource();
      if (clickCount != 1) {

        // remove(alles andere von anderen Buttons falls vorhanden);

        if (clicked == verkaeuferButton) {
          clickCount = 1;
          contentPanel.setLayout(new GridBagLayout());
          GridBagConstraints grid = new GridBagConstraints();
          /// Menueleiste soll sich nicht veraendern ///
          grid.weightx = 1;
          grid.weighty = 0;
          grid.gridx = 0;
          grid.gridy = 0;
          contentPanel.add(verkaeuferButton, grid);

          grid.weightx = 1;
          grid.weighty = 0;
          grid.gridx = 0;
          grid.gridy = 1;
          contentPanel.add(einkaufButton, grid);

          grid.weightx = 1;
          grid.weighty = 0;
          grid.gridx = 0;
          grid.gridy = 2;
          contentPanel.add(abrechnungButton, grid);

          grid.weightx = 1;
          grid.weighty = 0;
          grid.gridx = 0;
          grid.gridy = 3;
          contentPanel.add(druckenButton, grid);

          /// Verkaeufer Erfassungs Maske ///

          grid.weightx = 1;
          grid.weighty = 0;
          grid.gridx = 1;
          grid.gridy = 0;
          contentPanel.add(verkaeuferHomeComponent.getPanel(), grid);
          contentPanel.revalidate();

          System.out.println("Verkaeufer Uebersicht!");
        }
      }
      if (clickCount != 2) {
        if (clicked == einkaufButton) {

          System.out.println("Einkauf Uebersicht!");
        }
      }
      if (clickCount != 3)
        if (clicked == abrechnungButton) {

          System.out.println("Abrechnung Uebersicht!");
        }
      if (clickCount != 4) {
        if (clicked == druckenButton) {

          System.out.println("Drucken!");
        }
      }

    }

  }

}
