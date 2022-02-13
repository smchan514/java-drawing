package simple_drawing;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

public class MultiSegmentLine implements Iterable<Point2D> {
    private final ArrayList<Point2D> _points;

    public MultiSegmentLine(int numberSegments) {
        _points = new ArrayList<>(numberSegments + 1);
        for (int i = 0; i < numberSegments + 1; i++) {
            _points.add(new Point2D.Double());
        }
    }

    public Point2D getPoint(int index) {
        return _points.get(index);
    }

    public void setPoint(int index, Point pt) {
        _points.get(index).setLocation(pt);
    }

    public void setPoint(int index, Point2D pt) {
        _points.get(index).setLocation(pt);
    }

    public void movePoint(int index, Point2D pt1) {
        Point2D pt0 = _points.get(index);
        pt0.setLocation(pt0.getX() + pt1.getX(), pt0.getY() + pt1.getY());
    }

    @Override
    public Iterator<Point2D> iterator() {
        return _points.iterator();
    }
}
