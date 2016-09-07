package data;

import javax.sql.rowset.CachedRowSet;

import db.Company;
import db.ComponentType;
import db.DBUtil;
import db.Premium;
import db.User;

public class LoginData {
	
	private DBUtil db = DBUtil.getInstance();
	
	public LoginData() {
		
	}
	
	public boolean loginAttempt(String username, String password) {
		
		try {
			CachedRowSet dbUser = db.executeQuery("SELECT * FROM Users WHERE username='" + username + "'" 
					+ " AND password='" + password + "'");
			if (dbUser.first()) {
				
				getAllTypes();
				
				User user = new User();
				user.setId(dbUser.getInt(1));
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(dbUser.getString(4));
				user.setPhone(dbUser.getString(5));
				user.setAddress(dbUser.getString(6));
				user.setCity(dbUser.getString(7));
				user.setCountry(dbUser.getString(8));
				
				CachedRowSet dbCompany = 
						db.executeQuery("SELECT * FROM Company WHERE Id_Company=" + user.getId());
				if (dbCompany.first()) {
					Company company = new Company(user);
					company.setCompanyName(dbCompany.getString(2));
					company.setCompanyPIB(dbCompany.getInt(3));
					company.setComponentTypes(dbCompany.getInt(4));
					company.setComponentTypesPresent(dbCompany.getInt(5));
					company.setOwnerId(dbCompany.getInt(7));
					
					dbUser.close();
					dbCompany.close();
					
					MainData.user = company;
					MainData.userType = MainData.UserType.COMPANY;
					
					return true;
					
				} else {
					CachedRowSet dbPremium =
							db.executeQuery("SELECT * FROM Premium WHERE Id_Premium=" + user.getId());
					if (dbPremium.first()) {
						
						Premium premium = new Premium(user);
						premium.setName(dbPremium.getString(2));
						premium.setGender(dbPremium.getBoolean(3));
						premium.setReservationsSuccess(dbPremium.getInt(4));
						premium.setReservationsFailure(dbPremium.getInt(5));
						
						dbUser.close();
						dbCompany.close();
						dbPremium.close();
						
						MainData.user = premium;
						MainData.userType = MainData.UserType.PREMIUM;
						
						return true;
						
					} else {
						
						dbUser.close();
						dbCompany.close();
						dbPremium.close();
						
						return false;
					}
				}
				
			} else {
				
				dbUser.close();
				
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private void getAllTypes() {
		try {

			CachedRowSet dbComponentTypes = db.executeQuery("SELECT * From Component_Type");

			while (dbComponentTypes.next()) {
				MainData.componentTypes.add(
						new ComponentType(dbComponentTypes.getInt(1),
											dbComponentTypes.getString(2),
											dbComponentTypes.getInt(3)));
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}
	
}
