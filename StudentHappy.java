package com.csus.csc133;

import java.util.Random;

public class StudentHappy extends Student {
	public StudentHappy() {
		//for parent methods
		super();
		//probability to set speed to x10
		Random rand = new Random();
		int r = rand.nextInt(101); 
		if(r < 10) {
			setSpeed(getDEFAULT_SPEED() * 10); 
		}
		//set speed to default after move?
		setSpeed(getDEFAULT_SPEED());
		
	}
	
	//override toString method for output purposes
	public String toString() {
		return super.toString() + ", I am happy!"; 
	}
	
}
