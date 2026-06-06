package com.csus.csc133;

public class Lecture {
	private int timeleft;

	public Lecture(int time) {
		setTimeleft(time); 
	}
	
	public void lessLec() {
		if (getTimeleft() > 0) {
			setTimeleft(getTimeleft() - 1); 
		}
	}

	public int getTimeleft() {
		return timeleft;
	}

	public void setTimeleft(int timeleft) {
		this.timeleft = timeleft;
	}
}