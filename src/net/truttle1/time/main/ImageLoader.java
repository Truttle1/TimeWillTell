package net.truttle1.time.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.truttle1.time.menu.MenuMode;
import net.truttle1.time.titlescreen.TitleMode;

public class ImageLoader {

	BufferedImage image;
	public BufferedImage loadImage(String path)
	{
	try
	{
		image = ImageIO.read(getClass().getResource(path));
		System.out.println(path);
		TitleMode.currentText = path;
		MenuMode.currentText = path;
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}
	return image;
	}
}
