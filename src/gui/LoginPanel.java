package gui;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import data.LoginData;
import data.MainData;
import data.OrderyData;

public class LoginPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Frame parent;
	
	private TextField username = new TextField();
	private TextField password = new TextField();
	
	private LoginData login = new LoginData();
	
	private OrderyData orderyData = new OrderyData();
	
	public LoginPanel(Frame parent) {		
	
		this.parent = parent;
		
		Panel inputPanel = new Panel();
		
		inputPanel.setSize(180, 120);
		
		Label usernameLabel = new Label("Username: ");
		usernameLabel.setSize(90, 40);
		username.setSize(90, 40);
		
		Label passwordLabel = (new Label("Password: "));
		passwordLabel.setSize(90, 40);
		password.setSize(90, 40);
		password.setEchoChar('*');
		
		inputPanel.setLayout(new GridLayout(6, 2));
		inputPanel.add(usernameLabel);
		inputPanel.add(username);
		inputPanel.add(passwordLabel);
		inputPanel.add(password);
		
		Button loginButton = new Button("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginOperation();
			}
			
		});
		
		Button closeButton = new Button("Close");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
	
		inputPanel.add(loginButton);
		inputPanel.add(closeButton);
		
		// insert empty row
		inputPanel.add(new Label());
		inputPanel.add(new Label());
		
		inputPanel.add(new Label("Register an account:"));
		inputPanel.add(new Label());
		
		Button registerPremiumButton = new Button("Premium");
		registerPremiumButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().setRegisterPremiumPanell();
			}
		});
		
		Button registerCompanyButton = new Button("Company");
		registerCompanyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.getInstance().setRegisterCompanyPanell();
			}
		});
		
		inputPanel.add(registerPremiumButton);
		inputPanel.add(registerCompanyButton);
		
		add(inputPanel);
	}
	
	private void loginOperation() {
		
		if (username.getText().trim().equals("") || password.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(parent, "Polja ne smeju biti prazna!");
			return;
		}
		
		if (login.loginAttempt(username.getText().trim(), password.getText().trim())) {
			orderyData.cancelUnrealized();
			switch(MainData.userType) {
				case PREMIUM:
					MainFrame.getInstance().setPremiumPanel();
					break;
				case COMPANY:
					/*
					MenuItem deleteMenuItem = new MenuItem("Delete");
					deleteMenuItem.setEnabled(false);
					deleteMenuItem.addActionListener(MainFrame.getInstance().getDeleteComponentActionListener());
					MainFrame.getInstance().getMenuBar().getMenu(0).add(deleteMenuItem);
					*/
					MainFrame.getInstance().setCompanyPanel();
					break;
			};
		} else {
			JOptionPane.showMessageDialog(parent, "Logovanje neuspesno!");
		}
	}
}
