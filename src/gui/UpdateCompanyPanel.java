package gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import data.MainData;
import db.Company;

public class UpdateCompanyPanel extends RegisterCompanyPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdateCompanyPanel(Frame parent) {
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
		
		this.nameTextField.setText(((Company)MainData.user).getCompanyName());
		this.pibTextField.setText(Integer.toString(((Company)MainData.user).getCompanyPIB()));
		this.ownerIdTextField.setText(Integer.toString(((Company)MainData.user).getOwnerId()));
		
		this.okButton.setLabel("Change");
		this.okButton.removeActionListener(okButtonListener);
		
		okButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> result = registerData.updateCompany(
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
					JOptionPane.showMessageDialog(parent, "Company user is successfully updated!");
					MainFrame.getInstance().setCompanyPanel();
				} else {
					JOptionPane.showMessageDialog(parent, result.get(0));
				}
			}
		};

		this.okButton.addActionListener(okButtonListener);
		
		closeButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().setCompanyPanel();
			}
		};
		
		this.closeButton.addActionListener(closeButtonListener);
	}

}
