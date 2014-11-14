package pl.grm.boll.config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceLoader {
	
	public static BufferedImage getBGImage() {
		BufferedImage background = null;
		try {
			String path = System.getProperty("user.dir") + "\\src\\main\\resources\\background.jpg";
			File file = new File(path);
			background = ImageIO.read(file);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		return background;
	}
	
}
