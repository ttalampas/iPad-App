package com.csus.csc133;

public class StudentAngry extends Student {
	public StudentAngry() {
		//for parent methods
		super();
		//set talk level to x2 of default
		setTalkativeLevel(getDEFAULT_TALKATIVELEVEL() * 2); 
	}
	
	//override toString method for output purposes
	public String toString() {
		return super.toString() + ", I am angry!"; 
	}
	
	
}
