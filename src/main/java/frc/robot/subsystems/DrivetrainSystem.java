package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DrivetrainSystem {
    WPI_VictorSPX leftMotorA = new WPI_VictorSPX(7);
    WPI_VictorSPX leftMotorB = new WPI_VictorSPX(8);
    MotorControllerGroup left = new MotorControllerGroup(leftMotorA, leftMotorB);
     
    WPI_VictorSPX rightMotorA = new WPI_VictorSPX(5);
    WPI_VictorSPX rightMotorB = new WPI_VictorSPX(6);
    MotorControllerGroup right = new MotorControllerGroup(rightMotorA, rightMotorB);

    private final DifferentialDrive robotDrive = new DifferentialDrive(left, right);

    public WPI_VictorSPX getleftMotorA() {
        return leftMotorA; 
    }
    public WPI_VictorSPX getleftMotorB() {
        return leftMotorB; 
    }
    public MotorControllerGroup getleft() {
        return left; 
    }
    public WPI_VictorSPX getrightMotorA() {
        return rightMotorA; 
    }
    public WPI_VictorSPX getrightMotorB() {
        return rightMotorB; 
    }
    public MotorControllerGroup getright() {
        return right; 
    }    
    public DifferentialDrive getRobotDrive() {
        return robotDrive; 
    }

    public double deadzone(double value) {
        double deadzone = 0.03; 
        if (Math.abs(value) < deadzone) {
            return 0; 
        }
        return value; 
    }

    public void setDriveMotors(double forward, double turn) {
        forward = deadzone(forward);
        turn = deadzone(turn);        

        SmartDashboard.putNumber("drive forward power (%)", forward);
        SmartDashboard.putNumber("drive turn power (%)", turn);
    
        double leftM = Math.pow((forward - turn),3);
        double rightM = Math.pow((forward + turn),3);
        //The pow applys a curve to the joystick level, allowing for more presice controllers and lower levels. 
    
        SmartDashboard.putNumber("drive left power (%)", leftM);
        SmartDashboard.putNumber("drive right power (%)", rightM);
    
        this.getleft().set(leftM);
        this.getright().set(rightM);
    }


}
