package com.csus.csc133;

public class StudentCar extends Student {
	public StudentCar() {
		//for parent methods
		super();
		//set speed to x5 of default 
		setSpeed(getDEFAULT_SPEED() * 5); 
		//0 sweat rate
		setSweatingRate(getSweatingRate() * 0); 
		//function to always round head to 90 or 270 
		if(getHead() < 134) {
			setHead(90);
		}
		else if(getHead() >= 135) {
			setHead(270);
		}
	}
	
	public String toString() {
		return super.toString() + ", Driving"; 
	}
}


