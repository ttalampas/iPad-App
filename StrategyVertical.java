package com.csus.csc133;

public class StrategyVertical implements Strategy {
	@Override
	public void apply(StudentWithStrategy student) {
		student.setHead(90);
	}
	
}