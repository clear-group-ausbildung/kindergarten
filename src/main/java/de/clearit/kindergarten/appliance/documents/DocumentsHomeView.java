package de.clearit.kindergarten.appliance.documents;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListModel;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.desktop.DesktopFrame;
import com.jgoodies.desktop.Document;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.StripedTable;
import com.jgoodies.uif2.util.ComponentUtils;
import com.jgoodies.uif2.util.TableUtils;

import de.clearit.kindergarten.appliance.HomeViewBuilder;

/**
 * Builds the table view for the All Documents view.
 */
public final class DocumentsHomeView extends AbstractView {

  private static DocumentsHomeView instance;

  private static final ResourceMap RESOURCES = Application.getResourceMap(DocumentsHomeView.class);

  private final DocumentsHomeModel model;
  private JTable table;

  // Instance Creation ******************************************************

  private DocumentsHomeView(DocumentsHomeModel model) {
    this.model = model;
  }

  public static DocumentsHomeView getInstance() {
    if (instance == null) {
      instance = new DocumentsHomeView(DocumentsHomeModel.getInstance());
    }
    return instance;
  }

  // Building ***************************************************************

  private void initComponents() {
    table = new StripedTable(new DocumentFrameTableModel(model.getSelectionInList()));
    table.setSelectionModel(new SingleListSelectionAdapter(model.getSelectionInList().getSelectionIndexHolder()));
    TableUtils.configureColumns(table, "60dlu, pref");
    Action openAction = model.getAction(DocumentsHomeModel.ACTION_SHOW_ITEM);
    ComponentUtils.registerDoubleClickAction(table, openAction);
  }

  @Override
  protected JComponent buildPanel() {
    initComponents();

    HomeViewBuilder builder = new HomeViewBuilder();
    builder.setTitle(RESOURCES.getString("documentsHome.mainInstruction"));
    builder.setListView(table);
    builder.setListBar(model.getActionMap(), DocumentsHomeModel.ACTION_SHOW_ITEM, DocumentsHomeModel.ACTION_CLOSE_ITEM);

    return builder.getPanel();
  }

  // TableModel *************************************************************

  private static final class DocumentFrameTableModel extends AbstractTableAdapter<DesktopFrame> {

    private static final long serialVersionUID = 1L;

    DocumentFrameTableModel(ListModel<?> listModel) {
      super(listModel, getColumnNames());
    }

    private static String[] getColumnNames() {
      return new String[] { RESOURCES.getString("document.unsaved"), RESOURCES.getString("document.displayString"), };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      DesktopFrame frame = getRow(rowIndex);
      Document document = frame.document();
      switch (columnIndex) {
      case 0:
        return document.isSaveRequired() ? "o" : "";

      case 1:
        return document.getDisplayString();

      default:
        throw new IllegalStateException("Can't handle column index " + columnIndex);
      }
    }

  }

}