package com.csus.csc133;

import java.util.Random;
import java.util.Vector;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public abstract class GameObject {  
	
	//variables for positions
	private Transform translateForm = Transform.makeIdentity();
	private Transform rotateForm = Transform.makeIdentity(); 
	
	//variables for height and width
	private double height;
	private double width; 
	
	//variables for color and size
	private int size; 
	private int color = ColorUtil.rgb(255, 0, 0); //default red
	
	//boolean for if an object is selected or not
	boolean selected = false;
	
	//vector for collisions
	private Vector<GameObject> currentlyColliding = new Vector<>();

	public GameObject() {
		//set positions using random
		setRandomPosition(width, height); 
	}
	
	//set positions using random
//	public void setRandomPosition(double maxWidth, double maxHeight) {
//		Random rand = new Random();
//	    int borderBuffer = 2;
//	    int halfSize = size / 2;
//
//	    // Compute safe range for center position
//	    double minX = borderBuffer + halfSize;
//	    //double maxX = maxWidth - borderBuffer - halfSize;
//	    double maxX = 1383;
//
//	    double minY = borderBuffer + halfSize;
//	   // double maxY = maxHeight - borderBuffer - halfSize;
//	    double maxY = 1349;
//
//	    // Set centered position safely
//	    x = minX + rand.nextDouble() * (maxX - minX);
//	    y = minY + rand.nextDouble() * (maxY - minY);
//	}
	
	public void setRandomPosition(double width, double height) {
        Random rand = new Random();
//          this.width = width;
//          this.height = height;
//          //calculating x and y for translation
//          double newX = (float) (rand.nextDouble() * this.width); 
//          double newY = (float) (rand.nextDouble() * this.height); 
        int borderBuffer = 2;
        int halfSize = getSize() / 2;
        double minX = borderBuffer + halfSize;
        double maxX = width - borderBuffer - halfSize;
        double minY = borderBuffer + halfSize;
        double maxY = height - borderBuffer - halfSize;

        double newX = minX + rand.nextDouble() * (maxX - minX);
        double newY = minY + rand.nextDouble() * (maxY - minY);

        translateForm.setTranslation((float)newX, (float)newY);
    }
	
	
	//handle collides 
	public abstract void handleCollide(Student s);
	
	//handles drawing
	public abstract void draw(Graphics g); 

	//for mouse input selection
    public boolean contains(double px, double py) {
        int halfSize = size / 2;
        return (px >= getTranslateForm().getTranslateX() - halfSize && px <= getTranslateForm().getTranslateX() + halfSize &&
                py >= getTranslateForm().getTranslateY() - halfSize && py <= getTranslateForm().getTranslateY() + halfSize);
    }
    
    //for drawing the highlight box
    public void drawAB(Graphics g) {
    	g.setColor(ColorUtil.rgb(255, 0, 0));
    	int dx = (int) (getTranslateForm().getTranslateX() - getSize() / 2); 
    	int dy = (int) (getTranslateForm().getTranslateY() - getSize() / 2); 
    	g.drawRect(dx, dy, getSize(), getSize()); 
    }
    
    public void drawSelected(Graphics g) {
    	if(selected) {
    		drawAB(g); 
    	}
    }
    
    //handling collides
    public boolean collidesWith(GameObject other) { //used for colliding with other students
        int halfSizeThis = this.size / 2;
        int halfSizeOther = other.size / 2;

        double thisLeft = this.getTranslateForm().getTranslateX() - halfSizeThis;
        double thisRight = this.getTranslateForm().getTranslateX() + halfSizeThis;
        double thisTop = this.getTranslateForm().getTranslateY() - halfSizeThis;
        double thisBottom = this.getTranslateForm().getTranslateY() + halfSizeThis;

        double otherLeft = other.getTranslateForm().getTranslateX() - halfSizeOther;
        double otherRight = other.getTranslateForm().getTranslateX() + halfSizeOther;
        double otherTop = other.getTranslateForm().getTranslateY() - halfSizeOther;
        double otherBottom = other.getTranslateForm().getTranslateY() + halfSizeOther;

        return !(thisRight < otherLeft || thisLeft > otherRight || thisBottom < otherTop || thisTop > otherBottom);
    }

    public Vector <GameObject> getCollisions(){
    	return currentlyColliding;
    }
    
	//getters and setters
//	public double getX() {
//		return x;
//	}

	public Transform getTranslateForm() {
		return translateForm;
	}

	public void setTranslateForm(Transform translateForm) {
		this.translateForm = translateForm;
	}

	public Vector<GameObject> getCurrentlyColliding() {
		return currentlyColliding;
	}

	public void setCurrentlyColliding(Vector<GameObject> currentlyColliding) {
		this.currentlyColliding = currentlyColliding;
	}

//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	} 
	
	public boolean getSelected() {
        return selected;
    }
	
	public void setSelected(boolean s) {
        selected = s;
    }

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public Transform getRotateForm() {
		return rotateForm;
	}

	public void setRotateForm(Transform rotateForm) {
		this.rotateForm = rotateForm;
	}
	
	
}

