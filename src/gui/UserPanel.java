package gui;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

public class UserPanel extends Panel {

	private Label usernameLabel = new Label("Username: ");
	private TextField usernameTextField = new TextField();
	
	private Label passwordLabel = new Label("Password: ");
	private TextField passwordTextField = new TextField();
	
	private Label emailLabel = new Label("Email: ");
	private TextField emailTextField = new TextField("email@m.com");
	
	private Label phoneLabel = new Label("Phone: ");
	private TextField phoneTextField = new TextField("+38164785669");
	
	private Label addressLabel = new Label("Address: ");
	private TextField addressTextField = new TextField("Userova 100");
	
	private Label cityLabel = new Label("City: ");
	private TextField cityTextField = new TextField("Usergrad");
	
	private Label countryLabel = new Label("Country: ");
	private TextField countryTextField = new TextField("Userstate");
	
	public UserPanel() {
		super();
		
		setLayout(new GridLayout(7, 2));
		
		add(usernameLabel);
		add(usernameTextField);
		
		passwordTextField.setEchoChar('*');
		add(passwordLabel);
		add(passwordTextField);
		
		add(emailLabel);
		add(emailTextField);
		
		add(phoneLabel);
		add(phoneTextField);
		
		add(addressLabel);
		add(addressTextField);
		
		add(cityLabel);
		add(cityTextField);
		
		add(countryLabel);
		add(countryTextField);
	}

	public TextField getUsernameTextField() {
		return usernameTextField;
	}

	public void setUsernameTextField(TextField usernameTextField) {
		this.usernameTextField = usernameTextField;
	}

	public TextField getPasswordTextField() {
		return passwordTextField;
	}

	public void setPasswordTextField(TextField passwordTextField) {
		this.passwordTextField = passwordTextField;
	}

	public TextField getEmailTextField() {
		return emailTextField;
	}

	public void setEmailTextField(TextField emailTextField) {
		this.emailTextField = emailTextField;
	}

	public TextField getPhoneTextField() {
		return phoneTextField;
	}

	public void setPhoneTextField(TextField phoneTextField) {
		this.phoneTextField = phoneTextField;
	}

	public TextField getAddressTextField() {
		return addressTextField;
	}

	public void setAddressTextField(TextField addressTextField) {
		this.addressTextField = addressTextField;
	}

	public TextField getCityTextField() {
		return cityTextField;
	}

	public void setCityTextField(TextField cityTextField) {
		this.cityTextField = cityTextField;
	}

	public TextField getCountryTextField() {
		return countryTextField;
	}

	public void setCountryTextField(TextField countryTextField) {
		this.countryTextField = countryTextField;
	}
	
	
}
