package data;

import gui.MainFrame;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import db.ComponentType;
import db.DBUtil;

public class ComponentData {

	private DBUtil db = DBUtil.getInstance();

	// change with Component
	public List<String> add(String name, String type, String amount,
			String price, String description, int companyId) {

		List<String> errorMessages = new ArrayList<>();

		try {

			if (name.equals("") || type.equals("") || amount.equals("")
					|| price.equals("") || description.equals("")) {
				errorMessages.add("Empty field is not allowed");

				return errorMessages;
			}

			int typeId = 0;
			int componentId = 0;

			for (ComponentType componentType : MainData.componentTypes) {
				if (componentType.getName().equals(type)) {
					typeId = componentType.getId();
					break;
				}
			}

			CachedRowSet dbComponent = db
					.executeQuery("SELECT Id_Component FROM Component WHERE name='"
							+ name + "'");

			if (dbComponent.first()) {
				componentId = dbComponent.getInt(1);
			} else {
				db.executeQuery("INSERT INTO Component VALUES(" + typeId
						+ ", '" + name + "', '" + description + "')", true);

				dbComponent = db
						.executeQuery("SELECT Id_Component FROM Component WHERE name='"
								+ name + "'");

				dbComponent.first();

				componentId = dbComponent.getInt(1);
			}

			db.executeQuery("UPDATE Component_Type SET Count = Count + "
					+ Integer.parseInt(amount) + " WHERE Id_Type = " + typeId,
					true);

			db.executeQuery("INSERT INTO Stock VALUES(" + componentId + ", "
					+ Integer.parseInt(amount) + ", " + Integer.parseInt(price)
					+ "," + companyId + ")", true);
			
			dbComponent.close();

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			errorMessages.add(e.getMessage());
			
			return errorMessages;
		}
	}

	// change with Component
	public List<String> update(String name, String type, String amount,
			String price, String description, int companyId) {

		List<String> errorMessages = new ArrayList<>();

		try {

			if (name.equals("") || type.equals("") || amount.equals("") || price.equals("")
					|| description.equals("")) {
				errorMessages.add("Empty field is not allowed");

				return errorMessages;
			}
			
			int typeId = 0;
			int oldTypeId = 0;
			int componentId = 0;

			for (ComponentType componentType : MainData.componentTypes) {
				if (componentType.getName().equals(type)) {
					typeId = componentType.getId();
					break;
				}
			}

			CachedRowSet dbComponent = db
					.executeQuery("SELECT Id_Component, Id_Type FROM Component WHERE Name='"
							+ name + "'");

			dbComponent.first();
			
			componentId = dbComponent.getInt(1);
			
			oldTypeId = dbComponent.getInt(2);
			
			db.executeQuery("UPDATE Component SET "
					+ "Description='" + description + "', "
					+ "Name='" + name + "', "
					+ "Id_Type=" + typeId + " "
					+ "WHERE Id_Component=" + componentId, true);
			
			dbComponent = db.executeQuery("SELECT Amount FROM Stock WHERE "
					+ "Id_Component=" + componentId + " AND Id_Company=" 
					+ MainData.user.getId());
			
			dbComponent.first();
			
			int previousAmount = dbComponent.getInt(1);
			
			int amountDifference = previousAmount - Integer.parseInt(amount);

			db.executeQuery("UPDATE Component_Type SET Count = Count - " + amountDifference 
					+ " WHERE Id_Type = " + oldTypeId, true);
			
			db.executeQuery("UPDATE Component_Type SET Count = Count + " + amountDifference 
					+ " WHERE Id_Type = " + typeId, true);
			
			db.executeQuery("UPDATE Stock SET Amount = " + Integer.parseInt(amount) + " WHERE "
					+ "Id_Component=" + componentId + " AND Id_Company=" 
					+ MainData.user.getId(), true);
			
			dbComponent.close();

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			errorMessages.add(e.getMessage());
			
			return errorMessages;
		}

	}

	// change with Component
	public List<String> order(int componentId, String amount, int companyId) {

		List<String> errorMessages = new ArrayList<>();

		try {

			if (amount.equals("")) {
				errorMessages.add("Empty field is not allowed");

				return errorMessages;
			}

			db.executeQuery("INSERT INTO Ordery VALUES(" 
					+ MainData.user.getId() + ", "
					+ componentId + ", "
					+ 1 + ", '" /* Status Type - pending */
					+ new java.sql.Date((new java.util.Date()).getTime()) + "', "
					+ companyId + ", "
					+ Integer.parseInt(amount) + ")", 
					true);

			db.executeQuery("UPDATE Stock SET Amount = Amount - " + Integer.parseInt(amount) 
					+ " WHERE Id_Component = " + componentId 
					+ " AND Id_Company = " + companyId, true);
			
			return null;

		} catch (Exception e) {
			e.printStackTrace();

			errorMessages.add(e.getMessage());
			
			return errorMessages;
		}
	}

	public List<String> delete(int componentId) {
		try {

			/*CachedRowSet dbComponent = db.executeQuery("SELECT Amount FROM Stock "
					+ "WHERE Id_Component=" + componentId + " AND Id_Company="
					+ MainData.user.getId());

			dbComponent.first();
			
			if (dbComponent.getInt(1) != 0) {
				return new ArrayList<String>() 
						{{ add("You can only delete components which you don't have on stock."); }};
			}
						
			db.executeQuery("DELETE FROM Stock WHERE Id_Component=" + componentId + " AND Id_Company="
					+ MainData.user.getId(), true);
			
			dbComponent.close();*/

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			
			return new ArrayList<String>() {{ add(e.getMessage()); }};
		}
	}
}
