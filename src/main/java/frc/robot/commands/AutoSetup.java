package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoSetup {
    public static final String kNothingAuto = "do nothing";
    public static final String kDropAuto = "cone or cube";
    public static final String kDropMoveAuto = "cone / cube and back up";

    String selectedOption;
    
    final SendableChooser<String> m_chooser; 

    public AutoSetup() {
        m_chooser = new SendableChooser<>();
    }


    public void setUpChooser() {
        m_chooser.setDefaultOption("cone / cube", kDropAuto);
        m_chooser.addOption("do nothing", kNothingAuto);
        m_chooser.addOption("cone / cube and back up", kDropMoveAuto);
    }

    public SendableChooser<String> getChooser() {
        return m_chooser; 
    }

    public void choosenSelection() {
        selectedOption = m_chooser.getSelected();
        System.out.println("Auto selected: " + selectedOption);
    }

    public String getSelectedOption() {
        return selectedOption; 
    }

}
