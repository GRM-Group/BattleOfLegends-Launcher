package pl.grm.boll.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class TransparentTextArea extends JTextArea {
	private static final long	serialVersionUID	= 1L;
	
	public TransparentTextArea() {
		setOpaque(false);
		setBackground(new Color(0, 0, 0, 0));
		setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(
				Color.LIGHT_GRAY)));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (insets.left + insets.right);
		int height = getHeight() - (insets.top + insets.bottom);
		g.fillRect(x, y, width, height);
		super.paintComponent(g);
	}
}