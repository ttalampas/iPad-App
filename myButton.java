package com.csus.csc133;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.plaf.Border;

public class myButton extends Button {
	public myButton(String string) {
		super(string); 
		//style for all buttons
		getAllStyles().setBgTransparency(255);
		getAllStyles().setFgColor(ColorUtil.rgb(255, 255, 255)); 
		getAllStyles().setBgColor(ColorUtil.BLUE);
		getAllStyles().setBorder(Border.createLineBorder(1,ColorUtil.BLACK));
	}
}