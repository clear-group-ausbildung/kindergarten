package de.clearit.kindergarten.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.application.Application;
import com.jgoodies.application.ResourceMap;
import com.jgoodies.desktop.DesktopManager;
import com.jgoodies.desktop.Document;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.jsdl.core.CommandValue;
import com.jgoodies.jsdl.core.MessageType;
import com.jgoodies.jsdl.core.PreferredWidth;
import com.jgoodies.jsdl.core.pane.TaskPane;
import com.jgoodies.jsdl.core.util.JSDLUtils;
import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;

import de.clearit.kindergarten.domain.PurchaseBean;

/**
 * Consists only of static method to open frequently used dialogs.
 *
 */
public final class Dialogs {

  private static final ResourceMap RESOURCES = Application.getResourceMap(Dialogs.class);
  private final static String lineSeparator = System.getProperty("line.separator");
  private final static String urlHelp = RESOURCES.getString("dialogs.about.help");
  private final static String urlHowTo = RESOURCES.getString("dialogs.about.howTo");
  private final static String urlInstall = RESOURCES.getString("dialogs.about.install");
  
  // Instance Creation ******************************************************

  private Dialogs() {
    // Overrides default constructor; prevents instantiation.
  }

  // API ********************************************************************

  /**
   * @return {@code true} if canceled, {@code false} otherwise
   */
  public static boolean vendorHasErrors(EventObject e, ValidationResult result) {
    String title = "Fehler bei der Eingabepr\u00fcfung";
    String mainInstruction = "Es konnte aufgrund folgender Fehler nicht gespeichert werden:";

    StringBuilder contentTextBuilder = new StringBuilder("<html>");
    contentTextBuilder.append(result.getMessages().stream().map(
                                 ValidationMessage::formattedText).collect(Collectors.joining("<br>")));
    
	// ich kann den Punkt nur hier entfernen deswegen habe ich noch in
    // VendorValidatable den PROPERTY String separat entfernt
    String contentText = contentTextBuilder.toString().replace('.', ' ').trim();  

    TaskPane pane = new TaskPane(MessageType.ERROR, mainInstruction, contentText, CommandValue.CANCEL);
    pane.setPreferredWidth(400);

    pane.showDialog(e, title);
    return pane.isCancelled();
  }

