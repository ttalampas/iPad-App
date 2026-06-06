package com.csus.csc133;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class StrategyChange extends Command {
	private GameModel gm; 
	
	public StrategyChange(GameModel gm) {
		super("Change Strategies");       
		this.gm = gm; 
	}
	
	@Override
	//command handling for changing strategy to add to the vector in sacrun
	public void actionPerformed(ActionEvent evt) {
		//iterate through all gameobjects and change strategy for student with strategy instances
		GameModel.GameObjectCollection.Iterator it = gm.getS().getIterator();
		while(it.hasNext()) {
			GameObject obj = it.getNext();
			if (obj instanceof StudentWithStrategy) {
	            ((StudentWithStrategy) obj).changeStrat();
			}
		}
	}
}