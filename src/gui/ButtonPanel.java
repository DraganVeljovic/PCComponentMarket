package gui;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import data.MainData;

public class ButtonPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected List<? extends DisplayItem> displayItems;
	protected Panel effectPanel;
	
	protected ButtonPanel(List<? extends DisplayItem> displayItems, Panel effectPanel) {
		this.displayItems = displayItems;
		this.effectPanel = effectPanel;
		
		setLayout(new GridLayout(0, 1));
		
		for (DisplayItem item : displayItems) {
			Panel buttonPanel = new Panel();
			Button button = new Button(item.getName());
			button.setSize(70, 30);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					updateEffectPanel(item);
				}
			});
			
			buttonPanel.add(button);
			add(buttonPanel);
		}
	}
	
	protected void updateEffectPanel(DisplayItem item) {

	}
} 
