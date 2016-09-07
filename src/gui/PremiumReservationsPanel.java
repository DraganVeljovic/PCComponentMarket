package gui;

import java.awt.Panel;
import java.util.List;

import javax.swing.DefaultListModel;

import data.MainData;
import data.OrderyData;
import db.Order;

public class PremiumReservationsPanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected OrderyData orderyData = new OrderyData();
	//private java.awt.List list = new java.awt.List();
	protected DefaultListModel<String> listModel = new DefaultListModel<String>();
	protected javax.swing.JList list = new javax.swing.JList(listModel);
	
	/*{
		list.add("No.  Component      Company      Price      Amount      Total      Status      Valid till");
		
		int no = 1;
		
		for (Order order : orderyData.getAllOrdersForPremium(MainData.user.getId())) {
			list.add(""
					+ no++ + "       "
					+ order.getComponentName() + "           "
					+ order.getCompanyName() + "           "
					+ order.getPrice() + "           "
					+ order.getAmount() + "       "
					+ order.getOverallPrice() + "       "
					+ getStatusString(order.getStatusId()) + "        "
					+ order.getDate()
					);
		}
		
		add(list);
	}*/
	
	{
		int no = 1;
		
		for (Order order : getData()) {
			listModel.addElement(""
					+ no++ + "       "
					+ order.getComponentName() + "           "
					+ order.getCompanyName() + "           "
					+ order.getPrice() + "           "
					+ order.getAmount() + "       "
					+ order.getOverallPrice() + "       "
					+ getStatusString(order.getStatusId()) + "        "
					+ order.getDate()
					);
		}
		
		add(list);
	}
	
	protected String getStatusString(int statusId) {
		switch(statusId) {
		case 1: 
			return "pending"; 
		case 2:
			return "failed";
		case 3: 
			return "realized";
		default:
			return "";			
		}
	}
	
	protected List<Order> getData() {
		return orderyData.getAllOrdersForPremium(MainData.user.getId());
	}

}
