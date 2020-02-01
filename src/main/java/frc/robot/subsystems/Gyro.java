package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Gyro extends SubsystemBase{

    private static Timer timer = new Timer();
    public float roll;
    public float pitch;
    public float yaw;

    public void init() {
        try {
            /* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            public static AHRS ahrs = new AHRS(SerialPort.Port.kMXP); //PUT IN PORT!!!    
        } catch (RuntimeException ex) {
            System.out.println("Error instantiating navX-MXP:  " + ex.getMessage());
        }    
        timer.start();
    }
     
    public void values() {
        
        boolean isCalibrating = ahrs.getIsCalibrating();
        if (isCalibrating = false) {
            public float getRoll() {
                return ahrs.getRoll();
            }
            public float getPitch() {
                return ahrs.getPitch();
            }
            public float getYaw() {
                return ahrs.getYaw();
            }
        } else {
            timer.delay(0.02);
        }
    }

}