package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class Controllers {
    private static final XboxController Xbox = new XboxController(0);
    private static final XboxController Xbox1 = new XboxController(1);
    private static final Joystick Joy = new Joystick(2);

    
    public static boolean povUPs() {
        return (Xbox.getPOV() == 0 || Xbox.getPOV() == 45 || Xbox.getPOV() == 315);
    }
    public static boolean povDOWNs() {
        return (Xbox.getPOV() == 180 || Xbox.getPOV() == 135 || Xbox.getPOV() == 225);
    }

    public static XboxController main_Controller() {
        return Xbox; 
    }
    public static Joystick second_Controller() {
        return Joy; 
    }
    public static XboxController testing_Controller() {
        return Xbox1; 
    }
}  
