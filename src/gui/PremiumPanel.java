package gui;

import java.awt.GridLayout;
import java.awt.Panel;

import data.PremiumData;

public class PremiumPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PremiumData premiumData = new PremiumData();

	public PremiumPanel() {
		super();

		//setLayout(new BorderLayout());
		setLayout(new GridLayout(1, 3));
		
		Panel componentPanel = new Panel(),
				informationPanel = new Panel();
		
		add(new CompaniesPanel(premiumData.getCompanies(), componentPanel, informationPanel));
		add(componentPanel);
		add(informationPanel);
	
	}
	
	

}
