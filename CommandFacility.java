package com.csus.csc133;
	
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandFacility extends Command {
	//access facility commands
	private GameModel s; 
	
	public CommandFacility(String command, GameModel s) {
		super(command); //store command name
		this.s = s; //store refer of some object for access later 
	}
	
	@Override
	//command handling
	public void actionPerformed(ActionEvent evt) {
		//Each command class should handle multiple command in the same group.	
		//player commands
		switch(getCommandName()) {
			case "Lecture Hall":
				s.collidelecturehall();
				break;
			case "Restroom": 
				s.colliderestroom();
				break;
			case "Water Dispenser":
				s.collidewaterdispenser();
				break;
		}
	}
}