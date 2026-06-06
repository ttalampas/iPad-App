package com.csus.csc133;

import java.util.Random;

//head needs to change every frame

public class StudentConfused extends Student {
	//set positions using random
	Random rand = new Random(); 
	private double r = rand.nextDouble(); 
	
	public StudentConfused() {
		//for parent methods
		super();
		this.setHead(this.getHead() + r);
	}
	
	//random head each time f is pressed
	public void move() {
		this.setHead(rand.nextDouble() * 360);
	}
}
