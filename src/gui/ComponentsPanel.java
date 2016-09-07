package gui;

import java.awt.GridLayout;
import java.awt.Panel;
import java.util.List;

import data.MainData;
import db.Component;

public class ComponentsPanel extends ButtonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComponentsPanel(List<? extends DisplayItem> displayItems,
			Panel effectPanel) {
		super(displayItems, effectPanel);
	}
	
	@Override
	protected void updateEffectPanel(DisplayItem item) {
		effectPanel.removeAll();
		effectPanel.setLayout(new GridLayout(1, 1));
		effectPanel.add(new ComponentDetailsPanel((Component)item, 
				MainData.userType == MainData.UserType.PREMIUM ? false : true));
		effectPanel.revalidate();
		
	}
}
