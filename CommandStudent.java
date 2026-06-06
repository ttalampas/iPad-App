package com.csus.csc133;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class CommandStudent extends Command {
	
	//to access student commands
	private GameModel form;
	
	public CommandStudent(String command, GameModel form) {
		super(command); //store command name
		this.form = form; //store refer of some object for access later 
	}
	
	@Override
	//command handling
	public void actionPerformed(ActionEvent evt) {
		//Each command class should handle multiple command in the same group.	
		//player commands
		switch(getCommandName()) {
			case "Move": 
				form.studentstartmove(); 
				break;
			case "Stop":
				form.studentstopmove();
				break;
			case "Turn Left": 
				form.turnleft();
				break;
			case "Turn Right": 
				form.turnright();
				break;
		}
	}
	
}