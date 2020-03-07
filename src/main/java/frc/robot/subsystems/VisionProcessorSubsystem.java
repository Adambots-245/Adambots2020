package frc.robot.subsystems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.vision.GripPipeline;
import edu.wpi.cscore.*;
import edu.wpi.cscore.VideoMode.PixelFormat;
import edu.wpi.first.networktables.*;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class VisionProcessorSubsystem extends SubsystemBase {

    private static UsbCamera camera;
    private static CvSource processedOutputStream;
    private static CvSink cvSink;
    private static GripPipeline grip;
    private static Mat mat;
    private static Point crosshair;
    private static Point[] pts = new Point[4];
    private static int pixelDistance;
    private static double angle;
    private Object lock = new Object();
    private Thread visionThread;
    private NetworkTableEntry angleEntry;

    public VisionProcessorSubsystem() {
        init();
    }

    public void init() {
        camera = CameraServer.getInstance().startAutomaticCapture(Constants.CAM_NUMBER);
        camera.setExposureManual(Constants.CAM_EXPOSURE);
        processedOutputStream = CameraServer.getInstance().putVideo("Output", Constants.IMG_WIDTH, Constants.IMG_HEIGHT);

        cvSink = CameraServer.getInstance().getVideo();
        grip = new GripPipeline();
        mat = new Mat();

        camera.setVideoMode(VideoMode.PixelFormat.kMJPEG, Constants.IMG_WIDTH, Constants.IMG_HEIGHT, Constants.PROCESSING_FPS);
        // camera.setPixelFormat(VideoMode.PixelFormat.kMJPEG);
        // camera.setFPS(Constants.PROCESSING_FPS);
        // camera.setResolution(Constants.IMG_WIDTH, Constants.IMG_HEIGHT);

        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        NetworkTable table = instance.getTable("Vision");
        angleEntry = table.getEntry("Angle");

        visionThread = new Thread(() -> {
            run();
        });

    }

    public void run() {
        // Main vision loop
        int frameCount = 0;
        while (!Thread.interrupted()) {
            crosshair = null;
            if (cvSink.grabFrame(mat) == 0) {
                processedOutputStream.notifyError(cvSink.getError());
                continue;

            }

            grip.process(mat);

            RotatedRect[] rects = findBoundingBoxes();
            if (rects.length != 0) {
                RotatedRect rect = findLargestRect(rects);
                draw(rect);
            }

            if (crosshair != null) {
                synchronized (lock) {
                    calculateAngle();

                }
                
            }

            if (frameCount == (Constants.PROCESSING_FPS / Constants.DRIVER_STATION_FPS)) {
                processedOutputStream.putFrame(mat);
                frameCount = 0;
            }

            frameCount++;

        }

    }

    public RotatedRect[] findBoundingBoxes() {
        ArrayList<MatOfPoint> contours = grip.filterContoursOutput();
        ArrayList<MatOfPoint> convexContours = grip.convexHullsOutput();
        contours = filterContoursByVertices(contours, convexContours);
        System.out.println(contours.size());
        RotatedRect[] rects = new RotatedRect[contours.size()];
        for (int i = 0; i < contours.size(); i++)
            rects[i] = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));

        return rects;

    }

    public ArrayList<MatOfPoint> filterContoursByVertices(ArrayList<MatOfPoint> contours, ArrayList<MatOfPoint> convexContours) {
        Point[] reducedContoursPoints, reducedConvexPoints;
        MatOfPoint2f contourSet, convexSet, reducedContourSet, reducedConvexSet;
        double perimeter, convexPerimeter;
        boolean contourRemoved;

        for (int i = 0; i < contours.size(); i++) {
            contourRemoved = false;

            // work with contourSet and convexSet instead so proper data type is used
            contourSet = new MatOfPoint2f(contours.get(i).toArray());
            convexSet = new MatOfPoint2f(convexContours.get(i).toArray());

            // gets perimeter of target
            perimeter = Imgproc.arcLength(contourSet, true);
            convexPerimeter = Imgproc.arcLength(convexSet, true);

            // create reduced sets
            reducedContourSet = new MatOfPoint2f();
            reducedConvexSet = new MatOfPoint2f();

            // puts reduced contours into reducedContourSet and reducedConvexSet
            Imgproc.approxPolyDP(contourSet, reducedContourSet, Constants.TARGET_APPROXIMATION_ACCURACY * perimeter, true);
            Imgproc.approxPolyDP(convexSet, reducedConvexSet, Constants.TARGET_APPROXIMATION_ACCURACY * convexPerimeter, true);

            // create and fill points array with vertices
            reducedContoursPoints = reducedContourSet.toArray();
            reducedConvexPoints = reducedConvexSet.toArray();

            // if too many or too few vertices, remove
            if (reducedContoursPoints.length > Constants.TARGET_VERTICES + Constants.TARGET_VERTICES_MOE ||
                reducedContoursPoints.length < Constants.TARGET_VERTICES - Constants.TARGET_VERTICES_MOE ||
                reducedConvexPoints.length > Constants.CONVEX_TARGET_VERTICES + Constants.TARGET_VERTICES_MOE ||
                reducedConvexPoints.length < Constants.CONVEX_TARGET_VERTICES - Constants.TARGET_VERTICES_MOE) {
                contours.remove(i);
                convexContours.remove(i);
                i--;
                contourRemoved = true;

            }

            if (!contourRemoved) {
                // inits bounding box and arrays for points
                Point[] pts = new Point[4];
                Point[] cpts = reducedContourSet.toArray();
                RotatedRect rect = Imgproc.minAreaRect(reducedContourSet);
                rect.points(pts);

                // inits points that will be useful
                Point topLeft = pts[0], topRight = pts[0];
                Point vertexTL = cpts[0], vertexTR = cpts[0], vertexBL = cpts[0], vertexBR = cpts[0];

                // finds top vertices of bounding box to assist in finding top vertices of contour
                for (int j = 0; j < pts.length; j++) {
                    if (pts[j].y < rect.center.y && pts[j].x < rect.center.x)
                        topLeft = pts[j];
                    if (pts[j].y < rect.center.y && pts[j].x > rect.center.x)
                        topRight = pts[j];
                        
                }

                // target center (important later)
                Point centerpt = new Point((topLeft.x + topRight.x) / 2, (topLeft.y + topRight.y) / 2);

                // finds needed vertices of contour
                for (int j = 1; i < cpts.length; i++) {
                    if (calcDist(topLeft, cpts[j]) < calcDist(topLeft, cpts[j]))
                        vertexTL = cpts[j];
                    if (calcDist(topRight, cpts[j]) < calcDist(topRight, cpts[j]))
                        vertexTR = cpts[j];
                    if (cpts[j].x < centerpt.x && cpts[j].y > vertexBL.y)
                        vertexBL = cpts[j];
                    if (cpts[j].x > centerpt.x && cpts[j].y > vertexBR.y)
                        vertexBR = cpts[j];

                }
                
                // removes contour if top is not longer than bottom
                if (calcDist(vertexTL, vertexTR) <= calcDist(vertexBL, vertexBR)) {
                    contours.remove(i);
                    convexContours.remove(i);
                    i--;
                    contourRemoved = true;

                }

            }

        }

        return contours;
    }

    public RotatedRect findLargestRect(RotatedRect[] rects) {
        RotatedRect rect = rects[0];
        for (int i = 0; i < rects.length; i++) {
            if (rects[i].size.area() > rect.size.area())
                rect = rects[i];

        }

        return rect;
    }

    public void draw(RotatedRect rect) {

        rect.points(pts);
        drawRect(pts);
        findCrosshair(pts);

        if (crosshair != null)
            drawCrosshair();

    }

    // Draw bounding box around the reflective tape
    public void drawRect(Point[] pts) {
        for (int i = 0; i < 4; i++)
            Imgproc.line(mat, pts[i], pts[(i + 1) % 4], Constants.GREEN, 1);

    }

    // Calculate the crosshair position
    public void findCrosshair(Point[] pts) {
        // i is starting point for line, j is next point
        int j;
        for (int i = 0; i < 4; i++) {
            j = (i + 1) % 4;
            if (crosshair == null || (pts[i].y + pts[j].y) / 2 < crosshair.y)
                crosshair = new Point((pts[i].x + pts[j].x) / 2, (pts[i].y + pts[j].y) / 2);

        }

    }

    // Draw the crosshair on the frame
    public void drawCrosshair() {
        Imgproc.line(mat, new Point(crosshair.x - 5, crosshair.y - 5), new Point(crosshair.x + 5, crosshair.y + 5), Constants.RED, 3);
        Imgproc.line(mat, new Point(crosshair.x - 5, crosshair.y + 5), new Point(crosshair.x + 5, crosshair.y - 5), Constants.RED, 3);

    }

    // Calculate horizontal turret angle
    public void calculateAngle() {
        pixelDistance = (int) crosshair.x - Constants.IMG_HOR_MID;
        angle = pixelDistance * Constants.HOR_DEGREES_PER_PIXEL;
        angleEntry.setDouble(angle);

    }

    // Calculate distance between two points
    public double calcDist(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    // Getter for angle
    public double getAngle() { 
        return angle;
    }

    public Thread getVisionThread() {
        return visionThread;
    }

}