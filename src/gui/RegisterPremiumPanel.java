package gui;

import java.awt.Button;
import java.awt.Choice;
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

public class RegisterPremiumPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected UserPanel userPanel = new UserPanel();
	protected Panel addonPanel = new Panel();
	
	protected Label nameLabel = new Label("Name: ");
	protected TextField nameTextField = new TextField();
	
	protected Label genderLabel = new Label("Gender: ");
	protected Choice genderChoice = new Choice();
	
	protected Button okButton = new Button("Register");
	protected Button closeButton = new Button("Close");
	
	protected ActionListener okButtonListener;
	protected ActionListener closeButtonListener;
	
	protected Frame parent;
	
	protected RegisterData registerData = new RegisterData();
	
	public RegisterPremiumPanel(Frame parent) {
		super();
		
		this.parent = parent;
		
		addonPanel.setLayout(new GridLayout(4, 2));

		genderChoice.add("Male");
		genderChoice.add("Female");
			
		addonPanel.add(nameLabel);
		addonPanel.add(nameTextField);
		
		addonPanel.add(genderLabel);
		addonPanel.add(genderChoice);
		
		// insert empty row
		addonPanel.add(new Label());
		addonPanel.add(new Label());
		
		okButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = registerData.registerPremium(
						userPanel.getUsernameTextField().getText(), 
						userPanel.getPasswordTextField().getText(), 
						userPanel.getEmailTextField().getText(), 
						userPanel.getPhoneTextField().getText(),
						userPanel.getAddressTextField().getText(),
						userPanel.getCityTextField().getText(),
						userPanel.getCountryTextField().getText(),
						nameTextField.getText(),
						(genderChoice.getSelectedItem().equals("Male") ? 1 : 0));
				
				if (result == null) {
					JOptionPane.showMessageDialog(parent, "Premium user is successfully registered!");
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
	
	public UserPanel getUserPanel() {
		return userPanel;
	}

	public void setUserPanel(UserPanel userPanel) {
		this.userPanel = userPanel;
	}

	public TextField getNameTextField() {
		return nameTextField;
	}

	public void setNameTextField(TextField nameTextField) {
		this.nameTextField = nameTextField;
	}

	public Choice getGenderChoice() {
		return genderChoice;
	}

	public void setGenderChoice(Choice genderChoice) {
		this.genderChoice = genderChoice;
	}

	public Button getOkButton() {
		return okButton;
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}

	public Button getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(Button closeButton) {
		this.closeButton = closeButton;
	}
	
	
}
