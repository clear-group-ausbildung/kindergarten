package de.clearit.kindergarten.application;

import java.awt.Component;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.Document;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;
import com.jgoodies.jsdl.core.util.JSDLUtils;

/**
 * Consists only of static method to open frequently used dialogs.
 *
 */
public final class Dialogs {

  private static final ResourceMap RESOURCES = Application.getResourceMap(Dialogs.class);

  // Instance Creation ******************************************************

  private Dialogs() {
    // Overrides default constructor; prevents instantiation.
  }

  // API ********************************************************************

  /**
   * Creates and shows a TaskPane to let the user choose how to proceed with a
   * close operation on valid data. Offers: Save, Don't Save and Cancel.
   * <p>
   *
   * Context: A document shall be closed, the document has changes, and the
   * validation has detected no validation errors.
   * 
   * @param e
   *          the event object
   * @param objectName
   *          describes the data to be closed, e.g. "BL 32168", "Invoice
   *          2007-10-67", "Unsaved", may be null, empty, or whitespace
   *
   * @return the TaskPane's commit value, one of: {@code CommandValue.SAVE},
   *         {@code CommandValue.DONT_SAVE}, {@code CommandValue.CANCEL}
   */
  public static Object showUnsavedChangesDialog(EventObject e, String objectName) {
    String title = RESOURCES.getString("dialogs.saveUnsavedChanges.title");
    boolean hasObject = JSDLUtils.isNotBlank(objectName);
    String mainInstruction = hasObject ? RESOURCES.getString("dialogs.saveUnsavedChanges.mainInstruction", objectName)
        : RESOURCES.getString("dialogs.saveUnsavedChanges.mainInstruction.noObject");
    TaskPane pane = new TaskPane(MessageType.PLAIN, // Routine confirmations have no icon.
        mainInstruction, CommandValue.SAVE, CommandValue.DONT_SAVE, CommandValue.CANCEL);
    pane.setPreferredWidth(PreferredWidth.LARGE);
    pane.showDialog(e, title);
    return pane.getCommitValue();
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static boolean unsavedDocuments(EventObject e) {
    String title = RESOURCES.getString("unsavedDocuments.title");
    String mainInstruction = RESOURCES.getString("unsavedDocuments.mainInstruction");
    List<?> unsavedDocuments = DesktopManager.INSTANCE.getUnsavedDocuments();
    JList list = new JList(new Vector(unsavedDocuments));
    list.setCellRenderer(new DocumentsCellRenderer());
    Object saveSelected = RESOURCES.getString("unsavedDocuments.saveSelected");
    Object discardSelected = RESOURCES.getString("unsavedDocuments.discardSelected");

    TaskPane pane = new TaskPane(mainInstruction, new JScrollPane(list), saveSelected, discardSelected,
        CommandValue.CANCEL);
    pane.setMarginContentTop(2);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, title);

    if (pane.getCommitValue() == saveSelected) {
      MainModel.getInstance().saveDocuments(getSelectedDocuments(list));
    } else if (pane.getCommitValue() == discardSelected) {
      MainModel.getInstance().discardDocuments(getSelectedDocuments(list));
    }
    return pane.isCancelled();
  }

  /**
   * Checks if the document has errors.
   * 
   * @param e
   *          the event object
   * @param document
   *          the document
   * @return {@code true} if canceled, {@code false} otherwise
   */
  public static boolean documentHasErrors(EventObject e, Document document) {
    String title = RESOURCES.getString("dialogs.documentHasErrors.title");
    String mainInstruction = RESOURCES.getString("dialogs.documentHasErrors.mainInstruction", document
        .getDisplayString());
    String contentText = RESOURCES.getString("dialogs.documentHasErrors.content", document.getDisplayString());

    TaskPane pane = new TaskPane(MessageType.WARNING, mainInstruction, contentText, CommandValue.DISCARD,
        CommandValue.CANCEL);
    pane.setMarginContentTop(4);
    pane.setMarginContentBottom(14);

    pane.showDialog(e, title);
    return pane.isCancelled();
  }

  public static void about(EventObject e) {
    String title = RESOURCES.getString("dialogs.about.title");
    String mainInstruction = RESOURCES.getString("application.name") + " " + RESOURCES.getString("application.version");
    String contentText = RESOURCES.getString("dialogs.about.content", RESOURCES.getString("application.copyright"),
        RESOURCES.getString("application.buildNo"), RESOURCES.getDate("application.buildDate"));

    TaskPane pane = new TaskPane(RESOURCES.getIcon("application.logo"), mainInstruction, contentText,
        CommandValue.CLOSE);
    pane.setMarginContentTop(4);
    pane.setMarginContentBottom(14);

    pane.showDialog(e, title);
  }

  public static void mustNotBeBlank(EventObject e, String titleName, String mainInstructionName) {
    TaskPane pane = new TaskPane(MessageType.WARNING, mainInstructionName + " darf nicht leer sein.", CommandValue.OK);
    pane.setPreferredWidth(PreferredWidth.MEDIUM);
    pane.showDialog(e, titleName + " leer");
  }

  // Helper Code ************************************************************

  @SuppressWarnings("deprecation")
  private static List<Document> getSelectedDocuments(JList<?> list) {
    Object[] values = list.getSelectedValues();
    List<Document> result = new LinkedList<Document>();
    for (Object element : values) {
      result.add((Document) element);
    }
    return result;
  }

  private static final class DocumentsCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
        boolean cellHasFocus) {
      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      Document document = (Document) value;
      setText(document.getDisplayString());
      return this;
    }
  }
}