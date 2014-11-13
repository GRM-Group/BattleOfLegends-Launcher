package pl.grm.boll.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import pl.grm.boll.Presenter;

/**
 * Contains Console
 */
public class LeftPanel extends JScrollPane {
	private static final long			serialVersionUID	= 1L;
	private static TransparentTextArea	textArea			= new TransparentTextArea();
	private Presenter					presenter;
	private BufferedImage				background;
	
	/**
	 * Create the left panel.
	 * 
	 * @param presenterT
	 */
	public LeftPanel(Presenter presenterT) {
		super(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.presenter = presenterT;
		try {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\background.jpg";
			File file = new File(path);
			System.out.println(path);
			System.out.println(file.exists());
			background = ImageIO.read(file);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		textArea.setLineWrap(true);
		textArea.setPreferredSize(new Dimension(100, 100));
		textArea.setEditable(false);
		textArea.setFont(new Font("LucidaSans", Font.PLAIN, 16));
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		getViewport().setOpaque(false);
	}
	
	/**
	 * Sets size to image resolustion
	 */
	@Override
	public Dimension getPreferredSize() {
		return background == null ? super.getPreferredSize() : new Dimension(background.getWidth(),
				background.getHeight());
	}
	
	/**
	 * Paints component as transparent
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (background != null) {
			int x = (getWidth() - background.getWidth()) / 2;
			int y = (getHeight() - background.getHeight()) / 2;
			g.drawImage(background, x, y, this);
		}
	}
	
	/**
	 * @return {@link JTextArea} Console
	 */
	public JTextArea getConsole() {
		return textArea;
	}
}
