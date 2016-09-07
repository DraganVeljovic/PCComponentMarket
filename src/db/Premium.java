package db;


public class Premium extends User {

	private String name;
	private boolean gender; // true - MALE / false - FEMALE
	private int reservationsSuccess;
	private int reservationsFailure;
	
	public Premium() {
		super();
		name = "";
		gender = false;
		reservationsSuccess =  reservationsFailure = 0;
	}
	
	public Premium(User user) {
		super(user);
		name = "";
		gender = false;
		reservationsSuccess =  reservationsFailure = 0;
	}
	
	public Premium(String name, boolean gender, int reservations_success,
			int reservations_failure) {
		super();
		this.name = name;
		this.gender = gender;
		this.reservationsSuccess = reservations_success;
		this.reservationsFailure = reservations_failure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public int getReservationsSuccess() {
		return reservationsSuccess;
	}

	public void setReservationsSuccess(int reservations_success) {
		this.reservationsSuccess = reservations_success;
	}

	public int getReservationsFailure() {
		return reservationsFailure;
	}

	public void setReservationsFailure(int reservations_failure) {
		this.reservationsFailure = reservations_failure;
	}
	
	
}
