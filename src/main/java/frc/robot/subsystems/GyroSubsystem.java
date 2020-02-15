package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroSubsystem extends SubsystemBase {

    public float roll;
    public float pitch;
    public float yaw;

    private AHRS ahrs;

    public GyroSubsystem() {
        try {
            /* Communicate w/navX-MXP via the MXP SPI Bus. */
            /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB */
            /*
             * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
             * details.
             */
            ahrs = new AHRS(SerialPort.Port.kMXP); // PUT IN PORT!!!
        } catch (RuntimeException ex) {
            System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
        }
    }

    public void calibrationCheck() {

        boolean isCalibrating = ahrs.isCalibrating();
        if (isCalibrating) {
            Timer.delay(0.02);

        }
    }

    public float getRoll() {
        calibrationCheck();
        return ahrs.getRoll();
    }

    public float getPitch() {
        calibrationCheck();
        return ahrs.getPitch();
    }

    public float getYaw() {
        calibrationCheck();
        return ahrs.getYaw();
    }

}