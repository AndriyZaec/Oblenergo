package net.ukr.ifkep.oblenergo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.ukr.ifkep.oblenergo.gui.LogoForm;
import net.ukr.ifkep.oblenergo.gui.MainForm;

public class Main {

	static LogoForm logoForm = new LogoForm();
	static MainForm mainForm = new MainForm();

	static javax.swing.Timer timer = new javax.swing.Timer(2000,
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					logoForm.setVisible(false);
					mainForm.setVisible(true);
					timer.stop();
				}
			});

	public static void main(String[] args) {
		logoForm.setVisible(true);
		timer.start();
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}

}
