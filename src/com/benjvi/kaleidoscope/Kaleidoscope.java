package com.benjvi.kaleidoscope;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Kaleidoscope {
	final private int imagewidth, imageheight;
	final private int outputcenterx, outputcentery;
	final private int aperturesize;
	final private double orientationdeg;
	//final private double zoom;
	final private List<List<Triangle>> reflectionOrdersList;
	final LinkedList<Triangle> allCells;
	
	public Kaleidoscope (int panelwidth, int panelheight, int zoom) {
		reflectionOrdersList = new ArrayList<List<Triangle>>();
		this.imagewidth = panelwidth;
		this.imageheight = panelheight;
		
		//set default parameters
		outputcenterx = imagewidth/2;
		outputcentery = imageheight/2;
		aperturesize = Math.min(imagewidth, imageheight)/zoom;
	    orientationdeg = 0;
	    
	    LinkedList<Triangle> primitive = createPrimitiveCell();
	    allCells = tessellate(primitive);
	    //generateTransforms();
		//usethe panelwidth and panelheight to set the limits of thes generated kaleidoscope
	}
	
	public Kaleidoscope (int panelWidth, int panelHeight) {
		this(panelWidth,panelHeight, 10);
	}
	/*
	private void generateTransforms() {
		//define the bounds of the the initial triangle 
		//this is the image produced by light which goes straight through the kaleidoscope 
		//without hitting any mirrors
		List<Triangle> nextOrderReflection;
		Triangle origtri = createUnreflectedImage();
		ArrayList<Triangle> origTriList = new ArrayList<Triangle>();
		origTriList.add(origtri);
		//step through the transformations to define all the triangles we will need
		//to produce a kaleidoscope pattern with the defined dimensions
		reflectionOrdersList.add(origTriList);
		nextOrderReflection = createNextOrderReflection(origTriList);
		reflectionOrdersList.add(nextOrderReflection);
		while(!isOutsideBounds(nextOrderReflection) && reflectionOrdersList.size()<30) {
			nextOrderReflection = createNextOrderReflection(nextOrderReflection);
			reflectionOrdersList.add(nextOrderReflection);
		}


	}
	*/
	public LinkedList<Triangle> createPrimitiveCell() {
		LinkedList<Triangle> primitiveCell = new LinkedList<Triangle>();
		
		//create the six triangles forming the primitive cell
		Double orientRad = Math.toRadians(orientationdeg);
		
		//base triangle
		Triangle one = new Triangle(aperturesize);
		one.setOrientation(orientationdeg);
		long centerx = Math.round(aperturesize*(Math.sin(orientRad)));
		long centery = Math.round(aperturesize*(Math.cos(orientRad)));
		one.setCenter((int)centerx, (int)centery);
		primitiveCell.add(one);
		long onecenterx = centerx;
		long onecentery = centery;
		
		//two triangles are not reflected - they are 
		//1. rotated by 60 degrees clockwise/anticlosewise respectively
		//2. translated upwards by aperturesize in  sin orientationdeg xh, cos orientationdeg yh 
		//and futher translated by aperturesize in sin newtriorientdeg xh, cos newtriangleorientationdeg yh
		Triangle two = new Triangle(aperturesize);
		two.setOrientation(orientationdeg+120);
		double triOrientRad = Math.toRadians(two.getOrientation()+180);
		centerx = (long)Math.ceil(aperturesize*(+Math.sin(triOrientRad)));
		centery = (long) Math.ceil(aperturesize*(-Math.cos(triOrientRad)));
		two.setCenter((int)centerx+1, (int)centery+1);
		primitiveCell.add(two);
		
		Triangle three = new Triangle(aperturesize);
		three.setOrientation(orientationdeg-120);
		triOrientRad = Math.toRadians(three.getOrientation()+180);
		centerx = (long)Math.floor(aperturesize*(Math.sin(triOrientRad)));
		centery = (long)Math.ceil(aperturesize*(-Math.cos(triOrientRad)));
		three.setCenter((int)centerx, (int)centery+1);
		primitiveCell.add(three);
		
		//doing reflected triangles
		int trinormalsize = (int)(aperturesize * Math.sin(Math.toRadians(30)));
		
		//for reflected neighbour 1
        Triangle four = new Triangle(aperturesize);
		four.doHorizontalFlip();
        four.setOrientation(orientationdeg+60);
		centerx = onecenterx + Math.round(-2 * trinormalsize * Math.cos(Math.toRadians(four.getOrientation() - 30)));
        centery = onecentery + Math.round(-2 * trinormalsize * Math.sin(Math.toRadians(four.getOrientation() - 30)));
        four.setCenter((int)centerx, (int)centery-1);
        primitiveCell.add(four);
        
        //for reflected neighbour2
        Triangle five = new Triangle(aperturesize);
        five.doHorizontalFlip();
        five.setOrientation(orientationdeg-60);
        centerx = onecenterx + Math.round(2 * trinormalsize * Math.cos(Math.toRadians(five.getOrientation() + 30)));
	    centery = onecentery + Math.round(2 * trinormalsize * Math.sin(Math.toRadians(five.getOrientation() + 30)));
        five.setCenter((int)centerx, (int)centery-1);
        primitiveCell.add(five);
        
        Triangle six = new Triangle(aperturesize);
        six.doHorizontalFlip();
		six.setOrientation(orientationdeg+180);
		triOrientRad = Math.toRadians(six.getOrientation());
		centerx = Math.round(aperturesize*-1*Math.sin(orientRad));
		centery = (long)Math.ceil(aperturesize*-1*Math.cos(orientRad)+1);
		six.setCenter((int)centerx, (int)centery);
		primitiveCell.add(six);
		

		
		return primitiveCell;
	}
	
	private LinkedList<Triangle> tessellate(LinkedList<Triangle> primitiveCell) {
		//this is an interesting method; we could implement the strategy pattern here if we needed to tesselate different primitives
		//for now - we only need to tesselate our set of 6 triangles
		//TODO - define unitCellSize properly - this is a geometric relation to aperturesize
		// (and should therefore be an instance variable)
		double unitCellSize = 2.9*aperturesize;
		int vertTransUnitX = (int)Math.round(unitCellSize*Math.sin(Math.toRadians(orientationdeg)));
		int vertTransUnitY = (int)Math.round(unitCellSize*Math.cos(Math.toRadians(orientationdeg)));
		int horizTransUnitX = (int)Math.round(unitCellSize*Math.sin(Math.toRadians(orientationdeg+60)));
		int horizTransUnitY = (int)Math.round(unitCellSize*Math.cos(Math.toRadians(orientationdeg+60)));
		long cellCenterX = 0;
		long cellCenterY = 0;
		LinkedList<Triangle> currCell = new LinkedList<Triangle>(primitiveCell);
		LinkedList<Triangle> rowStartCell = new LinkedList<Triangle>(primitiveCell);
		LinkedList<Triangle> allCells = new LinkedList<Triangle>(primitiveCell);
		Triangle currTri;
		//know that first set of triangles is centred at 0,0
		//translate in increasing y (subject to orientation inputted)
		//until the new point is past the boundary (we will do one last iteration that is beyond the boundary)
		long colStartX = cellCenterX;
		long colStartY = cellCenterY;
		while(colStartX < imagewidth+100) {
			cellCenterX = colStartX;
			cellCenterY = colStartY;
			//TODO: we need to store the cell data for the start of the row, 
			//and then start by translating that horizontally once before we go into the column loop
			
			while(!isPointOutsideBounds(cellCenterX, cellCenterY)) {
			//for (int j=0; j<4; j++) {
				for (int i=0; i<6; i++) {
					currTri = currCell.poll().clone();
					currTri.setCenter(currTri.getXPosition()+vertTransUnitX, currTri.getYPosition()+vertTransUnitY);
					allCells.offer(currTri);	
					currCell.offer(currTri);
				}
				cellCenterY+=vertTransUnitY;
				cellCenterX+=vertTransUnitX;
			}
			for (int i=0; i<6; i++) {
				currTri = rowStartCell.poll().clone();
				currTri.setCenter(currTri.getXPosition()+horizTransUnitX, currTri.getYPosition()+horizTransUnitY);
				allCells.offer(currTri);	
				rowStartCell.offer(currTri);
			}
			currCell = new LinkedList<Triangle>(rowStartCell);
			colStartY+=horizTransUnitY;
			colStartX+=horizTransUnitX;
			horizTransUnitY *= -1;
		}
		return allCells;
	}
	/*
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
		int c = aperturesize/2;
		for (List<Triangle> reflectionOrder : reflectionOrdersList) {
			for (Triangle tri : reflectionOrder ) {
				if ((tri.centerx < x+c && tri.centerx > x-c) && (tri.centery < y+c && tri.centery > y-c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private Boolean isOutsideBounds(List<Triangle> nextOrderReflection) {
		for (Triangle tri : nextOrderReflection) {
			if (!(((tri.centerx < imagewidth) && (tri.centerx > 0)) || ((tri.centery < imageheight) && (tri.centery > 0)))) {
				return true;
			}
		}
		return false;
	}
	*/
	private Boolean isPointOutsideBounds(long xcoord, long ycoord) {
		//TODO: 100 here is a "fudge factor" - should calc this wrt calculated primitive size
		if (!(((xcoord < imagewidth+100) && (xcoord > -100)) && ((ycoord < imageheight+100) && (ycoord > -100)))) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getWidth() {
		return imagewidth;
	}
	
	public int getHeight() {
		return imagewidth;
	}
	
	public List<List<Triangle>> getReflectionsList() throws UnsupportedOperationException {
		return Collections.unmodifiableList(reflectionOrdersList);
	}
}

