package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class WaterDispenser extends Facility {
	
	public WaterDispenser() {
		super(); 
		setSize(40); 
	}
	
	public void handleCollide(Student s) {
		//drink water if they collide with Water Dispenser
		s.drinkWater();
		//to ensure collide works
		System.out.println("Student collided with Water Dispenser."); 
	}
	
	//method to display info
	public String toString() {
		return ", Position: (" + getTranslateForm().getTranslateX() + " , " + getTranslateForm().getTranslateY() + ")";
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		int halfSize = getSize() / 2;
		g.setColor(ColorUtil.BLUE);
		g.fillArc((int)(getTranslateForm().getTranslateX() - halfSize), (int)(getTranslateForm().getTranslateY() - halfSize), getSize(), getSize(), 0, 360);
		drawSelected(g);
	}
}
