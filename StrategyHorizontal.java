package com.csus.csc133;

public class StrategyHorizontal implements Strategy {
	@Override
	public void apply(StudentWithStrategy student) {
		student.setHead(0);
	}
	
}