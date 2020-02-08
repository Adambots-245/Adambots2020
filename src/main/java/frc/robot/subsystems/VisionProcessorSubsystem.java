package frc.robot.subsystems;

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
        processedOutputStream = CameraServer.getInstance().putVideo("Output", Constants.IMG_WIDTH, Constants.IMG_HEIGHT);
        processedOutputStream.setVideoMode(PixelFormat.kMJPEG, Constants.IMG_WIDTH, Constants.IMG_HEIGHT, Constants.DRIVER_STATION_FPS);
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
        while (!Thread.interrupted()) {
            crosshair = null;
            if (cvSink.grabFrame(mat) == 0) {
                processedOutputStream.notifyError(cvSink.getError());
                continue;

            }

            grip.process(mat);

            RotatedRect[] rects = findBoundingBoxes();
            draw(rects);

            if (crosshair != null) {
                synchronized (lock) {
                    calculateAngle();

                }
                
            }

            processedOutputStream.putFrame(mat);

        }

    }

    public RotatedRect[] findBoundingBoxes() {
        ArrayList<MatOfPoint> contours = grip.filterContoursOutput();
        RotatedRect[] rects = new RotatedRect[contours.size()];
        for (int i = 0; i < contours.size(); i++)
            rects[i] = Imgproc.minAreaRect(new MatOfPoint2f(contours.get(i).toArray()));

        rects = filterBoundingBoxes(rects);

        return rects;

    }

    public RotatedRect[] filterBoundingBoxes(RotatedRect[] rects) {
        ArrayList<RotatedRect> newRects = new ArrayList<RotatedRect>();

        for (int i = 0; i < rects.length; i++) {
            double ratio = rects[i].size.width / rects[i].size.height;
            if (1 / ratio > ratio) ratio = 1 / ratio;

            if (ratio > 1.5 && ratio < 3.5) newRects.add(rects[i]);

        }

        rects = new RotatedRect[newRects.size()];
        for (int i = 0; i < newRects.size(); i++) {
            rects[i] = newRects.get(i);
        }

        return rects;

    }

    public void draw(RotatedRect[] rects) {
        if (rects.length != 0) {
            // Loop that gets and draws all rotated rectangles
            for (int i = 0; i < rects.length; i++) {
                rects[i].points(pts);
                drawRect(pts);
                findCrosshair(pts);

            }

            if (crosshair != null)
                drawCrosshair();

        }

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

    // Getter for angle
    public double getAngle() { 
        return angle;
    }

    public Thread getVisionThread() {
        return visionThread;
    }

}