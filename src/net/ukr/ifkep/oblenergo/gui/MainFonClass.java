package net.ukr.ifkep.oblenergo.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainFonClass extends JPanel {

	private static final long serialVersionUID = 1;

	private BufferedImage Fonimage;

	public MainFonClass(String s) {
		try {
			Fonimage = ImageIO.read(new File(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Fonimage, 0, 0, getWidth(), getHeight(), null);
	}
}
