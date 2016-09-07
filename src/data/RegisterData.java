package data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import db.DBUtil;

public class RegisterData {
	
	private DBUtil db = DBUtil.getInstance();
	
	public RegisterData() {
		
	}
	
	// List of error messages;
	// return null if there's no errors
	public List<String> registerPremium(String username, String password, String email, String phone, 
			String address, String city, String country, String name, int gender) {
		
		List<String> errorMessages = new ArrayList<>();
		
		try {
			
			if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || address.equals("")
					|| city.equals("") || country.equals("") || name.equals("")) {
				errorMessages.add("Empty field is not allowed");
				
				return errorMessages;
			}
			
			CachedRowSet dbUser = db.executeQuery("SELECT * FROM Users WHERE username='" + username + "'");
			
			if (dbUser.first()) {
				errorMessages.add("Username already exists!");
				
				dbUser.close();
				
				return errorMessages;
			}
			
			db.executeQuery("INSERT INTO Users VALUES('" + username + "', '"
														 + password + "', '"
														 + email + "', '"
														 + phone + "', '"
														 + address + "', '"
														 + city + "', '"
														 + country + "')",
															true);
			
			dbUser = db.executeQuery("SELECT Id_User FROM Users WHERE username='" + username + "'");
			
			dbUser.first();	
			
			db.executeQuery("INSERT INTO Premium VALUES("  + dbUser.getInt(1) + ", '"
														   + name + "', "
														   + gender + ", "
														   + "0, 0)",
														   true);
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> updatePremium(String username, String password, String email, String phone, 
			String address, String city, String country, String name, int gender) {
		
		List<String> errorMessages = new ArrayList<>();
		
		try {
			
			if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || address.equals("")
					|| city.equals("") || country.equals("") || name.equals("")) {
				errorMessages.add("Empty field is not allowed");
				
				return errorMessages;
			}
			
			CachedRowSet dbUser = db.executeQuery(
					"SELECT * FROM Users WHERE Username='" + username + "' AND Id_User<>" 
							+ MainData.user.getId());
			
			if (dbUser.first()) {
				errorMessages.add("Username already exists!");
				
				dbUser.close();
				
				return errorMessages;
			}
			
			db.executeQuery("UPDATE Users SET "
							+ "Username = '" + username + "', "
							+ "Password = '" + password + "', "
							+ "Email = '" + email + "', "
							+ "Phone = '" + phone + "', "
							+ "Address = '" + address + "', "
							+ "City = '" + city + "', "
							+ "Country = '" + country + "' "
							+ "WHERE Id_User = " + MainData.user.getId(),
															true);
			
			dbUser.first();
			
			db.executeQuery("Update Premium SET "
							+ "Name = '" + name + "', "
							+ "Gender = " + gender
							+ " WHERE Id_Premium = "  + MainData.user.getId(),
													true);
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> updateCompany(String username, String password, String email, String phone, 
			String address, String city, String country, String companyName, String companyPIB,
			String ownerId) {
		
		List<String> errorMessages = new ArrayList<>();
		
		try {
			
			if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || address.equals("")
					|| city.equals("") || country.equals("") || companyName.equals("")
					|| companyName.equals("") || companyPIB.equals("") || ownerId.equals("")) {
				errorMessages.add("Empty field is not allowed");
				
				return errorMessages;
			}
			
			CachedRowSet dbUser = db.executeQuery(
					"SELECT * FROM Users WHERE Username='" + username + "' AND Id_User<>" 
							+ MainData.user.getId());
			
			if (dbUser.first()) {
				errorMessages.add("Username already exists!");
				
				dbUser.close();
				
				return errorMessages;
			}
			
			db.executeQuery("UPDATE Users SET "
					+ "Username = '" + username + "', "
					+ "Password = '" + password + "', "
					+ "Email = '" + email + "', "
					+ "Phone = '" + phone + "', "
					+ "Address = '" + address + "', "
					+ "City = '" + city + "', "
					+ "Country = '" + country + "' "
					+ "WHERE Id_User = " + MainData.user.getId(),
													true);
			
			db.executeQuery("Update Company SET "
					+ "Company_Name = '" + companyName + "', "
					+ "Company_PIB = " + Integer.parseInt(companyPIB) + ", "
					+ "Owner_Id = " + Integer.parseInt(ownerId) + " "
					+ "WHERE Id_Company = "  + MainData.user.getId(),
												   true);
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<String> registerCompany(String username, String password, String email, String phone, 
			String address, String city, String country, String companyName, String companyPIB,
			String ownerId) {
		
		List<String> errorMessages = new ArrayList<>();
		
		try {
			
			if (username.equals("") || password.equals("") || email.equals("") || phone.equals("") || address.equals("")
					|| city.equals("") || country.equals("") || companyName.equals("")
					|| companyName.equals("") || companyPIB.equals("") || ownerId.equals("")) {
				errorMessages.add("Empty field is not allowed");
				
				return errorMessages;
			}
			
			CachedRowSet dbUser = db.executeQuery("SELECT * FROM Users WHERE username='" + username + "'");
			
			if (dbUser.first()) {
				errorMessages.add("Username already exists!");
				
				dbUser.close();
				
				return errorMessages;
			}
			
			db.executeQuery("INSERT INTO Users VALUES('" + username + "', '"
														 + password + "', '"
														 + email + "', '"
														 + phone + "', '"
														 + address + "', '"
														 + city + "', '"
														 + country + "')",
															true);
			
			dbUser = db.executeQuery("SELECT Id_User FROM Users WHERE username='" + username + "'");
			
			dbUser.first();	
			
			//db.executeQuery("SET IDENTITY_INSERT Company ON");
			
			//db.executeQuery("INSERT INTO Company(Id_Company, Company_Name, Company_PIB, Component_Types, "
			//		+ "Component_Types_Present, Company_Id, Owner_Id)"
			db.executeQuery("INSERT INTO Company"
											 + " VALUES("  + dbUser.getInt(1) + ", '"
														   + companyName + "', "
														   + Integer.parseInt(companyPIB) + ", "
														   + "0, 0, " 
														   + Integer.parseInt(ownerId) + ")",
														   true);
			
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
