package com.csus.csc133;

import java.util.Random; 

public class StudentWithStrategy extends Student {
	private GameModel gm; 
	private Strategy strat;
	private Random rand = new Random(); 
	
	public StudentWithStrategy(Strategy strat, GameModel gm) {
		this.strat = strat; 
		this.gm = gm; 
	}
	
	//function to change strategy
	public void changeStrat() {
		int choice = rand.nextInt(3);  
		switch(choice) {
			case 0:
				strat = new StrategyRandom();
				break;
			
			case 1:
				strat = new StrategyVertical();
				break;
				
			case 2:
				strat = new StrategyHorizontal();
				break; 
		}
		this.setStrat(strat);
		System.out.println("Changed strategy to: " + strat.getClass().getSimpleName());
		gm.setGamemessage("Changed strategy to: " + strat.getClass().getSimpleName());
	}
	
	//function to print strategy
	public String toString() {
		String message = " [Strategy: " + strat.getClass().getSimpleName() + "]";
		return super.toString() + message; 
	}
	
	//function to apply movement
	public void move() {
		strat.apply(this);
	}
	
	//getters and setters
	public void setStrat(Strategy strat) {
		this.strat = strat;
	}

	public Strategy getStrat() {
		return strat;
	}
}