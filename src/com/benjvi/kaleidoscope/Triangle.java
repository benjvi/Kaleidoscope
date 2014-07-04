package com.benjvi.kaleidoscope;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;


public class Triangle {
	private int x1, y1, x2, y2, x3, y3;
	private double orientationdeg;
	private int centerx, centery;
	private Boolean isflippedx, isflippedy;
	private int size;
	
	public Triangle(int size) {
		//creates a triangle in the default orientation, at the origin
		//default orientation has the orientation pointing upwards (point one at the top) 
		//size = distance from the center to the vertices
		this(size, 0, 0);
	}
	
	public Triangle(int size, int centerx, int centery) {
		x1 = centerx + (int)Math.round( (double) size * Math.sin(Math.toRadians(180))); 
		y1 = centery + (int)Math.round( (double) size * Math.cos(Math.toRadians(180)));
		
		x2 = centerx + (int)Math.round( (double) size * Math.sin(Math.toRadians(60))); 
		y2 = centery + (int)Math.round( (double) size * Math.cos(Math.toRadians(60))); 

		x3 = centerx + (int)Math.round( (double) size * Math.sin(Math.toRadians(-60))); 
		y3 = centery + (int)Math.round( (double) size * Math.cos(Math.toRadians(-60)));
		orientationdeg = 0;
		isflippedx= false;
		isflippedy = false;
		this.size = size;
	}
	
	public Polygon toPolygon() {
		Polygon triangle = new Polygon();
		triangle.addPoint(x1, y1);
		triangle.addPoint(x2, y2);
		triangle.addPoint(x3, y3);
		return triangle;
	}
	
	public AffineTransform getTransformMatrix() {
		//need to reset the translation matri each time
		AffineTransform transform = new AffineTransform();
		int flipx = 1;
		int flipy = 1;
		
		if (isflippedx) {
			transform.scale(-1,1);
			flipx = -1;
		}
		if (isflippedy) {
			transform.scale(1,-1);
			flipy = -1;
		}
		
		transform.rotate(Math.toRadians(flipx*flipy*orientationdeg));
		
        double translatex = centerx * Math.cos(Math.toRadians(orientationdeg)) + centery * Math.sin(Math.toRadians(orientationdeg));	        
        double translatey = centery * Math.cos(Math.toRadians(orientationdeg)) - (centerx * Math.sin(Math.toRadians(orientationdeg)));
        transform.translate(Math.round(flipx*translatex), Math.round(flipy*translatey));
        
        return transform;
	}
	
	public void setCenter(int tx, int ty) {
		centerx = tx;
		centery = ty;
	}
	
	public void setOrientation(double angdeg) {
		orientationdeg = angdeg;
	}
	
	public void doHorizontalFlip() {
		isflippedx = !isflippedx;
	}
	
	public Boolean isFlippedHorizontally() {
		return isflippedx;
	}
	
	public double getOrientation() {
		return orientationdeg;
	}
	
	public int getXPosition() {
		return centerx;
	}
	public int getYPosition() {
		return centery;
	}
	
	public Triangle clone() {
		Triangle newTri = new Triangle(this.size);
		newTri.setOrientation(orientationdeg);
		newTri.setCenter(getXPosition(), getYPosition());
		if (this.isFlippedHorizontally()) {
			newTri.isflippedx=true;
		}
		return newTri;
	}
}
