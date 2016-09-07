package gui;

import java.awt.Panel;
import java.util.List;

import data.MainData;
import db.Company;

public class CompaniesPanel extends ButtonPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Panel componentPanelInfo;

	public CompaniesPanel(List<? extends DisplayItem> displayItems,
			Panel effectPanel, Panel componentInfoPanel) {
		super(displayItems, effectPanel);
		
		this.componentPanelInfo = componentInfoPanel;
	}

	@Override
	protected void updateEffectPanel(DisplayItem item) {
		MainData.selectedCompanyId = ((Company)item).getId();
		
		effectPanel.removeAll();
		componentPanelInfo.removeAll();
		effectPanel.add(new ComponentsPanel(
				((Company)item).getComponents(), componentPanelInfo));
		effectPanel.revalidate();
	}
}
