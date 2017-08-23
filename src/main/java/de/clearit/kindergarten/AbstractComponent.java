package de.clearit.kindergarten;

import javax.swing.JComponent;

public abstract class AbstractComponent {

  /**
   * Holds the panel that has been lazily built in <code>#buildPanel</code>.
   */
  private JComponent panel;

  /**
   * Returns this view's panel. The default implementation builds the panel lazily
   * when this method is invoked the first time. Subclasses may override this
   * method to implement a better strategy that combines the lazy build with an
   * eager build performed in a background thread.
   *
   * @return this view's built panel
   */
  public synchronized JComponent getPanel() {
    if (panel == null) {
      panel = buildPanel();
    }
    return panel;
  }

  /**
   * Builds and returns this view's panel. This method is called by
   * <code>#getPanel</code> if the panel has not been built before.
   *
   * @return this view's panel
   */
  protected abstract JComponent buildPanel();

}
