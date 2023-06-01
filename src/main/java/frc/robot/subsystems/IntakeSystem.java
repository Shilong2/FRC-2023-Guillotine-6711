package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class IntakeSystem{

double CS = 0.0; 
double clawResetTime = 0; 
boolean clawCompleteOff = false; 

WPI_VictorSPX claw = new WPI_VictorSPX(3);

    public WPI_VictorSPX getClaw() {
        return claw; 
    }

    public void closeClaw() {
        CS = 0.5; 
    }

    public void openClaw() {
        clawResetTime = Timer.getFPGATimestamp();
        CS = -0.5; 
        clawCompleteOff = true;
    }
/* 
    public void zeroClaw() {
        if ((Timer.getFPGATimestamp() - clawResetTime) > 0.5 && clawCompleteOff){
            CS = 0; 
            clawCompleteOff = false; 
        }
    }
*/
    public double getCS() {
        return CS; 
    }

    public void setCS(double mCS) {
        CS = mCS; 
    }

    public void setClawMotor(double percent) {
        claw.set(ControlMode.PercentOutput, percent);
    }

    public void periodic(){ 
        if ((Timer.getFPGATimestamp() - clawResetTime) > 0.5 && clawCompleteOff){
            CS = 0; 
            clawCompleteOff = false; 
        }
        
        SmartDashboard.putNumber("Claw power (%)", CS);
        SmartDashboard.putNumber("Claw motor current Motor Output (volts)", claw.getMotorOutputVoltage());
        SmartDashboard.putNumber("Claw motor temperature (C)", claw.getTemperature());
    }

}
