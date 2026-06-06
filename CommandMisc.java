package com.csus.csc133;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

public class CommandMisc extends Command{
	private GameModel gm;
	
	public CommandMisc(String command, GameModel gm) {
		super(command);
		this.gm = gm; 
	}
	
	@Override
	//command handling
	public void actionPerformed(ActionEvent evt) {
		//Each command class should handle multiple command in the same group.	
		//player commands
		switch(getCommandName()) {
			case "Next Frame":
				// gm.eff();
				break; 
				
			case "Student": 
				TextField input = new TextField("", "Enter student type (0-9)", 2, TextArea.NUMERIC);
				Command ok = new Command("OK");

				if (Dialog.show("Select Student", input, ok) != null) {
					try {
						int selected = Integer.parseInt(input.getText());
						if (selected >= 0 && selected <= 9) {
							gm.fo(selected);
						} else {
							gm.setGamemessage("Please enter a number between 0 and 9.");
						}
					} catch (NumberFormatException e) {
						gm.setGamemessage("Invalid input. Please enter a number.");
					}
				}
				break; 
			
			case "Pause":
				SacRun sr = (SacRun) Display.getInstance().getCurrent();
				
			    if (!gm.isPause()) {
			        // to pause game
			        sr.setPauseTime(System.currentTimeMillis()); //to get rid of behind the scenes movement 
			        gm.setPause(true);
			        sr.setPauseLabel("Play");
			        gm.setGamemessage("Game Paused");
			    } else {
			        // to resume game
			        long now = System.currentTimeMillis();
			        long pausedDuration = now - sr.getPauseTime();
			        sr.setLastUpdateTime(sr.getLastUpdateTime() + pausedDuration); // <<== THE IMPORTANT LINE
			        gm.setPause(false);
			        sr.setPauseLabel("Pause");
			        gm.setGamemessage("Game Resumed");
			    }
				break;
				
			case "Change Position":
				//user can select an object
				//selected object can be moved by clicking anywhere on the map and object will move there
				if (gm.isPause()) {
			        gm.setChangePositionMode(true);
			        gm.setGamemessage("Click on the map to move the selected object.");
			    }
				break; 
				
		}
	}
}