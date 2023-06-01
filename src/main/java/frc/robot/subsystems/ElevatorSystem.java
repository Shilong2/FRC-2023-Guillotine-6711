package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Controllers;

public class ElevatorSystem{

    double ES = 0.0; 
    boolean wasPOV0Pressed = false; 
    boolean wasPOV180Pressed = false; 

    WPI_VictorSPX elv1 = new WPI_VictorSPX(1); 
    WPI_VictorSPX elv2 = new WPI_VictorSPX(4);
    MotorControllerGroup elv = new MotorControllerGroup(elv1, elv2);

    public WPI_VictorSPX getElv1() {
        return elv1;
    }
    public WPI_VictorSPX getElv2() {
        return elv2; 
    }
    public MotorControllerGroup getElv() {
        return elv; 
    }

    public void UpElv() {
        ES = 0.5; 
        wasPOV0Pressed = true; 
    }

    public void DownElv() {
        ES = 0.1; 
        wasPOV180Pressed = true;
    }

    /*
    public void elvBaseOnLastMove() {
        if (wasPOV0Pressed) {
            ES = 0.1; 
            wasPOV0Pressed = false; 
            //was up, now going up slowly
        } else if (wasPOV180Pressed) {
            ES = 0; 
            wasPOV180Pressed = false;
            //was down, now set to 0
        }
    }
    */
    public double getES() {
        return ES; 
    }

    public void setES(double mES) {
        ES = mES; 
    }
    
    public void setElvMotor(double percent) {
        this.getElv1().set(ControlMode.PercentOutput, -percent);
        this.getElv2().set(ControlMode.PercentOutput, percent);

    }

    public void periodic(){ 
        SmartDashboard.putNumber("Elv1 power (%)", ES);
        //SmartDashboard.putNumber("Elv1 motor current Motor Output (volts)", elv1.getMotorOutputVoltage());
        //SmartDashboard.putNumber("Elv1 motor temperature (C)", elv1.getBusVoltage());

        SmartDashboard.putNumber("Elv2 power (%)", ES);
        //SmartDashboard.putNumber("Elv2 motor current Motor Output (volts)", elv2.getMotorOutputVoltage());
        //SmartDashboard.putNumber("Elv2 motor temperature (C)", elv2.getBusVoltage());    

        if (Controllers.main_Controller().getPOV() == -1){
            if (wasPOV0Pressed) {
                ES = 0.1; 
                wasPOV0Pressed = false; 
                //was up, now going up slowly
            } else if (wasPOV180Pressed) {
                ES = 0; 
                wasPOV180Pressed = false;
                //was down, now set to 0
            } 
        }
    }

}
