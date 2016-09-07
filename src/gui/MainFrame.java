package gui;

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import data.MainData;
import db.Company;
import db.Component;

public class MainFrame extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static MainFrame singleton = null;
	
	private List<Company> companies = new ArrayList<Company>();
	private List<Component> components = new ArrayList<Component>();
	
	private Panel mainPanel = new Panel();
	
	private Menu view;
	private MenuItem openView;
	private MenuItem closeView; 
	private Menu logout;
	private MenuItem clickChange;
	private MenuItem clickLogout;
	
	private ActionListener deleteComponentActionListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private MainFrame() {
		super();
		
		setTitle("SAB Projekat");
		setSize(800, 500);
		setResizable(false);
		
		MenuBar menuBar = new MenuBar();
		
		//Menu 
		view = new Menu("Component");
		openView = new MenuItem("Open overview");
		openView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainData.userType == MainData.UserType.PREMIUM)
					setPremiumReservationsPanel();
				else 
					setCompanymReservationsPanel();
			}
		});
		view.add(openView);
		
		closeView = new MenuItem("Close overview");
		closeView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainData.userType == MainData.UserType.PREMIUM)
					setPremiumPanel();
				else 
					setCompanyPanel();
			}
		});
		view.add(closeView);
		
		//Menu 
		logout = new Menu("User");
		clickChange = new MenuItem("Change");
		clickChange.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (MainData.userType == MainData.UserType.PREMIUM)
					setUpdatePremiumPanel();
				else 
					setUpdateCompanyPanel();
				
			}
		});
		logout.add(clickChange);
		
		clickLogout = new MenuItem("Logout");
		clickLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainData.user = null;
				MainData.userType = null;
				MainData.componentTypes = new ArrayList<>();
				MainData.selectedCompanyId = 0;
				setLoginPanel();
			}
		});
		logout.add(clickLogout);
		
		menuBar.add(view);
		menuBar.add(logout);
		this.setMenuBar(menuBar);
		
		setLoginPanel();
		
		/*
		DBUtil db = DBUtil.getInstance();
		
		try {
			Connection connection = db.getConnection(); 
			
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM Company");
			
			while(resultSet.next()) {
				companies.add(new Company(rs.));
			}
			
			statement.cancel();
			
			db.closeConnection(connection);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		Component motherboard = new Component("Matica");
		motherboard.setDetails("Ovo je maticna");
		motherboard.setAmount(7);
		motherboard.setPrice(10000);
		
		Component graphic = new Component("Graficka");
		graphic.setDetails("Ovo je graficka");
		graphic.setAmount(7);
		graphic.setPrice(10000);
		
		components.add(motherboard);
		components.add(graphic);
		
		Company winwin = new Company("WinWin");
		winwin.addComponents(components);
		
		companies.add(winwin);
		companies.add(new Company("Gigatron"));
		
		this.setLayout(new GridLayout(1, 3));
		
		Panel componentPanel = new Panel(),
				informationPanel = new Panel();
		
		add(new CompanyPanel(companies, componentPanel, informationPanel));
		add(componentPanel);
		add(informationPanel);
		
		*/
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public static MainFrame getInstance() {
		if (singleton == null) {
			singleton = new MainFrame();
		} 
		
		return singleton;
	}
	
	
	
	public ActionListener getDeleteComponentActionListener() {
		return deleteComponentActionListener;
	}

	public void setDeleteComponentActionListener(
			ActionListener deleteComponentActionListener) {
		this.deleteComponentActionListener = deleteComponentActionListener;
	}

	public void setLoginPanel() {
		view.setEnabled(false);
		logout.setEnabled(false);
		this.remove(mainPanel);
		mainPanel = new LoginPanel(this);
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setPremiumPanel() {
		view.setEnabled(true);
		openView.setEnabled(true);
		closeView.setEnabled(false);
		logout.setEnabled(true);
		clickChange.setEnabled(true);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new PremiumPanel();
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setCompanyPanel() {
		view.setEnabled(true);
		openView.setEnabled(true);
		closeView.setEnabled(false);
		logout.setEnabled(true);
		clickChange.setEnabled(true);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new CompanyPanel();
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setRegisterPremiumPanell() {
		view.setEnabled(false);
		logout.setEnabled(false);
		this.remove(mainPanel);
		mainPanel = new RegisterPremiumPanel(this);
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setRegisterCompanyPanell() {
		view.setEnabled(false);
		logout.setEnabled(false);
		this.remove(mainPanel);
		mainPanel = new RegisterCompanyPanel(this);
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setPremiumReservationsPanel() {
		view.setEnabled(true);
		openView.setEnabled(false);
		closeView.setEnabled(true);
		logout.setEnabled(true);
		clickChange.setEnabled(true);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new PremiumReservationsPanel();
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setCompanymReservationsPanel() {
		view.setEnabled(true);
		openView.setEnabled(false);
		closeView.setEnabled(true);
		logout.setEnabled(true);
		clickChange.setEnabled(true);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new CompanyReservationsPanel();
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setUpdatePremiumPanel() {
		view.setEnabled(true);
		openView.setEnabled(false);
		closeView.setEnabled(true);
		logout.setEnabled(true);
		clickChange.setEnabled(false);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new UpdatePremiumPanel(this);
		this.add(mainPanel);
		this.revalidate();
	}
	
	public void setUpdateCompanyPanel() {
		view.setEnabled(true);
		openView.setEnabled(false);
		closeView.setEnabled(true);
		logout.setEnabled(true);
		clickChange.setEnabled(false);
		clickLogout.setEnabled(true);
		this.remove(mainPanel);
		mainPanel = new UpdateCompanyPanel(this);
		this.add(mainPanel);
		this.revalidate();
	}

	public static void main(String[] args) {
		(MainFrame.getInstance()).setVisible(true);
		System.out.println(new java.sql.Date((new java.util.Date()).getTime()));
	}

}