  /**
   * @return {@code true} if canceled, {@code false} otherwise
   */
  public static boolean purchaseHasErrors(EventObject e, PurchaseBean purchase) {
    String title = RESOURCES.getString("dialogs.documentHasErrors.title");
    String mainInstruction = RESOURCES.getString("dialogs.documentHasErrors.mainInstruction", purchase.getItemNumber());
    String contentText = RESOURCES.getString("dialogs.documentHasErrors.content", purchase.getItemNumber());

    TaskPane pane = new TaskPane(MessageType.WARNING, mainInstruction, contentText, CommandValue.DISCARD,
        CommandValue.CANCEL);
    pane.setMarginContentTop(4);
    pane.setMarginContentBottom(14);

    pane.showDialog(e, title);
    return pane.isCancelled();
  }

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
      MainModel.getInstance().discardDocuments();
    }
    return pane.isCancelled();
  }

  
  //Show About
  public static void about(EventObject e) {
	  
    String title = RESOURCES.getString("dialogs.about.title");
    String mainInstruction = RESOURCES.getString("application.name") + " " + RESOURCES.getString("application.version");
    String contentText = RESOURCES.getString("dialogs.about.content", RESOURCES.getString("application.copyright"),
        RESOURCES.getString("application.buildNo"), RESOURCES.getDate("application.buildDate"));
    String contactDetails = RESOURCES.getString("dialogs.about.contactDetails");
    
    JLabel imageLabel = new JLabel(RESOURCES.getIcon("application.logo"));

    JFrame frame = new JFrame(title);
    FormLayout layout = new FormLayout("5dlu, pref, 5dlu", "4dlu, pref, 10dlu, pref, pref, 10dlu, pref, 4dlu");
    JPanel aboutPanel = new JPanel(layout);
    CellConstraints cc = new CellConstraints();
    aboutPanel.add(imageLabel, cc.xy(2, 2, CellConstraints.CENTER, CellConstraints.CENTER));
    aboutPanel.add(new JLabel(mainInstruction), cc.xy(2, 4, CellConstraints.CENTER, CellConstraints.CENTER));
    aboutPanel.add(new JLabel(contentText), cc.xy(2, 5, CellConstraints.CENTER, CellConstraints.CENTER));
    aboutPanel.add(new JLabel(contactDetails), cc.xy(2, 7, CellConstraints.CENTER, CellConstraints.CENTER));
    aboutPanel.setBackground(Color.WHITE);
    frame.add(aboutPanel);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setMinimumSize(new Dimension(320,300));
  }

  //Show all Shortcuts 
  public static void shortcuts(EventObject e) {
	  String shortcutTitle = RESOURCES.getString("dialogs.about.shortcuts");
	  String shortcutMainTitle = RESOURCES.getString("dialogs.about.shortCutTitle");
	  String shortcutVendor = RESOURCES.getString("dialogs.about.shortCutVendor");
	  JFrame frame = new JFrame(shortcutTitle);
	  FormLayout layout = new FormLayout("4dlu, pref, pref", "4dlu, pref, 100dlu, pref");
	  JPanel shortcutPanel = new JPanel(layout);
	  CellConstraints cc = new CellConstraints();
	   
	  shortcutPanel.add(new JLabel(shortcutMainTitle), cc.xy(2, 2, CellConstraints.CENTER, CellConstraints.CENTER));
	  shortcutPanel.add(new JLabel(shortcutVendor), cc.xy(2, 3, CellConstraints.CENTER, CellConstraints.CENTER));	  
	  shortcutPanel.setBackground(Color.WHITE);
	  
	  frame.add(shortcutPanel);
	  frame.setVisible(true);
	  frame.setLocationRelativeTo(null);
	  frame.setMinimumSize(new Dimension(300,300));
  }
  
  //Open Help
  public static void help(EventObject e) throws IOException {
	  String helpTitle = RESOURCES.getString("dialogs.about.help.title");
	  final JLabel helpIntro = new JLabel(RESOURCES.getString("dialogs.about.help.intro"));
	  final JLabel helpLabel = new JLabel("<HTML><FONT color=\"#000000\"><U><b>" + "Benutzerhandbuch" + "</b></U></FONT></HTML>");
	  final JLabel howToIntro = new JLabel(RESOURCES.getString("dialogs.about.howTo.intro"));
	  final JLabel howToLabel = new JLabel("<HTML><FONT color=\"#000000\"><U><b>" + "HowTo Datei" + "</b></U></FONT></HTML>");
	  final JLabel installIntro = new JLabel(RESOURCES.getString("dialogs.about.install.intro"));
	  final JLabel installLabel = new JLabel("<HTML><FONT color=\"#000000\"><U><b>" + "Installations Datei" + "</b></U></FONT></HTML>");
	  final JLabel logoLabel = new JLabel(RESOURCES.getIcon("application.logo")); 
	  
	  helpLabel.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {}
		
		@Override
		public void mousePressed(MouseEvent e) {}
		
		@Override
		public void mouseExited(MouseEvent e) {
			helpLabel.setText("<HTML><FONT color=\"#000000\"><U><b>" + "Benutzerhandbuch" + "</b></U></FONT></HTML>");
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			helpLabel.setText("<HTML><FONT color=\"#000099\"><U>" + "Klicken Sie hier um das Benutzerhandbuch zu öffnen." + "</U></FONT></HTML>");
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			File htmlFile = new File(urlHelp);
			try {
				Desktop.getDesktop().browse(htmlFile.toURI());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
	});
	  
	  howToLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				howToLabel.setText("<HTML><FONT color=\"#000000\"><U><b>" + "HowTo Datei" + "</b></U></FONT></HTML>");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				howToLabel.setText("<HTML><FONT color=\"#000099\"><U>" + "Klicken Sie hier um die HowTo Datei zu öffnen." + "</U></FONT></HTML>");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				File htmlFile = new File(urlHowTo);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
	  
	  installLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				installLabel.setText("<HTML><FONT color=\"#000000\"><U><b>" + "Installations Datei" + "</b></U></FONT></HTML>");
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				installLabel.setText("<HTML><FONT color=\"#000099\"><U>" + "Klicken Sie hier um die Installations Datei zu öffnen." + "</U></FONT></HTML>");
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				File htmlFile = new File(urlInstall);
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		});
	  
	  JFrame frame = new JFrame(helpTitle);		
	  FormLayout layout = new FormLayout("5dlu, pref, pref", "4dlu, pref, 8dlu, pref, 1dlu, pref, 8dlu, pref, 1dlu, pref, 8dlu, pref, 1dlu, pref, 8dlu, pref, 1dlu");
	  JPanel helpPanel = new JPanel(layout);
	  CellConstraints cc = new CellConstraints();
	  
	  helpPanel.add(logoLabel, cc.xy(2, 2, CellConstraints.CENTER, CellConstraints.CENTER));
	  helpPanel.add(installIntro, cc.xy(2, 4, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  helpPanel.add(installLabel, cc.xy(2, 6, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  helpPanel.add(howToIntro, cc.xy(2, 8, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  helpPanel.add(howToLabel, cc.xy(2, 10, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  helpPanel.add(helpIntro, cc.xy(2, 12, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  helpPanel.add(helpLabel, cc.xy(2, 14, CellConstraints.DEFAULT, CellConstraints.DEFAULT));
	  
	  helpPanel.setBackground(Color.WHITE);
	  
	  frame.add(helpPanel);
	  frame.setVisible(true);
	  frame.setLocationRelativeTo(null);
	  frame.setMinimumSize(new Dimension(380,300));
  }
  
  
  
  // Helper Code ************************************************************

  private static List<Document> getSelectedDocuments(JList<?> list) {
    List<Document> result = new LinkedList<>();
    for (Object element : list.getSelectedValuesList()) {
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