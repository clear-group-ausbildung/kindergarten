package de.clearit.kindergarten.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.application.TaskMonitor;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.spec.NavigationBarSpec;
import com.jgoodies.desktop.spec.ToolBarSpec;
import com.jgoodies.desktop.view.DefaultProgressView;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.quicksearch.QuickSearchManager;
import com.jgoodies.quicksearch.QuickSearchView;
import com.jgoodies.uif2.AbstractView;
import com.jgoodies.uif2.component.ToolBarButton;
import com.jgoodies.uif2.util.ComponentUtils;

import de.clearit.kindergarten.application.KindergartenApplication;
import de.clearit.kindergarten.application.MainModel;
import de.clearit.kindergarten.desktop.component.ImageBackgroundPanel;

/**
 * The view for the desktop.
 */
public final class DesktopView extends AbstractView {

  private static final ResourceMap RESOURCES = Application.getResourceMap(DesktopView.class);

  private JButton backButton; // header navigation
  private JButton nextButton;

  private JPanel contextActionContainer; // header center: toolbar
  private JButton searchButton; // header popup for quicksearch

  private JPanel navigationActionContainer; // side bar top
  private JPanel applicationActionContainer; // side bar middle
  private JPanel documentActionContainer; // side bar bottom

  private JPanel contentContainer; // content
  private JPanel statusContainer; // footer: details
  private DefaultProgressView progressView; // footer: progress

  // Building ***************************************************************

