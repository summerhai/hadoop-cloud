package counter;

import java.io.Serializable;

public class Employee implements Serializable{
	private static final long serialVersionUID = -7622136111218732162L;
	private String name;
	private String address;
	
	public Employee(String name, String address) {
		super();
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
