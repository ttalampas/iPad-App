package com.csus.csc133;

public class StudentNonstop extends Student {
	public StudentNonstop() {
		//for parent methods
		super();
		//time remain cannot be changed for this student
		setTimeRemain(0);
	}
}