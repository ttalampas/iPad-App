package com.csus.csc133;

import java.util.Random; 

public class StrategyRandom implements Strategy {
	private Random rand = new Random(); 
	@Override
	public void apply(StudentWithStrategy student) {
		student.setHead(rand.nextDouble() * 360);
	}
	
}