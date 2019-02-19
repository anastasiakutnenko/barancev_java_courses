package mypackage.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistance() {
        Point p1 = new Point(11,1);
        Point p2 = new Point(1,1);
        assert p1.distance(p2) == 10.0;
        Assert.assertEquals(p1.distance(p2),10.0 );
    }

    @Test
    public void testDistanceIsNotNegative() {
        Point p1 = new Point(11, 1);
        Point p2 = new Point(1, 1);
        Assert.assertFalse(p1.distance(p2) < 0, "distance can NOT be negative");
    }

    @Test
    public void testDistanceIsReversal() {
        Point p1 = new Point(11,1);
        Point p2 = new Point(1,1);
        Assert.assertEquals(p1.distance(p2),p2.distance(p1));

    }
}