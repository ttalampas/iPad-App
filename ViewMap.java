package com.csus.csc133;

import java.util.List;
import java.util.Observable; 
import java.util.Observer; 
import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.csus.csc133.GameModel.GameObjectCollection;
import com.codename1.ui.Label;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Display;



public class ViewMap extends Container implements Observer{
	//to tell viewmap what to draw
	private GameModel gm; 
	
	private float worldX = 0;
	private float worldY = 0;
	private float worldWidth = 1383;   // world width
	private float worldHeight = 1349;  // world height
	
	//inputs variables
	private float lastPanX = -1; 
	private float lastPanY = -1;
	
	private float zoomFactor = 1.0f; 
	private float lastPinchDistance = -1;
	
	public ViewMap(GameModel gm) {
		this.gm = gm; 
		//Container border = new Container(BoxLayout.y());
		//setting style of container / border
		this.setLayout(new BorderLayout());
		//code to make the red border
		this.getAllStyles().setBorder(Border.createLineBorder(2,0xFF0000));
		this.getAllStyles().setBgTransparency(255);
		getAllStyles().setBgColor(0xEEEEEE); // Light gray
		getAllStyles().setBgTransparency(255);
		initializeObjectPositions(); 
		
		//for mouse input
		this.addPointerPressedListener(e -> {
		    int relX = e.getX() - getAbsoluteX();
		    int relY = e.getY() - getAbsoluteY();
		    pressed(relX, relY + 235);
		});
		
		//for zoom input
		
		
		//for scrolling input
		this.addPointerDraggedListener(e -> {
		    float curX = e.getX();
		    float curY = e.getY();
		    if(lastPanX >= 0 && lastPanY >= 0) {
		        float scale = Math.min(
		            (float)getWidth() / worldWidth,
		            (float)getHeight() / worldHeight
		        );
		        float dx = (lastPanX - curX) / scale;
		        float dy = (lastPanY - curY) / scale;
		        worldX += dx;
		        worldY += dy; 
		        repaint();
		    }
		    lastPanX = curX;
		    lastPanY = curY;
		});
		this.addPointerReleasedListener(e -> { lastPanX = lastPanY = -1; });
		
	}
	
	//method to set view map size
	public void laidOut() {
		super.laidOut();
	    if (gm.getWidth() == 0 || gm.getHeight() == 0) {
	        // Use the actual ViewMap dimensions directly
//	        int actualWidth = this.getWidth(); 
//	        int actualHeight = this.getHeight();
	        //reposition all objects
	        gm.repositionObjects();
	    }
	}

	//method to draw animation onto map 
	//make sure its all inside the viewmap
	public void paint(Graphics g) {
//		 super.paint(g);
//		    // Draw objects using their exact logical positions
//		    GameModel.GameObjectCollection.Iterator it = gm.getS().getIterator();
//		    while (it.hasNext()) {
//		        GameObject obj = it.getNext();
//		        obj.draw(g);
//		    }
		
		super.paint(g);

	    //Compute the VTM per frame
	    Transform vtm = computeVTM(getWidth(), getHeight());

	    //Save the old transform
	    Transform oldform = g.getTransform();

	    //Apply VTM
	    g.setTransform(vtm);

	    //Draw world boundary
	    g.setColor(0); // black
	    g.drawRect(0, 0, (int)worldWidth, (int)worldHeight);

	    //Draw all objects in world coordinates
	    GameModel.GameObjectCollection.Iterator it = gm.getS().getIterator();
	    while (it.hasNext()) {
	        GameObject obj = it.getNext();
	        obj.draw(g);
	    }

	    //Restore transform
	    g.setTransform(oldform);
	}
	
	public void initializeObjectPositions() {
	    if (gm.getWidth() > 0 && gm.getHeight() > 0) {
	        gm.repositionObjects();
	    }
	}
	
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		if(observable instanceof GameModel) {
			GameModel gm = (GameModel) observable;
			gm.outputInfo();
		}
		repaint(); 
	}
	
	//method to handle mouse input 
	public void pressed(int x, int y) {
		//everything is "right side up"
		Transform invVTM = computeVTM(getWidth(), getHeight());
	    try {
	        invVTM.invert();
	    } catch (Transform.NotInvertibleException e) {
	    	//for weird error i was getting
	        System.err.println("Could not invert VTM: " + e);
	        return;
	    }

	    float[] pts = {(float)x, (float)y};
	    invVTM.transformPoint(pts, pts);
	    float worldX = pts[0];
	    float worldY = pts[1];

	    // change position (must come before selection)
	    if (gm.isPause() && gm.isChangePositionMode()) {
	        GameObjectCollection.Iterator it = gm.getS().getIterator();
	        while (it.hasNext()) {
	            GameObject obj = it.getNext();
	            if (obj.getSelected()) {
	                obj.getTranslateForm().setTranslation(worldX, worldY);
	                gm.setChangePositionMode(false);
	                gm.setGamemessage("Moved " + obj.getClass().getSimpleName() + " to (" + (int)worldX + ", " + (int)worldY + ")");
	                repaint();
	                return;
	            }
	        }
	        gm.setGamemessage("No object selected to move.");
	        gm.setChangePositionMode(false);
	        return;
	    }

	    // default
	    GameObject selectedObj = null;
	    GameObjectCollection.Iterator it = gm.getS().getIterator();
	    while (it.hasNext()) {
	        GameObject obj = it.getNext();
	        if (obj.contains(worldX, worldY)) {
	            selectedObj = obj;
	            break;
	        }
	    }

	    // Reset all selections
	    GameObjectCollection.Iterator resetIt = gm.getS().getIterator();
	    while (resetIt.hasNext()) {
	        GameObject obj = resetIt.getNext();
	        obj.setSelected(obj == selectedObj);
	    }

	    gm.setMessage(selectedObj != null ? "Selected " + selectedObj.getClass().getSimpleName() : "Unselected all at (" + worldX + ", " + worldY + ")");
	    repaint();
    }
	
	//for zooming
	
	
	//to store transformations 
	public Transform computeVTM(int viewWidth, int viewHeight) {
	    Transform vtm = Transform.makeIdentity();

	    //Translate world origin to (0,0)
	    vtm.translate(-worldX, -worldY);
	    
	    float baseScale = Math.min(
	    	    (float)viewWidth / worldWidth,
	    	    (float)viewHeight / worldHeight
	    	);
	    
	    // for zooming
	    float scale = baseScale * zoomFactor;

	    
	    // for scrolling
	    vtm.scale(scale, -scale); // y flip

	    //Move origin to bottom-left corner of view (for "north is up")
	    vtm.translate(0, -worldHeight - 235); //235 offset 

	    return vtm;
	}

	public GameModel getGm() {
		return gm;
	}

	public void setGm(GameModel gm) {
		this.gm = gm;
	}

	public float getWorldX() {
		return worldX;
	}

	public void setWorldX(float worldX) {
		this.worldX = worldX;
	}

	public float getWorldY() {
		return worldY;
	}

	public void setWorldY(float worldY) {
		this.worldY = worldY;
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(float worldWidth) {
		this.worldWidth = worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(float worldHeight) {
		this.worldHeight = worldHeight;
	}
}