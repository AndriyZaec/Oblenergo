package net.ukr.ifkep.oblenergo.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogoForm extends JDialog {

	private static final long serialVersionUID = 1L;
	JPanel objPanel = new JPanel();

	public LogoForm() {

		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		setLocation(Math.round((xSize - 300) / 2),
				Math.round((ySize - 300) / 2));
		setUndecorated(true);
		setSize(438, 178);
		this.setBackground(Color.WHITE);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel pic = new JLabel();
		pic.setIcon(new ImageIcon("img/colledg.jpg"));
		add(pic);
	}
}
