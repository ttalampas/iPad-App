package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

import java.util.Random; 

public abstract class Student extends GameObject implements IMoveable {
	//default values for student class
	private double DEFAULT_SPEED = 150; 
	private double DEFAULT_TALKATIVELEVEL = 2.0; 
	private double head = 0.0; 
	private double speed = DEFAULT_SPEED; 
	private double talkativeLevel = DEFAULT_TALKATIVELEVEL; 
	private double timeRemain = 0; 
	private double Hydration = 150; 
	private double waterIntake = 0; 
	private double sweatingRate = 2; 
	private double absenceTime = 0; 

	//for bounding
	private WorldBound bounds;
	
	//for collisions
	private double collisionCooldown = 0;
	 
	public Student() {
		super();
		studentsize();  
		setColor(ColorUtil.rgb(255, 0, 0));
		this.head = new Random().nextDouble() * 360; //changes direction of head for students moving
	}

	//student features 
	//drink water 
	public void drinkWater() { 
		//subtract current hydration from default value to get hydration change 
		waterIntake += 150 - Hydration; 
		Hydration = 150; 
	} 
	//restroom 
	public void useRestroom() { 
		waterIntake = 0; 
	}
	//lecturehall
	public void useLecHall() {
		System.out.println("Student collided with Lecture Hall."); 
	}
	
	//for student collisions 
	public void handleCollide(Student s) {
		 double maxTime = Math.max(talkativeLevel, s.talkativeLevel);
		 double cooldownTime = 5;

		 // Only start new "hold" if not already in a talking state
		 if (this.collisionCooldown <= 0 && s.collisionCooldown <= 0 && this.timeRemain <= 0 && s.timeRemain <= 0) {
		        this.timeRemain = maxTime;
		        s.timeRemain = maxTime;

		        this.collisionCooldown = cooldownTime;
		        s.collisionCooldown = cooldownTime;
		    }
	}
	
	//student moving 
	public void move(double elapsedtime, double width, double height) {
		if (collisionCooldown > 0) {
	        collisionCooldown -= elapsedtime;
	        if (collisionCooldown < 0) collisionCooldown = 0;
	    }

		int buffer = 2;
	    
	    colorchange(); 
	    
	    if (timeRemain > 0) {
	        timeRemain -= elapsedtime;
	        return;
	    }

	    double rad = Math.toRadians(90.0 - head);
	    double newX = getTranslateForm().getTranslateX() + Math.cos(rad) * speed * elapsedtime;
	    double newY = getTranslateForm().getTranslateY() + Math.sin(rad) * speed * elapsedtime;

	    int halfSize = getSize() / 2;

	    // Handle horizontal (left/right) bounds
	    if (newX - halfSize < buffer) {
	        newX = halfSize + buffer;
	        head = (head + 180) % 360;
	    } else if (newX + halfSize > width - buffer) {
	        newX = width - halfSize - buffer;
	        head = (head + 180) % 360;
	    }

	    // Handle vertical (top/bottom) bounds
	    if (newY - halfSize < buffer) {
	        newY = halfSize + buffer;
	        head = (head + 180) % 360;
	    } else if (newY + halfSize > height - buffer) {
	        newY = height - halfSize - buffer;
	        head = (head + 180) % 360;
	    }

	    
	    getTranslateForm().setTranslation((float)newX, (float) newY);
	    getRotateForm().setRotation((float) Math.toRadians(head), 0f, 0f);

	    Hydration -= sweatingRate * elapsedtime;
	}
		
	//method for changing color if student is talking
	public void colorchange() {
		if(getTimeRemain() > 0) {
			setColor(ColorUtil.rgb(255, 192, 203)); 
		}
		else if(getTimeRemain() <= 0) {
			setColor(ColorUtil.rgb(255, 0, 0)); 
		}
	}
	
	//method for setting sizes 
	public void studentsize() {
		Random rand = new Random(); 
		int min = 40;
		int max = 60; 
		int randnum = rand.nextInt(max - min + 1) + min; 
		setSize(randnum); 
	}
	
	//method for drawing student
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
	    g.drawPolygon(xPoints, yPoints, 3);
	    
	    drawSelected(g);
	}
		
	//method for outputting data
	public String toString() {
		String info = ", Position: (" + (int)getTranslateForm().getTranslateX() + ", " + (int)getTranslateForm().getTranslateY() + "), Head: " + 
				getHead() + ", Speed: " + getSpeed() + ", Hydration: " + getHydration() +
				", Talkative Level: " + getTalkativeLevel() + ", Time Remain: " + timeRemain;
		return info; 
	}
		
	//getters and setters for all variables 
	public double getDEFAULT_TALKATIVELEVEL() {
		return DEFAULT_TALKATIVELEVEL;
	}

	public void setDEFAULT_TALKATIVELEVEL(double dEFAULT_TALKATIVELEVEL) {
		DEFAULT_TALKATIVELEVEL = dEFAULT_TALKATIVELEVEL;
	}

	public double getDEFAULT_SPEED() {
		return DEFAULT_SPEED;
	}

	public void setDEFAULT_SPEED(double dEFAULT_SPEED) {
		DEFAULT_SPEED = dEFAULT_SPEED;
	}

	public double getSweatingRate() {
		return sweatingRate;
	}

	public void setSweatingRate(double sweatingRate) {
		this.sweatingRate = sweatingRate;
	}

	public double getHead() {
		return head;
	}

	public void setHead(double head) {
		this.head = head;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getTalkativeLevel() {
		return talkativeLevel;
	}

	public void setTalkativeLevel(double talkativeLevel) {
		this.talkativeLevel = talkativeLevel;
	}

	public double getTimeRemain() {
		return timeRemain;
	}

	public void setTimeRemain(double timeRemain) {
		this.timeRemain = timeRemain;
	}

	public double getHydration() {
		return Hydration;
	}

	public void setHydration(double hydration) {
		Hydration = hydration;
	}

	public double getWaterIntake() {
		return waterIntake;
	}

	public void setWaterIntake(double waterIntake) {
		this.waterIntake = waterIntake;
	}

	public double getAbsenceTime() {
		return absenceTime;
	}

	public void setAbsenceTime(double absenceTime) {
		this.absenceTime = absenceTime;
	}

	public WorldBound getBounds() {
		return bounds;
	}

	public void setWorldBounds(WorldBound bounds) {
		this.bounds = bounds;
	}
	
	
}
