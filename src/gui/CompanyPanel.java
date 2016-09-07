package gui;

import java.awt.GridLayout;
import java.awt.Panel;

import data.CompanyData;

public class CompanyPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompanyData companyData = new CompanyData();
	
	private ComponentsPanel componentsPanel;
	
	public CompanyPanel() {
		super();
		
		//setLayout(new BorderLayout());
		setLayout(new GridLayout(1, 3));
		
		Panel informationPanel = new Panel();
		
		componentsPanel = new ComponentsPanel(companyData.getComponents(), informationPanel);
		
		add(componentsPanel);
		add(informationPanel);
		add(new ComponentDetailsPanel(this));
	
	}
	
	public void updateCompanyComponents() {
		companyData.updateComponents();
		componentsPanel.revalidate();
	}

}
