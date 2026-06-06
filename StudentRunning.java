package com.csus.csc133;

public class StudentRunning extends Student { 
	public StudentRunning() {
		//for parent methods
		super();
		//set sweat rate to x2 of default 
		setSweatingRate(getSweatingRate() * 2); 
	}
	
	public String toString() {
		return super.toString() + ", Running"; 
	}
	
}