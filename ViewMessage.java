package com.csus.csc133;

import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import java.util.List;
import java.util.Observable; 
import java.util.Observer;

public class ViewMessage extends Container implements Observer {
	private Label message; 
	
	public ViewMessage() {
		//setting the layout 
		this.setLayout(new BorderLayout()); 
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createLineBorder(2,0x000000));
		getAllStyles().setBgColor(0xEEEEEE); // Light gray
		getAllStyles().setBgTransparency(255);
		//creating the message label
		message = new Label("Game Start");
		//adding the message
		this.add(BorderLayout.CENTER, message);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		if(observable instanceof GameModel) {
			GameModel gm = (GameModel) observable;
			message.setText(gm.getMessage());
		}
	}
	
}