  private void initComponents() {
    Action backAction = DesktopManager.INSTANCE.getGoBackAction();
    backButton = new ToolBarButton(backAction);
    backButton.setPreferredSize(new Dimension(28, 29));
    backButton.setContentAreaFilled(false);
    backButton.setBorder(null);
    backButton.setDisabledIcon(RESOURCES.getIcon("history.goBack.Action.icon.disabled"));
    backButton.setRolloverIcon(RESOURCES.getIcon("history.goBack.Action.icon.rollover"));
    Object backAccelerator = backAction.getValue(Action.ACCELERATOR_KEY);
    if (backAccelerator != null) {
      ComponentUtils.registerKeyboardAction(backButton, backAction, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    Action nextAction = DesktopManager.INSTANCE.getGoNextAction();
    nextButton = new ToolBarButton(nextAction);
    nextButton.setPreferredSize(new Dimension(27, 29));
    nextButton.setContentAreaFilled(false);
    nextButton.setBorder(null);
    nextButton.setDisabledIcon(RESOURCES.getIcon("history.goNext.Action.icon.disabled"));
    nextButton.setRolloverIcon(RESOURCES.getIcon("history.goNext.Action.icon.rollover"));
    Object nextAccelerator = nextAction.getValue(Action.ACCELERATOR_KEY);
    if (nextAccelerator != null) {
      ComponentUtils.registerKeyboardAction(nextButton, nextAction, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    contextActionContainer = new JPanel(new BorderLayout());
    contextActionContainer.setOpaque(false);

    QuickSearchManager manager = new QuickSearchManager(new DesktopQuickSearchProcessor());
    Action searchAction = new QuickSearchView(manager).getQuickSearchAction();
    searchButton = DesktopViewFactory.createToolBarButton(searchAction);
    // searchButton.setBorder(new
    // DesktopViewFactory.ToolbarButtonHighlightBorder());
    // searchButton.setBorder(new LineBorder(Color.GRAY));
    // searchButton.setContentAreaFilled(false);
    // searchButton.setRolloverEnabled(false);
    searchButton.setText("");
    searchButton.setPreferredSize(new Dimension(30, 26));

    navigationActionContainer = new JPanel(new BorderLayout());
    navigationActionContainer.setOpaque(false);
    applicationActionContainer = new JPanel(new BorderLayout());
    applicationActionContainer.setOpaque(false);
    documentActionContainer = new JPanel(new BorderLayout());
    documentActionContainer.setOpaque(false);

    contentContainer = new JPanel(new BorderLayout());
    // contentContainer.setOpaque(false);
    contentContainer.setBackground(RESOURCES.getColor("content.background"));
    statusContainer = new JPanel(new BorderLayout());
    statusContainer.setOpaque(false);

    TaskMonitor monitor = Application.getInstance().getContext().getTaskMonitor();
    progressView = new DefaultProgressView(monitor);
  }

  // Presentation Logic *****************************************************

  private void initEventHandling() {
    DesktopManager.INSTANCE.addPropertyChangeListener(DesktopManager.PROPERTY_ACTIVE_FRAME, new FrameChangeHandler());
    OpenDocumentsChangeHandler handler = new OpenDocumentsChangeHandler();
    DesktopManager.INSTANCE.addPropertyChangeListener(DesktopManager.PROPERTY_OPEN_DOCUMENT_ACTION, handler);
    DesktopManager.INSTANCE.getOpenDocumentFrames().addListDataListener(handler);
  }

  // Building ***************************************************************

  @Override
  protected JComponent buildPanel() {
    initComponents();
    initEventHandling();

    FormLayout layout = new FormLayout("[80dlu,pref], 2px, [400px,pref]:grow", "fill:200px:grow, 2px, pref");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);

    builder.add(buildNavigation(), CC.xywh(1, 1, 1, 3));
    builder.add(buildVerticalSeparator(), CC.xy(2, 1));
    builder.add(contentContainer, CC.xy(3, 1));
    builder.add(buildHorizontalSeparator(), CC.xyw(2, 2, 2));
    builder.add(buildBottom(), CC.xyw(2, 3, 2));
    return builder.getPanel();
  }

  private JComponent buildHorizontalSeparator() {
    return ImageBackgroundPanel.createFrom(RESOURCES.getImage("statusbar.separator"));
    // return GradientSeparator.createHorizontalGradient(
    // //new Color(44, 88, 173),
    // new Color(32, 56, 118),
    // new Color(33, 68, 143),
    // Color.BLACK);
    // new Color(30, 22, 63));
  }

  private JComponent buildVerticalSeparator() {
    return ImageBackgroundPanel.createFrom(RESOURCES.getImage("navigation.separator"));
    // return GradientSeparator.createVerticalGradient(
    // new Color(32, 56, 118),
    // new Color(30, 22, 63),
    // Color.BLACK);
  }

  private JComponent buildHeader() {
    FormLayout layout = new FormLayout("fill:0:grow", "fill:0:grow");
    PanelBuilder builder = new PanelBuilder(layout, DesktopViewFactory.createHeaderPanel());
    builder.add(buildHeaderContent(), CC.xy(1, 1));
    return builder.getPanel();
  }

  private JComponent buildHeaderContent() {
    FormLayout layout = new FormLayout("p, 4dlu, p, 14dlu:grow, p, 9dlu, p", "fill:pref:grow");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setBorder(Borders.createEmptyBorder("1px, 4dlu, 2px, 3px"));
    builder.setOpaque(false);
    builder.add(buildBackNextToolBar(), CC.xy(1, 1));
    builder.add(buildMenuBar(), CC.xy(3, 1));
    builder.add(contextActionContainer, CC.xy(5, 1));
    builder.add(searchButton, CC.xy(7, 1));
    return builder.getPanel();
  }

  private JComponent buildBackNextToolBar() {
    FormLayout layout = new FormLayout("p, p", "center:p:grow");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setOpaque(false);
    builder.add(backButton, CC.xy(1, 1));
    builder.add(nextButton, CC.xy(2, 1));
    return builder.getPanel();
  }

  private JComponent buildMenuBar() {
    return DesktopViewFactory.createToolBar(MainModel.getInstance().createApplicationToolBarSpec());
  }

  private JComponent buildNavigation() {
    FormLayout layout = new FormLayout("fill:pref:grow", "fill:pref:grow");
    PanelBuilder builder = new PanelBuilder(layout); // , DesktopViewFactory.createNavigationPanel());
    builder.setBackground(RESOURCES.getColor("content.background"));
    builder.add(buildNavigationContent(), CC.xy(1, 1));
    return builder.getPanel();
  }

  private JComponent buildNavigationContent() {
    KindergartenApplication application = (KindergartenApplication) Application.getInstance();
    updateApplicationActions(application.applicationActions());
    updateDocumentActions();

    FormLayout layout = new FormLayout("pref:grow", "p, 9dlu, p, 9dlu:grow, p");
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setBorder(Borders.createEmptyBorder("9dlu, 7dlu, 2dlu, 9dlu"));
    builder.setOpaque(false);
    builder.add(applicationActionContainer, CC.xy(1, 1));
    builder.add(DesktopViewFactory.createActionSeparator(), CC.xy(1, 3));
    builder.add(documentActionContainer, CC.xy(1, 5));
    return builder.getPanel();
  }

  private JComponent buildBottom() {
    FormLayout layout = new FormLayout("fill:pref:grow, 4dlu, pref", "[14dlu,pref]");
    PanelBuilder builder = new PanelBuilder(layout, DesktopViewFactory.createStatusPanel());
    builder.setBorder(Borders.createEmptyBorder("2dlu, 14dlu, 2dlu, 5dlu"));
    builder.add(statusContainer, CC.xy(1, 1));
    builder.add(progressView.getPanel(), CC.xy(3, 1));
    return builder.getPanel();
  }

  // Event Handling Code ****************************************************

  private void updateDocumentActions() {
    updateDocumentActions0();
    documentActionContainer.revalidate();
    documentActionContainer.repaint();
  }

  private void updateDocumentActions0() {
    documentActionContainer.removeAll();
    String title = RESOURCES.getString("navigation.openDocuments");
    NavigationBarSpec spec = DesktopManager.INSTANCE.getOpenDocumentsSideBarSpec(title);
    if (spec == null)
      return;
    documentActionContainer.add(DesktopViewFactory.createSideBar(spec));
  }

  private void updateApplicationActions(NavigationBarSpec applicationNavigationSpec) {
    applicationActionContainer.removeAll();
    if (applicationNavigationSpec == null) {
      return;
    }
    applicationActionContainer.add(DesktopViewFactory.createSideBar(applicationNavigationSpec), BorderLayout.CENTER);
  }

  private void updateContextActions(ToolBarSpec newContextSpec) {
    contextActionContainer.removeAll();
    if (newContextSpec == null) {
      return;
    }
    contextActionContainer.add(DesktopViewFactory.createToolBar(newContextSpec), BorderLayout.CENTER);
  }

  private void updateNavigationActions(NavigationBarSpec newNavigationSpec) {
    navigationActionContainer.removeAll();
    if (newNavigationSpec == null) {
      return;
    }
    navigationActionContainer.add(DesktopViewFactory.createSideBar(newNavigationSpec), BorderLayout.CENTER);
  }

  private void updateContent(JComponent newContentView) {
    contentContainer.removeAll();
    contentContainer.add(newContentView, BorderLayout.CENTER);
  }

  private void updateStatus(JComponent newStatusView) {
    statusContainer.removeAll();
    if (newStatusView != null) {
      statusContainer.add(newStatusView, BorderLayout.CENTER);
    }
  }

  // Event Handler Classes **************************************************

  private final class OpenDocumentsChangeHandler implements ListDataListener, PropertyChangeListener {
    @Override
    public void contentsChanged(ListDataEvent e) {
      updateDocumentActions();
    }

    @Override
    public void intervalAdded(ListDataEvent e) {
      updateDocumentActions();
    }

    @Override
    public void intervalRemoved(ListDataEvent e) {
      updateDocumentActions();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      updateDocumentActions();
    }
  }

  private final class FrameChangeHandler implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
      DefaultDesktopFrame newFrame = (DefaultDesktopFrame) evt.getNewValue();
      updateContextActions(newFrame.contextSpec());
      updateNavigationActions(newFrame.navigationSpec());
      updateContent(newFrame.contentPane());
      updateStatus(newFrame.statusPane());
      newFrame.requestInitialFocus();
      contextActionContainer.revalidate();
      contextActionContainer.repaint();
      navigationActionContainer.revalidate();
      navigationActionContainer.repaint();
      contentContainer.revalidate();
      contentContainer.repaint();
      statusContainer.revalidate();
      statusContainer.repaint();
    }
  }

}
