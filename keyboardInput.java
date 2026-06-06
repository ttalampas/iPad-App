package com.csus.csc133;

import com.codename1.ui.*;

//for keyboard inputs 
public class keyboardInput {
	private Form form; 
	private CommandStudent[] sc; 
	
	public keyboardInput(Form form, CommandStudent[] sc) {
		//methods to handle input
			this.form = form;
			this.sc = sc; 
	}
	
	public void keys() {
		form.addKeyListener('w', evt -> sc[0].actionPerformed(null));
		form.addKeyListener('s', evt -> sc[1].actionPerformed(null));
		form.addKeyListener('a', evt -> sc[2].actionPerformed(null));
		form.addKeyListener('d', evt -> sc[3].actionPerformed(null));
		
		//for uppercase inputs 
		form.addKeyListener('W', evt -> sc[0].actionPerformed(null));
		form.addKeyListener('S', evt -> sc[1].actionPerformed(null));
		form.addKeyListener('A', evt -> sc[2].actionPerformed(null));
		form.addKeyListener('D', evt -> sc[3].actionPerformed(null));
	}
	
	
}