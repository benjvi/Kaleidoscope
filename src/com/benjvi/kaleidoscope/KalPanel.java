package com.benjvi.kaleidoscope;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;


public class KalPanel extends JPanel {
	//refer to the following to implement best practices here:
	//http://stackoverflow.com/questions/13022754/java-bouncing-ball/13022788#13022788
	//http://stackoverflow.com/questions/12642852/the-images-are-not-loading/12648265#12648265
	BufferedImage img;
        Kaleidoscope kal;

	public KalPanel(BufferedImage image, Kaleidoscope kal) {
		//just initialising values
		this.img = image;
		this.kal = kal;
		this.setOpaque(true);
		setBackground(Color.white);
		
	}

	public void setKaleidoscope(Kaleidoscope kal) {
		this.kal = kal;
	}
	
	public void paintComponent(Graphics g) {
			g.clearRect(0, 0, WIDTH, HEIGHT);
			//this.kal = new Kaleidoscope(getWidth(), getHeight());
	        Graphics2D g2 = (Graphics2D) g.create();
	        super.paintComponent(g2);
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	 
	        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
	                            RenderingHints.VALUE_RENDER_QUALITY);

	        drawPrimitiveCell(g2);
	        g2.dispose();
	        

	    }
		/*
		private void drawKaleidoscope(Graphics2D g) {
			for (List<Triangle> reflectionOrders : kal.getReflectionsList()) {
				for (Triangle tri : reflectionOrders) {
					AffineTransform transform = tri.getTransformMatrix();
					Shape triangle = tri.toPolygon();
					drawTriangle(triangle, transform, g);					
				}
				
				
			}
		}
		*/
		private void drawPrimitiveCell(Graphics2D g) {
			LinkedList<Triangle> primitiveCell = new LinkedList<Triangle>(kal.allCells);
			Triangle tri;
			while (primitiveCell.peek()!=null) {
				tri = primitiveCell.poll();
				drawTriangle(tri.toPolygon(), tri.getTransformMatrix(), g);
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
