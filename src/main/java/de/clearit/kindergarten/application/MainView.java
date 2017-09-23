package de.clearit.kindergarten.application;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.CardPanel;

import de.clearit.kindergarten.desktop.DesktopView;

/**
 * Builds the main view that switches between the login panels (login, logging
 * in, logging out) and the main desktop view.
 *
 */
final class MainView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(MainView.class);

  @SuppressWarnings("unused")
  private final MainModel mainModel;

  private CardPanel cardPanel;

  // Instance Creation ******************************************************

  public MainView(MainModel mainModel) {
    this.mainModel = mainModel;
    initEventHandling();
  }

  private void initEventHandling() {
    updateFrameTitleFormat();
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    cardPanel = new CardPanel();
    cardPanel.add("Desktop", new DesktopView().getPanel());

    return cardPanel;
  }

  // Event Handling *********************************************************

  private void updateFrameTitleFormat() {
    DesktopManager.INSTANCE.setTitleFormat(RESOURCES.getString("frame.title.format"));
  }

}