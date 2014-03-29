import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageConsumer;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;


public class KaleidoscopeGenerator {
	BufferedImage baseImage;
	BufferedImage resultImage;

	private KaleidoscopeGenerator(BufferedImage inputImage) {
		baseImage = inputImage;
	}	
	
	public static BufferedImage createImage(BufferedImage inputImage) {
		int width = 500;
		int height = 500;
		KaleidoscopeGenerator kg = new KaleidoscopeGenerator(inputImage);
		
		kg.resultImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dresult = kg.resultImage.createGraphics();
		
		//create the base triangle of the image
		Polygon triangle = TriangleFactory.getTrianglePolygon(0, 0, 100, 30);
		
		//clip polygon from the original image
		Graphics2D g2dbase = kg.baseImage.createGraphics();
		g2dbase.setClip(triangle);
		Rectangle r = triangle.getBounds();
		//g2dbase.drawImage(kg.baseImage, r.x, r.y, r.width, r.height, this)
		
		 // Draw graphics
	    g2dbase.setComposite(AlphaComposite.Clear);
	    g2dbase.fillRect(0, 0, width, height);
	    
		return kg.baseImage;
	}
	
	public static void saveImage(BufferedImage image) {
		try {
		    File outputfile = new File("C:\\Users\\ben\\Dropbox\\Programming Projects\\Kaleidoscope\\saved.png");
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}

}
