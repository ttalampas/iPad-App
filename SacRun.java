package com.csus.csc133;

import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;

public class SacRun extends Form implements Runnable {
	
	private GameModel gm;
	
	//for buttons
	Container buttons = new Container(BoxLayout.y());
	WorldBound WB = new WorldBound(); 
	//vector to store commands
	//use vectors to apply the same command to multiple buttons
	private CommandStudent[] studentCommands = new CommandStudent[4];
	private CommandFacility[] facilityCommands = new CommandFacility[4];
	//commands for other stuff? (next frame, strategies)
	private CommandMisc[] miscCommands = new CommandMisc[4];
	private StrategyChange[] stratCommands = new StrategyChange[3]; 
	
	//variables to store observers
	ViewStatus viewStatus = new ViewStatus(); 
	ViewMessage viewMessage = new ViewMessage(); 
	Container north = new Container(BoxLayout.x());
	ViewMap viewMap; 
	GameArea gameArea; 

	//variable to store elapsed time
	private long lastUpdateTime; 
	private long pauseTime = 0; 
	
	//variable to store keyboard inputs
	keyboardInput ki = new keyboardInput(this, studentCommands); 
	
	//timer variable
	UITimer time = new UITimer(this); //new UITimer variable

	//variable for pause button
	private Button pause; 
	
	public SacRun(){
		gm = new GameModel();
			
		gm.init();
		
		//initialize viewmap after gm is initialized
		viewMap = new ViewMap(gm);
		gameArea = new GameArea(gm);  
		
		A2(); 
		
		setLastUpdateTime(System.currentTimeMillis()); 
		
		//20ms intervals generates 50 frames per second 
		time.schedule(20, true, this); //call run every 20 milliseconds, change later on
	}
	
	//main UI
	private void create() {
		setLayout(new BorderLayout()); 
		//game status
		add(BorderLayout.EAST, viewStatus);
		//game messages
		add(BorderLayout.SOUTH, viewMessage);
		//center box
		add(BorderLayout.NORTH, north);
		gameArea.add(BorderLayout.CENTER, viewMap); 
		add(BorderLayout.CENTER, gameArea);
		
		getAllStyles().setBgColor(0xEEEEEE); // Light gray
		getAllStyles().setBgTransparency(255);
	}
	
	//function to create toolbar in north sector
	private void toolbar() {
		Toolbar myToolbar= new Toolbar();
		setToolbar(myToolbar);
		Command line = new Command("");
				
		//add commands for these buttons!!!
		myToolbar.addCommandToSideMenu(stratCommands[0]);
		//dialog and confirm buttons for about 
		myToolbar.addCommandToSideMenu("About", null, e -> {
	           Dialog.show("About", "A2\nTim Talampas\nSummer 2025", "Confirm", null);});
		myToolbar.addCommandToLeftBar(line);
		
		//exit command
		myToolbar.addCommandToSideMenu("Exit", null, e -> {
	            Command exit = Dialog.show("Exit", "Are you sure you want to exit?", 
	               new Command("Yes"), new Command("No"));
	           if (exit.getCommandName().equals("Yes")) {
	               com.codename1.ui.CN.exitApplication();
	           }
	       });
		
		//commands for toolbar 
		myToolbar.addCommandToRightBar(facilityCommands[0]);
		//same about function as earlier 
		myToolbar.addCommandToRightBar("About", null, e -> {
	           Dialog.show("About", "A2\nTim Talampas\nSummer 2025", "Confirm", null);});
		
		myToolbar.getAllStyles().setBgColor(0xFFFFFF); // white
		myToolbar.getAllStyles().setBgTransparency(255);
	}
	
	//create buttons
	private void createButton() {
			//setting the layout 
			this.getAllStyles().setBgTransparency(255);
			
			//creating the buttons
			myButton b1 = new myButton("Move"); 
			myButton b2 = new myButton("Stop");
			myButton b3 = new myButton("Turn Left");
			myButton b4 = new myButton("Turn Right");
			myButton b5 = new myButton("Change Strategy");
			//delete all simulated collision buttons
//			myButton b6 = new myButton("Lecture Hall");
//			myButton b7 = new myButton("Restroom");
//			myButton b8 = new myButton("Water Dispenser");
//			myButton b9 = new myButton("Student");
//			myButton b10 = new myButton("Next Frame");
			myButton b11 = new myButton("Pause");
			pause = b11; 
			myButton b12 = new myButton("Change Position");
			
			//adding buttons
			buttons.addAll(b1, b2, b3, b4, b5, b11, b12);
			
			//set commands to button
			b1.setCommand(studentCommands[0]); 
			b2.setCommand(studentCommands[1]); 
			b3.setCommand(studentCommands[2]); 
			b4.setCommand(studentCommands[3]); 
			b5.setCommand(stratCommands[0]);
//			b6.setCommand(facilityCommands[0]);
//			b7.setCommand(facilityCommands[1]);
//			b8.setCommand(facilityCommands[2]);
//			b9.setCommand(miscCommands[1]);
//			b10.setCommand(miscCommands[0]); 
			b11.setCommand(miscCommands[2]);
			b12.setCommand(miscCommands[3]);
			
			this.add(BorderLayout.WEST, buttons);
	}
	
