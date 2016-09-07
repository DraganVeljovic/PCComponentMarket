package gui;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.MainData;
import db.Order;

public class CompanyReservationsPanel extends PremiumReservationsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	{
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					if (list.getSelectedIndex() != -1) {

						Order order = orderyData.getAllOrdersForCompany(
								MainData.user.getId()).get(
								list.getSelectedIndex());

						if (order.getStatusId() == 1) {
							int reply = JOptionPane.showConfirmDialog(null,
									"Sell/ Cancel Order/ Quit",
									"What to do with an order?",
									JOptionPane.YES_NO_CANCEL_OPTION);

							if (reply == JOptionPane.YES_OPTION) {
								orderyData.sell(order.getId());
							} else if (reply == JOptionPane.NO_OPTION) {
								orderyData.cancel(order.getId(), order.getComponentId(), 
										order.getCompanyId(), order.getAmount());	
							}
						}
					}
				}	
			}
		});
	}

	@Override
	protected List<Order> getData() {
		return orderyData.getAllOrdersForCompany(MainData.user.getId());
	}

}
