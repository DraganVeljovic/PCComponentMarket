package data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import db.Company;
import db.Component;
import db.DBUtil;

public class PremiumData {
	
	private DBUtil db = DBUtil.getInstance();
	
	private List<Company> companies = new ArrayList<>();
	//private List<Reservartions>
	
	public PremiumData() {
		updateCompanies();
	}

	public void updateCompanies() {
		try {
			 
			CachedRowSet dbCompanies = db.executeQuery("SELECT * FROM Users, Company WHERE Id_User=Id_Company");
			
			while(dbCompanies.next()) {
				Company company = new Company();
				company.setId(dbCompanies.getInt("Id_User"));
				company.setUsername(dbCompanies.getString("Username"));
				company.setPassword(dbCompanies.getString("Password"));
				company.setEmail(dbCompanies.getString("Email"));
				company.setPhone(dbCompanies.getString("Phone"));
				company.setAddress(dbCompanies.getString("Address"));
				company.setCity(dbCompanies.getString("City"));
				company.setCountry(dbCompanies.getString("Country"));
				company.setCompanyName(dbCompanies.getString("Company_Name"));
				company.setCompanyPIB(dbCompanies.getInt("Company_PIB"));
				company.setComponentTypes(dbCompanies.getInt("Component_Types"));
				company.setComponentTypesPresent(dbCompanies.getInt("Component_Types_Present"));
				
				CachedRowSet dbCompanyComponents = 
						db.executeQuery("SELECT * FROM Stock AS S, Component AS C "
								+ "WHERE S.Id_Component=C.Id_Component AND "
								+ "S.Id_Company=" + company.getId());
				
				while(dbCompanyComponents.next()) {
					Component component = new Component();
					component.setId(dbCompanyComponents.getInt("Id_Component"));
					component.setTypeId(dbCompanyComponents.getInt("Id_Type"));
					component.setName(dbCompanyComponents.getString("Name"));
					component.setDescription(dbCompanyComponents.getString("Description"));
					component.setAmount(dbCompanyComponents.getInt("Amount"));
					component.setPrice(dbCompanyComponents.getInt("Price"));
					
					company.addComponent(component);
				}
				
				companies.add(company);
								
				dbCompanyComponents.close();
			}
			
			dbCompanies.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	
}
