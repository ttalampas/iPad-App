package com.csus.csc133;

public class StudentFriendly extends Student {
	public StudentFriendly() {
		//for parent methods
		super();
		//set talk level to 1/2 of default 
		setTalkativeLevel(getDEFAULT_TALKATIVELEVEL() / 2); 
	}
	
}