	//create commands
	private void command() {
		//setup list of commands
		studentCommands[0] = new CommandStudent("Move"	, gm);
		studentCommands[1] = new CommandStudent("Stop"		, gm);
		studentCommands[2] = new CommandStudent("Turn Left"	, gm);
		studentCommands[3] = new CommandStudent("Turn Right"	, gm);
		facilityCommands[0] = new CommandFacility("Lecture Hall"	, gm);
		facilityCommands[1] = new CommandFacility("Restroom"	, gm);
		facilityCommands[2] = new CommandFacility("Water Dispenser"	, gm);
		miscCommands[0] = new CommandMisc("Next Frame"	, gm);
		miscCommands[1] = new CommandMisc("Student"	, gm);
		miscCommands[2] = new CommandMisc("Pause", gm); 
		miscCommands[3] = new CommandMisc("Change Position", gm); 
		stratCommands[0] = new StrategyChange(gm); 
	}
	
	//method to add observers 
	private void observe() {
		gm.addObserver(viewStatus);
		gm.addObserver(viewMap);
		gm.addObserver(viewMessage);
	}
	
	//method to change frame by time instead of by clicking the button
	public void run() {
		if(!gm.isPause()) {
		// TODO Auto-generated method stub
			long currentTime = System.currentTimeMillis(); 
			double elapsedTime = (currentTime - lastUpdateTime) / 1000.0; 
			System.out.println("RUN: " + System.currentTimeMillis() + ", elapsed=" + elapsedTime);
			setLastUpdateTime(currentTime); 
			gm.eff(elapsedTime);
			repaint(); 
		}
	}
	
	//method for changing label of button
	public void setPauseLabel(String text) {
	    pause.setText(text);
	}

	private void A2() {
		create(); 
		command(); 
		toolbar(); 
		createButton();
		observe(); 
		ki.keys();
		show(); 
		
		WB.setBound(viewMap.getWidth(), buttons.getWidth(), viewMap.getHeight(), viewMessage.getHeight());
		gm.setWorldBounds(WB);
		
	}

	//getters and setters
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public GameModel getGm() {
		return gm;
	}

	public void setGm(GameModel gm) {
		this.gm = gm;
	}

	public CommandStudent[] getStudentCommands() {
		return studentCommands;
	}

	public void setStudentCommands(CommandStudent[] studentCommands) {
		this.studentCommands = studentCommands;
	}

	public CommandFacility[] getFacilityCommands() {
		return facilityCommands;
	}

	public void setFacilityCommands(CommandFacility[] facilityCommands) {
		this.facilityCommands = facilityCommands;
	}

	public CommandMisc[] getMiscCommands() {
		return miscCommands;
	}

	public void setMiscCommands(CommandMisc[] miscCommands) {
		this.miscCommands = miscCommands;
	}

	public StrategyChange[] getStratCommands() {
		return stratCommands;
	}

	public void setStratCommands(StrategyChange[] stratCommands) {
		this.stratCommands = stratCommands;
	}

	public ViewStatus getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(ViewStatus viewStatus) {
		this.viewStatus = viewStatus;
	}

	public ViewMessage getViewMessage() {
		return viewMessage;
	}

	public void setViewMessage(ViewMessage viewMessage) {
		this.viewMessage = viewMessage;
	}

	public ViewMap getViewMap() {
		return viewMap;
	}

	public void setViewMap(ViewMap viewMap) {
		this.viewMap = viewMap;
	}

	public keyboardInput getKi() {
		return ki;
	}

	public void setKi(keyboardInput ki) {
		this.ki = ki;
	}

	public UITimer getTime() {
		return time;
	}

	public void setTime(UITimer time) {
		this.time = time;
	}
	
	public Button getPause() {
		return pause;
	}

	public void setPause(Button pause) {
		this.pause = pause;
	}

	public long getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
	
}
