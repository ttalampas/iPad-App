package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;

public class StudentPlayer extends Student {
	
	//singleton
	private static StudentPlayer instance = null;
	
	private StudentPlayer() {
		//for parent methods
		super(); 
	}
	
	//to access student
	 public static  StudentPlayer getInstance() {
	        if (instance == null) {
	            instance = new StudentPlayer();
	        }
	        return instance;
	    }
	
	public void startMove() {
		setSpeed(getDEFAULT_SPEED());
		System.out.println("Student started moving."); 
	}
	
	public void stopMove() {
		setSpeed(0.0);
		System.out.println("Student stopped moving."); 
	}
	
	public void left() {
		setHead(getHead() - 5); 
		System.out.println("Student turned left.");
	}
	
	public void right() {
		setHead(getHead() + 5); 
		System.out.println("Student turned right.");
	}
	
	//method to handle output
	public String toString() {
		return super.toString() + ", Absence Time:" + getAbsenceTime() + ", Water Intake: "
				+ getWaterIntake(); 
	}
	
	public void colorchange() {
		if(getTimeRemain() > 0) {
			setColor(ColorUtil.rgb(255, 192, 203)); 
		}
		else if(getTimeRemain() <= 0) {
			setColor(ColorUtil.rgb(255, 0, 0)); 
		}
	}
	
	public void draw(Graphics g) {
		 //draw as triangle
	    int centerX = (int) getTranslateForm().getTranslateX();
	    int centerY = (int) getTranslateForm().getTranslateY();
	    int halfSize = getSize() / 2;
	    
	    // Draw the triangle
	    int[] xPoints = {
	        centerX,                   // Top vertex
	        centerX - halfSize,       // Bottom left
	        centerX + halfSize        // Bottom right
	    };
	    int[] yPoints = {
	        centerY + halfSize,       // Top vertex
	        centerY - halfSize,       // Bottom left
	        centerY - halfSize        // Bottom right
	    };

	    g.setColor(getColor());
	    g.fillPolygon(xPoints, yPoints, 3);
	    
	    drawSelected(g);
	}
	
}