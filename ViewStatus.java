package com.csus.csc133;

import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import java.util.List;
import java.util.Observable; 
import java.util.Observer; 

public class ViewStatus extends Container implements Observer{
	private Label lecHall, lh;
	private Label lecTime, lt;
	private Label gameTime, gt;
	private Label absence, ab;
	private Label hydration, hy;
	private Label waterIntake, wi;
	private Label timeRemain, tr;
	
	public ViewStatus() {
		//set the layout
		this.setLayout(new BorderLayout());
		Container status = new Container(BoxLayout.y());
		
		//creating labels
		lecHall = new Label("Lecture Hall: "); 
		lh = new Label(""); 
		lecTime = new Label("Lecture Time Remain: "); 
		lt = new Label(""); 
		gameTime = new Label("Game Time: "); 
		gt = new Label(""); 
		absence = new Label("Absences: "); 
		ab = new Label(""); 
		hydration = new Label("Hydration: "); 
		hy = new Label(""); 
		waterIntake = new Label("Water Intake: "); 
		wi = new Label(""); 
		timeRemain = new Label("Hold: "); 
		tr = new Label(""); 
		
		//adding labels, need 2 to print data on separate line
		status.addAll(lecHall, lh, lecTime, lt, gameTime, gt, absence, ab, hydration, hy, waterIntake, wi, timeRemain, tr); 
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBorder(Border.createLineBorder(1, 0x888888));
		getAllStyles().setBgColor(0xEEEEEE); // Light gray
		getAllStyles().setBgTransparency(255);
		
		this.add(BorderLayout.EAST, status);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		if(observable instanceof GameModel) {
			GameModel gm = (GameModel) observable; 
			//set texts to get methods that update in gamemodel
			lecHall.setText("Lecture Hall: ");
			lh.setText(gm.getCurrentLec().getName());
			lecTime.setText("Lecture Time Remain: ");
			lt.setText("" + gm.getLecture().getTimeleft());
			gameTime.setText("Game Time: ");
			gt.setText("" + gm.getGametime());
			absence.setText("Absences: ");
			ab.setText("" + gm.getStudentPlayer().getAbsenceTime());
			hydration.setText("Hydration: ");
			hy.setText("" + (int)gm.getStudentPlayer().getHydration());
			waterIntake.setText("Water Intake: ");
			wi.setText("" + (int)gm.getStudentPlayer().getWaterIntake());
			timeRemain.setText("Hold: ");
			tr.setText(""+ (int)gm.getStudentPlayer().getTimeRemain());
			//to instantly show the values
			this.revalidate();
		}
	}
	
}