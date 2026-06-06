package com.csus.csc133;

public class WorldBound {
	private int lbound, rbound, tbound, bbound; 
	
	public void setBound(int centerwidth, int widths, int centerHeight, int heights) {
		this.setLbound(widths);
		this.setRbound(centerwidth + widths);
		this.setTbound(0);
		this.setBbound(centerHeight - heights); 
	}

	public int getLbound() {
		return lbound;
	}

	public void setLbound(int lbound) {
		this.lbound = lbound;
	}

	public int getRbound() {
		return rbound;
	}

	public void setRbound(int rbound) {
		this.rbound = rbound;
	}

	public int getTbound() {
		return tbound;
	}

	public void setTbound(int tbound) {
		this.tbound = tbound;
	}

	public int getBbound() {
		return bbound;
	}

	public void setBbound(int bbound) {
		this.bbound = bbound;
	}
	
}