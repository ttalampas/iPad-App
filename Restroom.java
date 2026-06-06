package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Restroom extends Facility {
	public Restroom() {
		super(); 
		setSize(90); 
	}
	
	public void handleCollide(Student s) {
		//clear water intake if they collide with restroom
		s.useRestroom(); 
		//to ensure collide works
		System.out.println("Student collided with Restroom."); 
	}
	
	//method to display info
	public String toString() {
		return ", Position: (" + getTranslateForm().getTranslateX() + " , " + getTranslateForm().getTranslateY() + ")";
	}
	
	//method to draw
	public void draw(Graphics g) {
		int halfsize = getSize() / 2; 
	    g.setColor(ColorUtil.GREEN);
	    g.fillRect((int) (getTranslateForm().getTranslateX() - halfsize), (int) (getTranslateForm().getTranslateY() - halfsize), getSize(), getSize());
	    drawSelected(g);
	}
	
}