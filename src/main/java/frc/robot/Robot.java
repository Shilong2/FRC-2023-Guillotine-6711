// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DrivetrainSystem;
import frc.robot.subsystems.ElevatorSystem;
import frc.robot.subsystems.IntakeSystem;
import frc.robot.subsystems.RopeSystem;
import frc.robot.Controllers;
import frc.robot.commands.AutoSetup;  

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private final DrivetrainSystem Drive = new DrivetrainSystem(); 
  private final ElevatorSystem Elv = new ElevatorSystem();
  private final RopeSystem Pulley = new RopeSystem();
  private final IntakeSystem Claw = new IntakeSystem(); 

  private static final XboxController Xbox = Controllers.main_Controller();
  private static final XboxController Xbox1 = Controllers.testing_Controller(); 
  
  public Timer m_Timer = new Timer(); 

  AutoSetup choices = new AutoSetup(); 

  private final Field2d m_field = new Field2d();

  @Override
  public void robotInit() {
    SmartDashboard.putData("Field", m_field);

    CameraServer.startAutomaticCapture();

    choices.setUpChooser();
    SmartDashboard.putData("Auto choices", choices.getChooser());

    Drive.getleftMotorA().configOpenloopRamp(0);
    Drive.getleftMotorB().configOpenloopRamp(0);
    Drive.getrightMotorA().configOpenloopRamp(0);
    Drive.getrightMotorB().configOpenloopRamp(0);
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("Time (seconds)", Timer.getFPGATimestamp());
  }

  double autoStart = 0;

  @Override
  public void autonomousInit() {
    choices.choosenSelection();

    autoStart = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    if (choices.getSelectedOption().equals(AutoSetup.kNothingAuto)) {
      Elv.setElvMotor(0);
      Drive.setDriveMotors(0,0);
      Claw.setClawMotor(0);
    }

    double timeOfAuto = Timer.getFPGATimestamp() - autoStart;
    if (timeOfAuto < 1) {//2 sec go forward
      Claw.setClawMotor(0.5);
      Drive.getright().set(-0.2);
      Drive.getleft().set(0.2);
      //Commands.setPulleyMotor(-0.001);
    } else if (timeOfAuto < 2.5) {//1 sec, turn off motors and lift ele
      Drive.getright().set(0);
      Drive.getleft().set(0);
      //Commands.setPulleyMotor(0.005);
      Elv.setElvMotor(0.4);
    } else if (timeOfAuto < 4.5) {//0.5 sec, drop claw, turn of Elv
      Elv.setElvMotor(0.1);
      Claw.setClawMotor(-0.3); 
    } else if (timeOfAuto < 5.5) { 
      Pulley.setPulleyMotor(0.2);
      Elv.setElvMotor(0.05);
    } else if (timeOfAuto < 6.5) { 
      Pulley.setPulleyMotor(-0.2);
    } else {
      Pulley.setPulleyMotor(0);
      Elv.setElvMotor(0);
    }
    Drive.getRobotDrive().feed();

    SmartDashboard.putNumber("Time Auto is using begining(seconds)", autoStart);
    SmartDashboard.putNumber("Time Auto is using difference (seconds)", timeOfAuto);
  }

  @Override
  public void teleopInit() {
    m_Timer.start(); 
  }


  //The testing variables that was created for testing second controller, unimportant
  double YWasPressTime = 0; 
  boolean YWasPress = false; 
  double XWasPressTime = 0; 
  boolean XWasPress = false;   
  double BWasPressTime = 0; 
  boolean BWasPress = false; 
  double AWasPressTime = 0; 
  boolean AWasPress = false; 

  @Override
  public void teleopPeriodic() {
    //For the drivetrain
    Drive.setDriveMotors(Xbox.getRightX(), Xbox.getLeftY());

    //For Elv
    if (Controllers.povUPs()){
      Elv.UpElv(); 
    } else if (Controllers.povDOWNs()) {
      Elv.DownElv(); 
    } 
    Elv.periodic();
    Elv.setElvMotor(Elv.getES());

    //For the claw
    if (Xbox.getRightBumper()) {
      Claw.closeClaw(); 
    } else if (Xbox.getLeftBumper()) { //tighen and then stop too much
      Claw.openClaw();  
    } 
    Claw.periodic();
    Claw.setClawMotor(Claw.getCS());

    //For the Pulley
    if (Xbox.getXButton()) {
      Pulley.slowUp(); 
    } else if (Xbox.getYButton()) {
      Pulley.fastUp();
    } else if (Xbox.getAButton()) {
      Pulley.slowDown();
    } else if (Xbox.getBButton()) {
      Pulley.fastDown();
    }
    Pulley.periodic();
    Pulley.setPulleyMotor(Pulley.getPS());


    SmartDashboard.putNumber("m_Timer, how it works", m_Timer.get());


    //This was the testing section, using a second controller to test speeds or plan out auto mode.
    if (YWasPress && (Timer.getFPGATimestamp() - YWasPressTime > 2)) {
      Pulley.setPS(0.1);
      YWasPress = false; 
    } else if (XWasPress && (Timer.getFPGATimestamp() - XWasPressTime > 2)) {
      Pulley.setPS(0);
      XWasPress = false;
    } else if (BWasPress && (Timer.getFPGATimestamp() - BWasPressTime > 2)) {
      Elv.setES(0.1);
      BWasPress = false; 
    } else if (AWasPress && (Timer.getFPGATimestamp() - AWasPressTime > 1)) {
      Elv.setES(0.1);
      AWasPress = false; 
    }

    if (Xbox1.getYButtonPressed()) {
      YWasPressTime = Timer.getFPGATimestamp();
      Pulley.setPS(0.5);
      YWasPress = true; 
    } else if (Xbox1.getXButtonPressed()) {
      XWasPressTime = Timer.getFPGATimestamp();
      Pulley.setPS(-0.2);
      XWasPress = true; 
    } else if (Xbox1.getBButtonPressed()) {
      BWasPressTime = Timer.getFPGATimestamp();
      Elv.setES(0.5);
      BWasPress = true; 
    } else if (Xbox1.getAButtonPressed()) {
      AWasPressTime = Timer.getFPGATimestamp();
      Elv.setES(-0.2);
      AWasPress = true;
    } 
  }

  @Override
  public void disabledInit() {
    Elv.getElv1().setNeutralMode(NeutralMode.Coast);
    Elv.getElv2().setNeutralMode(NeutralMode.Coast);
    Pulley.getPulley().setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

}
