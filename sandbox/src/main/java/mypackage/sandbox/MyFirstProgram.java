package mypackage.sandbox;

public class MyFirstProgram {
	public static void main(String[] args) {
		Point p1 = new Point(-1, 2);
		Point p2 = new Point(3, 2);
		System.out.println("Distance between points (" + p1.x + "; " + p1.y + ") and (" + p2.x + "; " + p2.y + ") = " + p1.distance(p2));
	}

}

