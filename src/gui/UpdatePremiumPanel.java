package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import data.MainData;
import db.Premium;

public class UpdatePremiumPanel extends RegisterPremiumPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdatePremiumPanel(Frame parent) {
		super(parent);
		
		this.userPanel.getUsernameTextField().setText(
				MainData.user.getUsername());
		this.userPanel.getPasswordTextField().setText(
				MainData.user.getPassword());
		this.userPanel.getEmailTextField().setText(
				MainData.user.getEmail());
		this.userPanel.getPhoneTextField().setText(
				MainData.user.getPhone());
		this.userPanel.getAddressTextField().setText(
				MainData.user.getAddress());
		this.userPanel.getCityTextField().setText(
				MainData.user.getCity());
		this.userPanel.getCountryTextField().setText(
				MainData.user.getCountry());
		
		this.nameTextField.setText(((Premium)MainData.user).getName());
		
		this.genderChoice.removeAll();
		if (((Premium)MainData.user).isGender()) {
			this.genderChoice.add("Male");
			this.genderChoice.add("Female");
		} else {
			this.genderChoice.add("Female");
			this.genderChoice.add("Male");
		}
		
		this.okButton.setLabel("Change");
		this.okButton.removeActionListener(okButtonListener);
		
		okButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = registerData.updatePremium(
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
					JOptionPane.showMessageDialog(parent, "Premium user is successfully chanaged!");
					MainFrame.getInstance().setPremiumPanel();
				} else {
					JOptionPane.showMessageDialog(parent, result.get(0));
				}
			}
		}; 
		
		this.okButton.addActionListener(okButtonListener);
		
		this.closeButton.removeActionListener(closeButtonListener);
		
		closeButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().setPremiumPanel();
			}
		};
		
		closeButton.addActionListener(closeButtonListener);
	}

}
