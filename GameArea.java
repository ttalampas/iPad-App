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

public class GameArea extends Container {
	private GameModel gm; 
	private boolean worldSizeSet = false;
	
	public GameArea(GameModel gm) {
		this.gm = gm; 
		this.setLayout(new BorderLayout());
		this.getAllStyles().setBorder(Border.createLineBorder(2,0xFF0000));
		this.getAllStyles().setBgTransparency(255);
	}
	
	public void laidOut() {
	    super.laidOut();
	    if (!worldSizeSet) {
	    	//hard coded cause im pissed off
	        int w = 1383;//1383
	        int h = 1349;//1349
	        gm.setWidth(w);
	        gm.setHeight(h);
	        gm.repositionObjects(); // Only ONCE
	        worldSizeSet = true;
	    }
	}
	
	
}