package data;

import java.util.ArrayList;
import java.util.List;

import db.ComponentType;
import db.User;

public class MainData {

	public static enum UserType {PREMIUM, COMPANY};
	
	public static User user;
	public static UserType userType;
	
	public static int selectedCompanyId;
	
	public static List<ComponentType> componentTypes = new ArrayList<>();
}
