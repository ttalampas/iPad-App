package com.csus.csc133;


public class StudentBiking extends Student {
	public StudentBiking() {
		//for parent methods
		super();
		//set speed to x3 of default
		setSpeed(getDEFAULT_SPEED() * 3); 
		//set sweat to x2 of default 
		setSweatingRate(getSweatingRate() * 2); 
	}
	
	public String toString() {
		return super.toString() + ", Biking"; 
	}
}

