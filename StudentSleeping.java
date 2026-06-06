package com.csus.csc133;

public class StudentSleeping extends Student {
	//cant talk to sleeping student 
	public StudentSleeping() {
		//for parent methods
		super();
		//set sweat rate to 0
		setSweatingRate(0); 
		//sets x and y so it doesnt move 
		double newX = getTranslateForm().getTranslateX();
		double newY = getTranslateForm().getTranslateY();
		getTranslateForm().setTranslation((float)newX, (float) newY); 
	}
	
	//override toString method for output purposes
	public String toString() {
		return super.toString() + ", zzzZZZ!"; 
	}
	
}