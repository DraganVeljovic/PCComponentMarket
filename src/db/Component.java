package db;

import gui.DisplayItem;

public class Component implements DisplayItem {
	
	private int id;
	private int typeId;
	private String name;
	private String description;
	private int amount;
	private int price;
	
	public Component() {
		id = typeId = 0;
		name = description = "";
	}
	
	public Component(int id, int typeId, String name, String description) {
		super();
		this.id = id;
		this.typeId = typeId;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
}
