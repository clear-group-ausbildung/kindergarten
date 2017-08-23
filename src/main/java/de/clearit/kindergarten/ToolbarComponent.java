package de.clearit.kindergarten;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ToolbarComponent extends AbstractComponent {

  private JButton menuButton;
  private JButton configButton;
  private NavigationComponent navigationComponent;
  private ConfigurationComponent configurationComponent;
  private JPanel contentPanel;
  private ActionListener clickCountListener;
  private int importCount;
  private int configCount;
  private int clickCount;

  // Instance Creation ******************************************************

  public ToolbarComponent() {
    super();
    importCount = 1;
    configCount = 1;
    clickCount = 0;
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();
    initActionHandling();

    contentPanel.add(menuButton);
    contentPanel.add(configButton);
    return contentPanel;
  }

  // Initialization *********************************************************

  private void initComponents() {
    menuButton = new JButton("Menue");
    configButton = new JButton("Bearbeiten");
    importCount = 1;
    configCount = 1;
    clickCount = 0;

    menuButton.setPreferredSize(new Dimension(100, 25));
    configButton.setPreferredSize(new Dimension(100, 25));
    contentPanel = new JPanel();
  }

  private void initActionHandling() {
    clickCountListener = new ClickCountListener();
    menuButton.addActionListener(clickCountListener);
    configButton.addActionListener(clickCountListener);
    menuButton.setSelected(true);
  }

  private final class ClickCountListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton clicked = (JButton) e.getSource();
      if (importCount == 1) {
        if (clicked == menuButton) {
          navigationComponent = new NavigationComponent();
          importCount++;
          configCount = 1;
          if (clickCount == 2) {
            contentPanel.remove(configurationComponent.getPanel());
            contentPanel.revalidate();
          }
          clickCount = 1;

          contentPanel.setLayout(new GridBagLayout());
          GridBagConstraints gc = new GridBagConstraints();

          /// Positionierung der Navigations Button - für das Menü///

          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.gridwidth = 200;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridx = 0;
          gc.gridy = 1;
          contentPanel.add(navigationComponent.getPanel(), gc);
          contentPanel.revalidate();
          System.out.printf("Menue wurde betaetigt\n");
        }
      }
      if (configCount == 1) {
        if (clicked == configButton) {
          configurationComponent = new ConfigurationComponent();
          configCount++;
          importCount = 1;
          if (clickCount == 1) {
            contentPanel.remove(navigationComponent.getPanel());
            contentPanel.revalidate();
          }
          clickCount = 2;

          contentPanel.setLayout(new GridBagLayout());
          GridBagConstraints gc = new GridBagConstraints();
          /// Positionierung der Navigations Button - für die Bearbeitung///

          gc.weightx = 0;
          gc.weighty = 0.1;
          gc.gridwidth = 200;
          gc.insets = new Insets(5, 0, 0, 0);
          gc.gridx = 0;
          gc.gridy = 1;
          contentPanel.add(configurationComponent.getPanel(), gc);
          contentPanel.revalidate();
          System.out.printf("Bearbeiten wurde betaetigt\n");
        }
      }

    }

  }

}
