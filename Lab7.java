/****************************************************************/    
/*   Program Name:     Lab 7                                    */    
/*																*/    
/*   Student Name:		Quang Nguyen							*/    
/*   Semester:			Fall 2014								*/
/*   Class-Section: 	CoSc 10403-055							*/    
/*   Instructor:		James Comer				                */    
/*                                                              */    
/*   Program Overview:                                          */    
/*		_ This program is using for calculating GPA, storing,	*/
/* 		displaying, and deleting students' data					*/
/*																*/
/*                                                              */    
/*   Input:                                                     */    
/*		_ grades and credit hours						 		*/
/*																*/    
/*                                                              */    
/*   Output:                                                    */    
/*		_ GPA													*/
/*                                                              */
/*   Program Abilities:											*/
/*		This program 											*/
/* 		_is able to change interfaces interactively				*/
/*		_can calculate GPA from inputs of grades and credit    	*/
/*		hours								 				    */
/*		_stores students' name, GPA, and ID						*/
/*		_displays, arranges, and delete data					*/
/*      														*/    
/*   Significant codes				                            */    
/*		_Class constructors										*/
/*		_Build an instant of a class in another class			*/
/*		_link two classes together to make program interactive	*/
/*																*/
/*																*/
/*	 Program flaws:												*/
/*		_There are many insignificant codes, especially			*/
/*		in DisplayControl class. The mechanism is not elegant	*/
/*		and sophisticated. Those mechanism might not work		*/
/*		in other cases											*/
/* 															    */
/****************************************************************/

import java.awt.event.*;

import javax.swing.event.*;

import java.awt.*;

import javax.swing.*;

public class Lab7 extends JApplet {
// declarations
	StartupView startupGUI;
	EnterView enterGUI;
	DisplayView displayGUI;
	ExitView exitGUI;
	Model model;
	JPanel gui = new JPanel ();
	
	public void init() {
		setLayout(new FlowLayout());
		model = new Model();
		add(gui);
		createStartupView();
	}
	
	public void createStartupView() {
		startupGUI = new StartupView(this);
		gui.removeAll();
		gui.add(startupGUI);
		validate();
	}
		
	public void createEnterView() {
		enterGUI = new EnterView(this, model);
		gui.removeAll();
		gui.add(enterGUI);
		validate();
	}
	
	public void createDisplayView() {
		displayGUI = new DisplayView (this, model);
		gui.removeAll();
		gui.add(displayGUI);
		validate();
	}
	
	public void createExitView() {
		exitGUI = new ExitView(this);
		gui.removeAll();
		gui.add(exitGUI);
		validate();
	}
}
