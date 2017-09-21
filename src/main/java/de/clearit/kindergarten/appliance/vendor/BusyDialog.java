package de.clearit.kindergarten.appliance.vendor;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public final class BusyDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusyDialog(final String title, final String label) {
		this.setSize(300, 130);
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;

		this.setLocation(x, y);
		this.setTitle(title);
		this.add(new JLabel(label, SwingConstants.CENTER));
	}
}