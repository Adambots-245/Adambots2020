/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import frc.robot.subsystems.BlasterSubsystem;

/**
 * Add your docs here.
 */
@RunWith(MockitoJUnitRunner.class) 
public class BlasterSubsytemTest {

    @Mock
    WPI_TalonFX blasterMotor;
    @Mock
    Solenoid blasterHood;

    @Test
    public void testStarter(){
        // blasterMotor = mock(WPI_TalonFX.class);
        // blasterHood = mock(Solenoid.class);
        BlasterSubsystem blaster = new BlasterSubsystem(blasterMotor, blasterHood);

        final double[] speed = {0};

        doAnswer((x) -> {
            speed[0] = (double) x.getArgument(1);
            System.out.println("Speed in Lambda:" + speed[0]);
            return null;
        }).when(blasterMotor).set(ControlMode.PercentOutput, 0.85);

        when(blasterMotor.getSelectedSensorVelocity()).thenReturn(10);

        blaster.output(0.85);
        System.out.println("Speed in GetVelocity:" + blaster.getVelocity());

        assertEquals(10, blaster.getVelocity(), 0.001);
    }
}
