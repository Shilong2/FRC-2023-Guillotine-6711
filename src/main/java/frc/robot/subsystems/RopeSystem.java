package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Controllers;

public class RopeSystem{
    
    double PS = 0; 
    WPI_VictorSPX pulley = new WPI_VictorSPX(2);

    public WPI_VictorSPX getPulley() { 
        return pulley; 
    }

    public void fastUp(){
        PS = 0.5;
        //Up needs strength
    }

    public void slowUp(){
        PS = 0.3; 
    }

    public void fastDown(){
        PS = -0.3; 
        //Down need little
    }

    public void slowDown(){
        PS = -0.1; 
    }

    public void zeroPS() {
        PS = 0; 
        // Turn off PS speed
    }

    public void slowPS() {
        PS = 0.1; 
        // Slowly go up for Pulley to stay in place
    }

    public double getPS() {
        return PS; 
    }

    public void setPS(double mPS) {
        PS = mPS; 
    }
    
    public void setPulleyMotor(double percent) {
        this.getPulley().set(ControlMode.PercentOutput, percent);
    }

    public void periodic() {
        if (Controllers.main_Controller().getYButtonReleased() || Controllers.main_Controller().getXButtonReleased()) {
            slowPS();
        } else if (Controllers.main_Controller().getAButtonReleased() || Controllers.main_Controller().getBButtonReleased()) {
            zeroPS(); 
        } 

        SmartDashboard.putNumber("Pulley power (%)", PS);
        SmartDashboard.putNumber("Pulley motor current Motor Output (volts)", pulley.getMotorOutputVoltage());
        SmartDashboard.putNumber("Pulley motor temperature (C)", pulley.getTemperature());
    }

}

