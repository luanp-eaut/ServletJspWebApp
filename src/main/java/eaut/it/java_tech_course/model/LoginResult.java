package eaut.it.java_tech_course.model;

import java.util.List;

public class LoginResult {
	private String userName;
	private List<String> roles;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
