package com.android.skin;

import java.util.ArrayList;

import com.yskang.colorpicker.ColorPicker;
import com.yskang.colorpicker.OnColorSelectedListener;


import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class ChangeSkin {

	public ArrayList<Integer> getarraylist(ArrayList<Integer> presetColors) {
		// TODO Auto-generated constructor stub
	 
		presetColors = new ArrayList<Integer>();
		presetColors.add(Color.BLUE);
		presetColors.add(Color.CYAN);
		presetColors.add(Color.argb(255, 222, 100, 18));
		presetColors.add(Color.argb(128, 222, 100, 18));
		presetColors.add(Color.argb(10, 128, 128, 128));
		return presetColors;
		
	
	}
	
     
	

}
