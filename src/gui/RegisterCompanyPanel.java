package gui;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import data.RegisterData;

public class RegisterCompanyPanel extends Panel {
	
	protected UserPanel userPanel = new UserPanel();
	protected Panel addonPanel = new Panel();
	
	protected Label nameLabel = new Label("Name: ");
	protected TextField nameTextField = new TextField();
	
	protected Label pibLabel = new Label("PIB: ");
	protected TextField pibTextField = new TextField();
	
	protected Label ownerIdLabel = new Label("Owner ID: ");
	protected TextField ownerIdTextField = new TextField();
	
	protected Button okButton = new Button("Register");
	protected Button closeButton = new Button("Close");
	
	protected ActionListener okButtonListener;
	protected ActionListener closeButtonListener;
	
	protected Frame parent;
	
	protected RegisterData registerData = new RegisterData();
	
	public RegisterCompanyPanel(Frame parent) {
		super();
		
		this.parent = parent;
		
		addonPanel.setLayout(new GridLayout(6, 2));
			
		addonPanel.add(nameLabel);
		addonPanel.add(nameTextField);
		
		addonPanel.add(pibLabel);
		addonPanel.add(pibTextField);

		addonPanel.add(ownerIdLabel);
		addonPanel.add(ownerIdTextField);
		
		// insert empty row
		addonPanel.add(new Label());
		addonPanel.add(new Label());
		
		okButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = registerData.registerCompany(
						userPanel.getUsernameTextField().getText(), 
						userPanel.getPasswordTextField().getText(), 
						userPanel.getEmailTextField().getText(), 
						userPanel.getPhoneTextField().getText(),
						userPanel.getAddressTextField().getText(),
						userPanel.getCityTextField().getText(),
						userPanel.getCountryTextField().getText(),
						nameTextField.getText(),
						pibTextField.getText(),
						ownerIdTextField.getText());
						
				if (result == null) {
					JOptionPane.showMessageDialog(parent, "Company user is successfully registered!");
					MainFrame.getInstance().setLoginPanel();
				} else {
					JOptionPane.showMessageDialog(parent, result.get(0));
				}
			}
		};
		
		okButton.addActionListener(okButtonListener);
		
		closeButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().setLoginPanel();
			}
		};

		closeButton.addActionListener(closeButtonListener);
		
		addonPanel.add(okButton);
		addonPanel.add(closeButton);
		
		add(userPanel);
		add(addonPanel);
	}
}
