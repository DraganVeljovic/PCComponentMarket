package gui;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import data.ComponentData;
import data.MainData;
import db.Component;
import db.ComponentType;

public class ComponentDetailsPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ComponentData componentData = new ComponentData();

	private Component component;
	
	private Label nameLabel = new Label("Name:");
	private TextField nameText = new TextField();
	private Panel typePanel = new Panel();
	private Label typeLabel = new Label("Type: ");
	private JComboBox<String> typeText = new JComboBox();
	private Label amountLabel = new Label("Amout:");
	private TextField amountText = new TextField();
	private Label priceLabel = new Label("Price:");
	private TextField priceText = new TextField();
	private Label descriptionLabel = new Label("Description:");
	private TextArea descriptionText = new TextArea();
	private Label orderLabel = new Label("How much do you want?");
	private TextField orderText = new TextField();
	private Button firstButton = new Button();
	
	{
		for (ComponentType type : MainData.componentTypes) {
			typeText.addItem(type.getName());
		}
	}

	public ComponentDetailsPanel(CompanyPanel companyPanel) {
		firstButton.setLabel("Add");

		addInsertEvent(companyPanel);
		addAWTComponents(true);
	}

	public ComponentDetailsPanel(Component component) {
		this(component, true);
	}

	public ComponentDetailsPanel(Component component, boolean edit) {
		
		this.component = component;

		nameText.setText(component.getName());
		typeText.setSelectedIndex(component.getTypeId() - 1);
		amountText.setText(Integer.toString(component.getAmount()));
		priceText.setText(Integer.toString(component.getPrice()));
		descriptionText.setText(component.getDescription());

		if (!edit) {
			nameText.setEditable(false);
			typeText.setEnabled(false);
			amountText.setEditable(false);
			priceText.setEditable(false);
			descriptionText.setEditable(false);
			
			addOrderEvent();
			
			firstButton.setLabel("Order");
		} else {
			addUpdateEvent();
			/*
			MainFrame.getInstance().getMenuBar().getMenu(0).getItem(2)
			.removeActionListener(MainFrame.getInstance().getDeleteComponentActionListener());
			
			MainFrame.getInstance().setDeleteComponentActionListener(
					new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					/*List<String> result = componentData.delete(component.getId());
					
					if (result == null) {
						JOptionPane.showMessageDialog(null, "Component is successfully deleted!");
						MainFrame.getInstance().setCompanyPanel();
					} else {
						JOptionPane.showMessageDialog(null, result.get(0));
					}*//*
				}
			});
			
			MainFrame.getInstance().getMenuBar().getMenu(0).getItem(2)
			.addActionListener(MainFrame.getInstance().getDeleteComponentActionListener());
			
			MainFrame.getInstance().getMenuBar().getMenu(0).getItem(2).setEnabled(true);
			*/
			firstButton.setLabel("Update");
		}

		addAWTComponents(edit);
	}

	private void addAWTComponents(boolean edit) {
		setLayout(new GridLayout(0, 1));

		add(nameLabel);
		add(nameText);
		
		typePanel.add(typeLabel);
		typePanel.add(typeText);
		add(typePanel);
		
		add(amountLabel);
		add(amountText);
		add(priceLabel);
		add(priceText);
		add(descriptionLabel);
		add(descriptionText);
		
		if (!edit) {
			add(orderLabel);
			add(orderText);
		}
		
		add(firstButton);
	}

	private void addInsertEvent(CompanyPanel companyPanel) {
		firstButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				List<String> result = componentData.add(nameText.getText(), (String)typeText.getSelectedItem(),
						amountText.getText(), priceText.getText(),
						descriptionText.getText(), MainData.user.getId());

				if (result == null) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							"You have successfully added component!");
					companyPanel.updateCompanyComponents();
					companyPanel.revalidate();

				} else {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							result.get(0));
				}
				
			}
		});
	}

	private void addUpdateEvent() {
		firstButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = componentData.update(nameText.getText(), (String)typeText.getSelectedItem(),
						amountText.getText(), priceText.getText(),
						descriptionText.getText(), MainData.user.getId());

				if (result == null) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							"You have successfully updated component!");
				} else {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							result.get(0));
				}

			}
		});
	}
	
	private void addOrderEvent() {
		firstButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = componentData.order(
						component.getId(),
						orderText.getText(),
						MainData.selectedCompanyId);

				if (result == null) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							"You have successfully made your order. 3 days left to give us money!");
				} else {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							result.get(0));
				}
			}
		});
	}

}
