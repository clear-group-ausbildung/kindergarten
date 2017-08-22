package de.clearit.kindergarten.appliance.documents;

import java.awt.event.ActionEvent;

import javax.swing.ListModel;

import com.jgoodies.application.Action;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.DesktopManager;

import de.clearit.kindergarten.appliance.AbstractHomeModel;

/**
 * Provides the models and operations for the Documents home view.
 */
public final class DocumentsHomeModel extends AbstractHomeModel<DesktopFrame> {

  private static final long serialVersionUID = 1L;

  // Action Names ***********************************************************

  public static final String ACTION_SHOW_ITEM = "showItem";
  public static final String ACTION_CLOSE_ITEM = "closeItem";

  // ************************************************************************

  private static DocumentsHomeModel instance;

  // Instance Creation ******************************************************

  private DocumentsHomeModel() {
    super();
  }

  static DocumentsHomeModel getInstance() {
    if (instance == null) {
      instance = new DocumentsHomeModel();
    }
    return instance;
  }

  // Initialization *********************************************************

  @Override
  protected ListModel<?> getListModel() {
    return DesktopManager.INSTANCE.getOpenDocumentFrames();
  }

  // Presentation Logic *****************************************************

  @Override
  protected void handleSelectionChange(boolean hasSelection) {
    setActionEnabled(ACTION_SHOW_ITEM, hasSelection);
    boolean closeEnabled = hasSelection && getSelection().document().isSaveRequired();
    setActionEnabled(ACTION_CLOSE_ITEM, closeEnabled);
  }

  // Actions ****************************************************************

  @Override
  protected String[] contextActionNames() {
    return new String[] {};
  }

  @Action(enabled = false)
  public void showItem(ActionEvent e) {
    getSelection().setVisible(true);
  }

  @Action(enabled = false)
  public void closeItem(ActionEvent e) {
    getSelection().close();
  }

}