package db;

public class Order {

	private int id;
	private int premiumId;
	private int componentId;
	private String componentName;
	private int companyId;
	private String companyName;
	private int statusId;
	private java.sql.Date date;
	private int amount;
	private int price;
	private int overallPrice;
	
	public Order() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPremiumId() {
		return premiumId;
	}

	public void setPremiumId(int premiumId) {
		this.premiumId = premiumId;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOverallPrice() {
		return overallPrice;
	}

	public void setOverallPrice(int overallPrice) {
		this.overallPrice = overallPrice;
	}
	
	
	
}
