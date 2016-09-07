package db;

import gui.DisplayItem;

import java.util.ArrayList;
import java.util.List;

public class Company extends User implements DisplayItem {
	
	protected String companyName;
	protected int companyPIB;
	protected int companyId;
	protected int componentTypes;
	protected int componentTypesPresent;
	protected int ownerId;
	
	protected List<Component> components = new ArrayList<Component>();
	
	public Company() {
		super();
		companyName = "";
		companyPIB = componentTypes = componentTypesPresent = 0;
	}
	
	public Company(User user) {
		super(user);
		companyName = "";
		companyPIB = componentTypes = componentTypesPresent = 0;
	}
	
	public Company(User user, String companyName, int companyPIB, int componentTypes,
			int componentTypesPresent, int ownerId,
			List<Component> components) {
		super(user);
		this.companyName = companyName;
		this.companyPIB = companyPIB;
		this.componentTypes = componentTypes;
		this.componentTypesPresent = componentTypesPresent;
		this.ownerId = ownerId;
		this.components = components;
	}

	public void addComponent(Component component) {
		components.add(component);
	}
	
	public void addComponents(List<Component> components) {
		this.components.addAll(components);
	}
	
	public List<Component> getComponents() {
		return components;
	}
	
	public Component getComponent(int id) {
		return components.get(id);
	}
	
	public Component getComponentByName(String name) {
		for (Component component : components)
			if (component.getName().compareTo(name) == 0) return component;
		
		return null;
	}
	
	public String getName() {
		return getCompanyName();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyPIB() {
		return companyPIB;
	}

	public void setCompanyPIB(int companyPIB) {
		this.companyPIB = companyPIB;
	}

	public int getComponentTypes() {
		return componentTypes;
	}

	public void setComponentTypes(int componentTypes) {
		this.componentTypes = componentTypes;
	}

	public int getComponentTypesPresent() {
		return componentTypesPresent;
	}

	public void setComponentTypesPresent(int componentTypesPresent) {
		this.componentTypesPresent = componentTypesPresent;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
