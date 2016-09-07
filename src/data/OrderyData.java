package data;

import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import db.DBUtil;
import db.Order;

public class OrderyData {
	
	private DBUtil db = DBUtil.getInstance();
	
	public List<Order> getAllOrdersForPremium(int premiumId) {
		return getAllOrdersByCriteria("Id_Premium", Integer.toString(premiumId));
	}
	
	public List<Order> getAllOrdersForCompany(int companyId) {
		return getAllOrdersByCriteria("Id_Company", Integer.toString(companyId));
	}
	
	private List<Order> getAllOrdersByCriteria(String criteria, String value) {
		List<Order> result = new ArrayList<>();
		
		try {

			CachedRowSet dbOrder = db.executeQuery(
					"SELECT * FROM Ordery WHERE " + criteria + "=" + value);
			
			while(dbOrder.next()) {
				Order order = new Order();
				
				order.setId(dbOrder.getInt(5));
				order.setPremiumId(dbOrder.getInt(1));
				order.setCompanyId(dbOrder.getInt(6));
				order.setComponentId(dbOrder.getInt(2));
				order.setDate(dbOrder.getDate(4));
				order.setAmount(dbOrder.getInt(7));
				order.setStatusId(dbOrder.getInt(3));
				
				CachedRowSet dbRest = db.executeQuery(
						"SELECT Company_Name FROM Company WHERE Id_Company = " + order.getCompanyId());
				
				dbRest.first();
				
				order.setCompanyName(dbRest.getString(1));
				
				dbRest = db.executeQuery(
						"SELECT Name From Component WHERE Id_Component = " + order.getComponentId());
				
				dbRest.first();
				
				order.setComponentName(dbRest.getString(1));
				
				dbRest = db.executeQuery(
						"SELECT Price From Stock WHERE Id_Component = " + order.getComponentId()
						+ " AND Id_Company = " + order.getCompanyId());
				
				dbRest.first();
				
				order.setPrice(dbRest.getInt(1));
				
				order.setOverallPrice(order.getAmount() * order.getPrice());
				
				result.add(order);
			}
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public void sell(int orderId) {
		try {

			db.executeQuery("UPDATE Ordery SET Status_Type = 3 WHERE Id_Ordery = " + orderId, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancel(int orderId, int componentId, int companyId, int amount) {
		try {

			db.executeQuery("UPDATE Ordery SET Status_Type = 2 WHERE Id_Ordery = " + orderId, true);
			
			db.executeQuery("UPDATE Stock SET Amount = Amount + " + amount + 
					" WHERE Id_Company = " + companyId + " AND Id_Component = " + componentId, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelUnrealized() {	
		try {

			CachedRowSet dbOrder = db.executeQuery("SELECT * FROM Ordery");
			
			while(dbOrder.next()) {
				Order order = new Order();
				
				order.setStatusId(dbOrder.getInt(3));

				if (order.getStatusId() != 1) 
					continue;
				
				order.setId(dbOrder.getInt(5));
				order.setPremiumId(dbOrder.getInt(1));
				order.setCompanyId(dbOrder.getInt(6));
				order.setComponentId(dbOrder.getInt(2));
				order.setDate(dbOrder.getDate(4));
				order.setAmount(dbOrder.getInt(7));
				
				CachedRowSet dbRest = db.executeQuery(
						"SELECT Company_Name FROM Company WHERE Id_Company = " + order.getCompanyId());
				
				dbRest.first();
				
				order.setCompanyName(dbRest.getString(1));
				
				dbRest = db.executeQuery(
						"SELECT Name From Component WHERE Id_Component = " + order.getComponentId());
				
				dbRest.first();
				
				order.setComponentName(dbRest.getString(1));
				
				dbRest = db.executeQuery(
						"SELECT Price From Stock WHERE Id_Component = " + order.getComponentId()
						+ " AND Id_Company = " + order.getCompanyId());
				
				dbRest.first();
				
				order.setPrice(dbRest.getInt(1));
				
				order.setOverallPrice(order.getAmount() * order.getPrice());
				
				// three days expired
				if (order.getDate().getTime() + 3 * 24 * 60 * 60 * 1000 < 
						(new java.util.Date()).getTime()) {
					System.out.println("Amount: " + order.getAmount());
					db.executeQuery("UPDATE Ordery SET Status_Type=2 WHERE "
							+ "Id_Ordery=" + order.getId(), true);
					
					db.executeQuery("UPDATE Stock SET Amount = Amount + " + order.getAmount() + 
							" WHERE Id_Component=" + order.getComponentId() + 
							" AND Id_Company=" + order.getCompanyId(), true);
					
					db.executeQuery("UPDATE Premium SET "
							+ "Reservations_Failure = Reservations_Failure + 1"
							+ " WHERE Id_Premium=" + order.getPremiumId(), true);
					
				}
				
				dbRest.close();
			}
			
			dbOrder.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
