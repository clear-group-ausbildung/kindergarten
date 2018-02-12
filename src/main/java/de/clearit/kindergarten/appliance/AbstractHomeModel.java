package de.clearit.kindergarten.appliance;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ListModel;

import org.apache.commons.collections4.CollectionUtils;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.desktop.spec.ToolBarSpec;
import com.jgoodies.uif2.application.UIFModel;

/**
 * The abstract superclass for models that represent state for an appliance home
 * view.
 *
 * @param <T>
 *          the type of the object to edit
 */
public abstract class AbstractHomeModel<T> extends UIFModel {

  private static final long serialVersionUID = 1L;

  // Shared Action Names ****************************************************
  // Note that a subclass may or may not provide these and more Actions

  public static final String ACTION_NEW_ITEM = "newItem";
  public static final String ACTION_EDIT_ITEM = "editItem";
  public static final String ACTION_DELETE_ITEM = "deleteItem";

  // ************************************************************************

  private SelectionInList<T> selectionInList;

  // Instance Creation ******************************************************

  protected AbstractHomeModel() {
    initModels();
    initPresentationLogic();
  }

  // Initialisation *********************************************************

  protected abstract ListModel<?> getListModel();

  private void initModels() {
    selectionInList = new SelectionInList<>(getListModel());
    if (CollectionUtils.isNotEmpty(selectionInList.getList())) {
      selectionInList.setSelectionIndex(0);
    }
    handleSelectionChange(selectionInList.hasSelection());
  }

  private void initPresentationLogic() {
    getSelectionInList().addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION, new SelectionChangeHandler<>(
        this));
  }

  // Models *****************************************************************

  public SelectionInList<T> getSelectionInList() {
    return selectionInList;
  }

  protected T getSelection() {
    return getSelectionInList().getSelection();
  }

  // Actions ****************************************************************

  public ToolBarSpec contextSpec() {
    return contextActionNames().length == 0 ? null : new ToolBarSpec().add(getActionMap(), contextActionNames());
  }

  protected String[] contextActionNames() {
    throw new UnsupportedOperationException("Subclasses that do not "
        + "override this method must override #contextSpec().");
  }

  // Event Handling *********************************************************

  protected abstract void handleSelectionChange(boolean hasSelection);

  protected void handleSelectionChangeEditDelete(boolean hasSelection) {
    setActionEnabled(ACTION_EDIT_ITEM, hasSelection);
    setActionEnabled(ACTION_DELETE_ITEM, hasSelection);
  }

  // Event Handlers *********************************************************

  private static final class SelectionChangeHandler<T1> implements PropertyChangeListener {

    private final AbstractHomeModel<T1> model;

    SelectionChangeHandler(AbstractHomeModel<T1> model) {
      this.model = model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      model.handleSelectionChange(model.getSelectionInList().hasSelection());
    }
  }

}
