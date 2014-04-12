package com.benjvi.kaleidoscope;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Kaleidoscope {
	BufferedImage srcimage;
	int imagewidth, imageheight;
	int outputcenterx, outputcentery;
	int aperturesize;
	double orientationdeg;
	double zoom;
	public List<List<Triangle>> reflectionOrdersList;
	
	public Kaleidoscope (int panelwidth, int panelheight, BufferedImage origimage) {
		reflectionOrdersList = new ArrayList<List<Triangle>>();
		this.srcimage = origimage;
		this.imagewidth = panelwidth;
		this.imageheight = panelheight;
		setDefaultParameters();
		generate();
		//usethe panelwidth and panelheight to set the limits of the generated kaleidoscope
	}
	
	private void setDefaultParameters() {
		outputcenterx = imagewidth/2;
		outputcentery = imageheight/2;
		aperturesize = Math.min(imagewidth, imageheight)/5;
	    orientationdeg = 0;
	    zoom = 1.0;
	}
	
	private void generate() {
		//create the initial triangle 
		//this is the image produced by light which goes straight through the kaleidoscope without hitting any mirrors
		List<Triangle> nextOrderReflection;
		Triangle origtri = createUnreflectedImage();
		ArrayList<Triangle> origTriList = new ArrayList<Triangle>();
		origTriList.add(origtri);
		reflectionOrdersList.add(origTriList);
		nextOrderReflection = createNextOrderReflection(origTriList);
		reflectionOrdersList.add(nextOrderReflection);
		while(!isOutsideBounds(nextOrderReflection)) {
			nextOrderReflection = createNextOrderReflection(nextOrderReflection);
			reflectionOrdersList.add(nextOrderReflection);
		}


	}

	private Triangle createUnreflectedImage() {
		Triangle image = new Triangle(aperturesize);
		image.setOrientation(orientationdeg);
		image.setCenter(outputcenterx, outputcentery);
		return image;
	}
	
	private List<Triangle> createNextOrderReflection(List<Triangle> lastOrderReflection) {
		int trinormalsize = (int)(aperturesize * Math.sin(Math.toRadians(30)));
		ArrayList<Triangle> nextOrderReflection = new ArrayList<Triangle>();
		
		for (Triangle triangle : lastOrderReflection) {
			Boolean doHorizFlip = !triangle.isFlippedHorizontally();
			//if the previous reflection was already flipped, we dont need to flip back
			//since we will end up in the same state
			int centerx = triangle.getXPosition();
			int centery = triangle.getYPosition();
			double orientation = triangle.getOrientation();
			
			
			//for neighbour 1
			double refltriangdeg= orientation+60;
			int trinormalsizex = (int) (trinormalsize * Math.cos(Math.toRadians(refltriangdeg - 30)));
	        int trinormalsizey = (int) (trinormalsize * Math.sin(Math.toRadians(refltriangdeg - 30)));
	        int targetx = (int) (centerx - (  2 * trinormalsizex ) );
	        int targety = (int) (centery - (  2 * trinormalsizey ) );
	        Triangle reflectedImage1 = new Triangle(aperturesize);
	        if (doHorizFlip) {
				reflectedImage1.doHorizontalFlip();
			}
	        reflectedImage1.setOrientation(refltriangdeg);
	        reflectedImage1.setCenter(targetx, targety);
	        if (!coordsInReflectionList(targetx, targety)) {
	        	nextOrderReflection.add(reflectedImage1);
	        } 
	        
	        //for neighbour2
	        refltriangdeg= orientation-60;
	        trinormalsizex = (int) (trinormalsize * Math.cos(Math.toRadians(refltriangdeg + 30)));
		    trinormalsizey = (int) (trinormalsize * Math.sin(Math.toRadians(refltriangdeg + 30)));
		    targetx = (int) (centerx + (  2 * trinormalsizex ) );
	        targety = (int) (centery + (  2 * trinormalsizey ) );
	        Triangle reflectedImage2 = new Triangle(aperturesize);
	        if (doHorizFlip) {
				reflectedImage2.doHorizontalFlip();
			}
	        reflectedImage2.setOrientation(refltriangdeg);
	        reflectedImage2.setCenter(targetx, targety);
	        if (!coordsInReflectionList(targetx, targety)) {
	        	nextOrderReflection.add(reflectedImage2);
	        }
	        
	        //for neighbour3
	        refltriangdeg= orientation+180;
	        trinormalsizex = (int) (trinormalsize * Math.sin(Math.toRadians(refltriangdeg)));
		    trinormalsizey = (int) (trinormalsize * Math.cos(Math.toRadians(refltriangdeg)));
		    targetx = (int) (centerx + (  2 * trinormalsizex ) );
		    targety = (int) (centery - (  2 * trinormalsizey ) );
		    Triangle reflectedImage3 = new Triangle(aperturesize);
	        if (doHorizFlip) {
				reflectedImage3.doHorizontalFlip();
			}
	        reflectedImage3.setOrientation(refltriangdeg);
	        reflectedImage3.setCenter(targetx, targety);
	        if (!coordsInReflectionList(targetx, targety)) {
	        	nextOrderReflection.add(reflectedImage3);
	        }
		}
		return nextOrderReflection;
	}
	
	private Boolean coordsInReflectionList(int x, int y) {
		for (List<Triangle> reflectionOrder : reflectionOrdersList) {
			for (Triangle tri : reflectionOrder )
			if (tri.centerx < x+2 && tri.centerx > x-2 && tri.centery < y+2 && tri.centery > y-2) {
				return true;
			}
		}
		return false;
	}
	
	private Boolean isOutsideBounds(List<Triangle> nextOrderReflection) {
		for (Triangle tri : nextOrderReflection) {
			if (((tri.centerx < imagewidth) && (tri.centerx > 0)) && ((tri.centery < imageheight) && (tri.centery > 0))) {
				return false;
			}
		}
		return true;
	}
}
