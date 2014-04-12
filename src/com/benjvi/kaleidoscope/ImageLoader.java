package com.benjvi.kaleidoscope;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static final String[] imageExtensions = {"jpeg","jpg","png"};
	
	public static List<BufferedImage> loadFromFolder(String folderpath) {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		File folder = new File(folderpath);
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				if (isImage(file)) {
					BufferedImage img = null;
					try {
					    img = ImageIO.read(file.getAbsoluteFile());
					    result.add(img);
					    //initialise the kaleidoscopeImage
					    //BufferedImage kaleidoscope = KaleidoscopeGenerator.createImage(img);
					    //KaleidoscopeGenerator.saveImage(kaleidoscope);
					} catch (IOException e) {
					} 
					if (img != null) {
						result.add(img);
					}
				}
			}
		}
		return result;
	}
	
	private static Boolean isImage(File file) {
		Boolean result = false;
		String fileExt = "";
		int i = file.getName().lastIndexOf('.');
		if (i > 0) {
		    fileExt = file.getName().substring(i+1);
		}
		
		for (String imageExt : imageExtensions) {
			if (fileExt.equals(imageExt)) {
				result = true;
			}
		}
		
		return result;
	}

}
