/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * The {@link DashAutonSelector} is an autonomous mode selector that works with Dashboard245 in order to allow the selection and execution of auton commands.
 * Inspired by the WPILibJ SendableChooser. Highly customized in order to better interact with NetworkTables.
 * 
 * @author Cannicide
 * @see edu.wpi.first.wpilibj.smartdashboard.SendableChooser
 */
public class DashAutonSelector {

    private String DEFAULT_COMMAND;
    private HashMap<String, Command> cmds;

    public DashAutonSelector() {
        cmds = new HashMap<String, Command>();
        DEFAULT_COMMAND = null;
        SmartDashboard.putString("autonomous/selected", "NONE");
    }

    private void putModes() {
        String modeSet = "";
        String delimiter = ",";
        int index = 0;

        for (String name : cmds.keySet()) {
            modeSet += name + (index + 1 == cmds.size() ? "" : delimiter);
            index += 1;
        }

        SmartDashboard.putString("autonomous/modes", modeSet);
    }

    /**
     * Adds an option to the bank of selectable autonomous modes.
     * 
     * @param name - A String name for the autonomous mode.
     * @param cmd - The Command to execute when this autonomous mode is selected.
     */
    public void addOption(String name, Command cmd) {
        if (name==null || cmd == null) throw new RuntimeException("A null object cannot be added to the DashAutonSelector.");
        if (DEFAULT_COMMAND == null) {
            DEFAULT_COMMAND = name;
        }
        cmds.put(name, cmd);
        putModes();
    }

    /**
     * Adds an option to the bank of selectable autonomous modes, and sets it as the default command.
     * The default command is run when no autonomous mode is selected.
     * 
     * @param name - A String name for the autonomous mode.
     * @param cmd - The Command to execute when this autonomous mode is selected.
     */
    public void setDefaultOption(String name, Command cmd) {
        DEFAULT_COMMAND = name;
        addOption(name, cmd);
    }

    /**
     * Gets the currently selected autonomous command, or the default command if none are selected.
     * 
     * @return The currently selected Command or default Command.
     */
    public Command getSelected() {
        String selected = SmartDashboard.getString("autonomous/selected", "NONE");

        if (selected.equals("NONE") && DEFAULT_COMMAND == null) {
            //No auton mode is selected, and no default command has been specified
            throw new RuntimeException("No autonomous commands have been specified, so none can be selected.");
        }
        else if (selected.equals("NONE")) {
            //No auton mode is selected, so use default command
            return cmds.get(DEFAULT_COMMAND);
        }
        else {
            //An auton mode is selected
            if (cmds.containsKey(selected)) {
                //The selected auton mode exists in the auton mode set
                return cmds.get(selected);
            }
            else {
                //The selected auton mode does not exist in the auton mode set, use default command
                throw new RuntimeException("The selected auton mode does not exist; it has not been added to the DashAutonSelector.");
            }
        }

    }

}
