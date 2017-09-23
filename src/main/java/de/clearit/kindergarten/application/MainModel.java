package de.clearit.kindergarten.application;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.jgoodies.application.Action;
import com.jgoodies.application.ExitListener;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.Document;
import com.jgoodies.help.HelpBroker;
import com.jgoodies.jsdl.core.util.HyperlinkAdapter;
import com.jgoodies.uif2.application.UIFModel;

/**
 * A central "Presentation Model" that provides application-wide behavior,
 * handles the application shutdown process, configures the JSDL hyperlink
 * handling. Part of the shutdown process is to save or discard unsaved
 * Documents.
 *
 */
public final class MainModel extends UIFModel implements ExitListener {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = Logger.getLogger(MainModel.class.getName());

  private static MainModel instance;

  // Instance Creation ******************************************************

  private MainModel() {
    super();
  }

  public static MainModel getInstance() {
    if (instance == null) {
      instance = new MainModel();
    }
    return instance;
  }

  // Action Implementation **************************************************

  /**
   * Aims to shutdown the application. May fail, if someone vetoes against the
   * shutdown.
   *
   * @param e
   *          the event that caused the exit
   */
  @Action
  public void exitApplication(ActionEvent e) {
    DesktopManager.closeModalFramesAndExitApplication(e);
  }

  @Action
  public void openAboutDialog(ActionEvent e) {
    Dialogs.about(e);
  }

  // Application Shutdown ***************************************************

  @Override
  public boolean applicationExitAllowed(EventObject e) {
    List<Document> unsavedDocuments = DesktopManager.INSTANCE.getUnsavedDocuments();
    if (unsavedDocuments.isEmpty()) {
      return true;
    }

    boolean cancelled = Dialogs.unsavedDocuments(e);
    return !cancelled;
  }

  /**
   * Persists application, desktop and model state - if any.
   */
  @Override
  public void applicationExiting() {
    LOGGER.fine("MainModel shutdown.");
  }

  void saveDocuments(List<Document> list) {
    for (Document document : list) {
      LOGGER.fine("Saving document:" + document.getDisplayString());
    }
  }

  void discardDocuments() {
    LOGGER.fine("Discarding selected documents");
  }

  // Hyperlink Handling *****************************************************

  HyperlinkListener createDefaultHyperlinkHandler() {
    return new HelpHyperlinkHandler();
  }

  private static final class HelpHyperlinkHandler extends HyperlinkAdapter {
    @Override
    protected void hyperlinkActivated(HyperlinkEvent e) {
      HelpBroker.open(e.getDescription());
    }
  }

}