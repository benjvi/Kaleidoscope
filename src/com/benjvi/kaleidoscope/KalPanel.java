package com.benjvi.kaleidoscope;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;


public class KalPanel extends JPanel {
	BufferedImage img;
    int w, h;
    Kaleidoscope kal;
 
	
	public KalPanel(BufferedImage image) {
		//just initialising values
		this.img = image;
		this.w = 1000;
	        this.h = 1000;
		this.kal = new Kaleidoscope(w, h, img);
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	        setBackground(Color.white);
	        if (kal.getWidth() < w || kal.getHeight() < h) {
	        	//need to make a new kaleidoscope
	        	kal = new Kaleidoscope(w, h, img);
	        }
	        
	        Graphics2D g2 = (Graphics2D) g.create();
	 
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	 
	        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
	                            RenderingHints.VALUE_RENDER_QUALITY);

	        drawKaleidoscope(g2);


	    }
	
		private void drawKaleidoscope(Graphics2D g) {
			for (List<Triangle> reflectionOrders : kal.getReflectionsList()) {
				for (Triangle tri : reflectionOrders) {
					AffineTransform transform = tri.getTransformMatrix();
					Shape triangle = tri.toPolygon();
					drawTriangle(triangle, transform, g);
				}
				
			}
		}
	
		private void drawTriangle(Shape triangle, AffineTransform transform, Graphics2D g) {
			Rectangle r = triangle.getBounds();
	                g.setTransform(transform);
	                g.setClip(triangle);
	        	Boolean drawSucceeded = g.drawImage(img, r.x, r.y, r.width, r.height, this);
			assert(drawSucceeded);	
		}
		

}
