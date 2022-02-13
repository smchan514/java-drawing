package simple_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

public class DrawingComponent extends JComponent {

    private static final long serialVersionUID = 1L;

    private MultiSegmentLine[] _lines = new MultiSegmentLine[0];
    private int _numberLines = 0;
    private int _numberSegments = 0;
    private int _pointDistance = 10;

    private boolean _firstDraw = true;


    public DrawingComponent() {
        setPreferredSize(new Dimension(600, 600));
    }

    /**
     * @param numberLines    total number of lines
     * @param numberSegments number of segments per line
     */
    public void setMultiSegmentLines(int numberLines, int numberSegments, int pointDistance) {
        _numberLines = numberLines;
        _numberSegments = numberSegments;
        _pointDistance = pointDistance;

        // Create the lines model
        _lines = new MultiSegmentLine[numberLines];
        for (int i = 0; i < _lines.length; i++) {
            _lines[i] = new MultiSegmentLine(numberSegments);
        }
    }

    /**
     * This method is called on "repaint".
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (_firstDraw) {
            // Initialize the lines model if this is the first time we draw
            _firstDraw = false;
            initialize(getSize());
        }
        else {
            // Otherwise update lines model for each step
            updateStep();
        }

        // Graphics2D is the "new" drawing interface (in ~2005 standard)
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        drawLines(g2d);
    }

    /**
     * Initialize the models to their starting values
     * 
     * @param dimension size of the drawing area
     */
    private void initialize(Dimension size) {
        // Center of the component
        int cx = size.width / 2;
        int cy = size.width / 2;

        // Position the lines around a circle and radiate out
        for (int i = 0; i < _lines.length; i++) {
            // Starting "angle" of each line
            double angle = i * (2 * Math.PI) / _numberLines;
            
            int distance = _pointDistance;
            for (int z = 0; z < _numberSegments + 1; z++) {
                // Convert from "polar coordinates" to "Cartesian coordinates"
                Point2D.Double pt = polarToCartesian(angle, distance);

                // Move the point to the center of the drawing component
                pt.x += cx;
                pt.y += cy;

                _lines[i].setPoint(z, pt);

                // Increase the distance for the next point in the line
                distance += _pointDistance;
            }
        }
    }

    /**
     * Recompute the models at each step
     */
    private void updateStep() {
        for (int i = 0; i < _lines.length; i++) {
            for (int z = 0; z < _numberSegments; z++) {
                // Generate a new point where X and Y vary between -0.5 and 0.5
                Point2D.Double pt = new Point2D.Double(Math.random() - 0.5, Math.random() - 0.5);
                _lines[i].movePoint(z + 1, pt);
            }
        }
    }

    private void drawBackground(Graphics2D g2d) {
        g2d.setBackground(Color.BLACK);
        Dimension size = getSize();
        g2d.clearRect(0, 0, size.width, size.height);
    }

    /**
     * @param g2d
     */
    private void drawLines(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);

        // Create a path for each line
        for (int i = 0; i < _lines.length; i++) {
            GeneralPath path = new GeneralPath();
            boolean firstPoint = true;

            for (Point2D pt : _lines[i]) {
                if (firstPoint) {
                    firstPoint = false;
                    path.moveTo(pt.getX(), pt.getY());
                } else {
                    path.lineTo(pt.getX(), pt.getY());
                }
            }

            g2d.draw(path);
        }
    }

    /**
     * Convert from a point in "polar coordinates" (angle, distance) and return a
     * point in "Cartesian coordinates" (x, y)
     * 
     * @param angle    angle from 0 to 2PI
     * @param distance distance in pixels
     * @return a Point2D in Cartesian coordinates
     */
    private Point2D.Double polarToCartesian(double angle, int distance) {
        double x = Math.cos(angle) * distance;
        double y = Math.sin(angle) * distance;

        return new Point2D.Double(x, y);
    }

}
