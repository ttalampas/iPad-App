package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class LectureHall extends Facility {
	private String name; 
	private Lecture current; 
	
	public LectureHall(String name, Lecture lecture) {
		super();
		setName(name); 
		current = lecture; 
		setSize(90); 
	}
	
	public void handleCollide(Student s) {
		if(s instanceof StudentPlayer) {
			current.setTimeleft(0);
		}
		System.out.println("Student collided with Lecture Hall."); 
		setName("No class now"); 
	}
	
	//method to display output
	public String toString() {
		if(current.getTimeleft() <= 0) {
			//commented out for viewstatus to look better not sure how to split up the 2 outputs yet 
			return " " + getName() + ",- Position: (" + getTranslateForm().getTranslateX() + " , " + getTranslateForm().getTranslateY() + ")" +
				", Remaining Lecture Time: NULL"; 
//			return "No Class Now"; 
		}
		else {
			//commented out for viewstatus to look better not sure how to split up the 2 outputs yet
			return " " + getName() + ", Position: (" + getTranslateForm().getTranslateX() + " , " + getTranslateForm().getTranslateY() + ")" +
				", Remaining Lecture Time: " + current.getTimeleft(); 
//			return getName(); 
		}
	}
	
	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void draw(Graphics g) {
		int x = (int) getTranslateForm().getTranslateX();
		int y = (int) getTranslateForm().getTranslateY();
		int halfsize = getSize() / 2; 
		// Draw blue square (lecture hall)
	    g.setColor(ColorUtil.BLUE);
	    g.fillRect(x - halfsize, y - halfsize, getSize(), getSize());
	    //text
	    g.setColor(ColorUtil.BLACK);
	    g.drawString(name, x - halfsize, y - halfsize - 5 - g.getFont().getHeight());	   
	    
	    drawSelected(g);
		
	}

}
