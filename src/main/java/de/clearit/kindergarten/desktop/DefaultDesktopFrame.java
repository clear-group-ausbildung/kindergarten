package de.clearit.kindergarten.desktop;

import java.util.EventObject;

import javax.swing.JComponent;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.Appliance;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.Document;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.desktop.spec.ToolBarSpec;
import com.jgoodies.jsdl.core.CloseRequestHandler;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;

/**
 * Describes ...
 * <p>
 *
 * TODO: To improve the application startup time, we may add a constructor that
 * gets a bunch of methods (or method names) that build the view lazily.
 *
 */
public final class DefaultDesktopFrame extends DesktopFrame {

  private static final long serialVersionUID = 1L;

  private static final ResourceMap RESOURCES = Application.getResourceMap(DefaultDesktopFrame.class);

  // Instance Fields ********************************************************

  private final ToolBarSpec contextSpec;
  private final NavigationBarSpec navigationSpec;
  private final JComponent statusPane;

  // Instance Creation ****************************************************

  /**
   * Constructs a default desktop frame with the given data.
   * 
   * @param parent
   *          the parent frame
   * @param title
   *          the frame title
   * @param modal
   *          yes / no
   * @param appliance
   *          the appliance
   * @param document
   *          the document
   * @param contextSpec
   *          the toolbar spec
   * @param navigationSpec
   *          the navigation bar spec
   * @param contentPane
   *          the view to be put into the content area
   * @param statusPane
   *          the view to be displayed in the status area
   */
  public DefaultDesktopFrame(DesktopFrame parent, String title, boolean modal, Appliance appliance, Document document,
      ToolBarSpec contextSpec, NavigationBarSpec navigationSpec, JComponent contentPane, JComponent statusPane) {
    super(parent, title, modal, appliance, document, contentPane);
    this.contextSpec = contextSpec;
    this.navigationSpec = navigationSpec;
    this.statusPane = statusPane;
  }

  // Modality ***************************************************************

  /**
   * {@inheritDoc}
   */
  @Override
  public void modalFrameClosing(EventObject e, Runnable postOperation) {
    if (!(contentPane() instanceof CloseRequestHandler)) {
      rejectFrameClosing(e);
      return;
    }
    CloseRequestHandler handler = (CloseRequestHandler) contentPane();
    handler.paneClosing(e, postOperation);
  }

  // Desktop Contribution ***************************************************

  /**
   * Returns this page's context actions. These will typically be displayed in a
   * tool bar.
   * 
   * @return the ToolBarSpec
   */
  public ToolBarSpec contextSpec() {
    return contextSpec;
  }

  /**
   * Returns this page's navigation actions. These will typically be displayed in
   * a side bar.
   * 
   * @return the NavigationBarSpec
   */
  public NavigationBarSpec navigationSpec() {
    return navigationSpec;
  }

  /**
   * Returns the view that will be displayed in the desktop's status area.
   *
   * @return this context's status view
   */
  public JComponent statusPane() {
    return statusPane;
  }

  // Helper Code ************************************************************

  /**
   * Handles the case where a frame close request has been rejected. Opens a task
   * dialog that informs about the rejected request.
   *
   * @param e
   *          describes the rejected event
   *
   * @see #modalFrameClosing(EventObject, Runnable)
   */
  private void rejectFrameClosing(EventObject e) {
    TaskPane pane = new TaskPane(MessageType.INFORMATION, RESOURCES.getString(
        "desktop.rejectFrameClosing.mainInstructionText"), RESOURCES.getString(
            "desktop.rejectFrameClosing.contentText"), CommandValue.OK);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, RESOURCES.getString("desktop.rejectFrameClosing.title"));
  }

}