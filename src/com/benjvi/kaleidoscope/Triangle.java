package com.benjvi.kaleidoscope;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;


public class Triangle {
	int x1, y1, x2, y2, x3, y3;
	double orientationdeg;
	int centerx, centery;
	Boolean isflippedx, isflippedy;
	
	
	public Triangle(int size) {
		//creates a triangle in the default orientation, at the origin
		//size = distance from the center to the vertices
		x1 = (int)Math.round( (double) size * Math.sin(Math.toRadians(180))); 
		y1 = (int)Math.round( (double) size * Math.cos(Math.toRadians(180)));
		
		x2 = (int)Math.round( (double) size * Math.sin(Math.toRadians(60))); 
		y2 = (int)Math.round( (double) size * Math.cos(Math.toRadians(60))); 

		x3 = (int)Math.round( (double) size * Math.sin(Math.toRadians(-60))); 
		y3 = (int)Math.round( (double) size * Math.cos(Math.toRadians(-60)));
		orientationdeg = 0;
		centerx = 0;
		centery = 0;
		isflippedx= false;
		isflippedy = false;
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
}
