import java.awt.Polygon;


public class TriangleFactory {
	
	private TriangleFactory() {}
	
	public static Polygon getTrianglePolygon(int size) {
		Polygon triangle = new Polygon();
		//origin is at the centre of the triangle
		int point1x = (int)Math.round( (double) size * Math.sin(Math.toRadians(180))); 
		int point1y = (int)Math.round( (double) size * Math.cos(Math.toRadians(180)));
		triangle.addPoint(point1x, point1y);
		int point2x = (int)Math.round( (double) size * Math.sin(Math.toRadians(60))); 
		int point2y = (int)Math.round( (double) size * Math.cos(Math.toRadians(60))); 
		triangle.addPoint(point2x, point2y);
		int point3x = (int)Math.round( (double) size * Math.sin(Math.toRadians(-60))); 
		int point3y = (int)Math.round( (double) size * Math.cos(Math.toRadians(-60)));
		triangle.addPoint(point3x, point3y);
		return triangle;
	}

}
