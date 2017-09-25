package de.clearit.kindergarten.desktop.component;

import javax.swing.Action;

/**
 *
 */
public final class ToolBarButton extends AbstractToolBarButton {

  private static final long serialVersionUID = 1L;

  public ToolBarButton(Action action) {
    if (action == null) {
      throw new IllegalArgumentException("The action must not be null.");
    }
    setAction(action);
  }

}