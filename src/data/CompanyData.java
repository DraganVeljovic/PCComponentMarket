package data;

import java.util.ArrayList;
import java.util.List;

import db.Company;
import db.Component;

public class CompanyData {
	
	private List<Component> components = new ArrayList<>();
	
	private PremiumData premiumData = new PremiumData();
	
	private String companyName;
	
	public CompanyData() {
		updateComponents();
	}
	
	public void updateComponents() {
		premiumData.updateCompanies();
		components.clear();
		for (Company company : premiumData.getCompanies()) {
			if (company.getUsername().equals(MainData.user.getUsername())) {
				components.addAll(company.getComponents());
				break;
			}
		}
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
}
