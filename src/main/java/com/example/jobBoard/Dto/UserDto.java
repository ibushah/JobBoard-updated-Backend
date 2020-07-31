package com.example.jobBoard.Dto;

import java.io.Serializable;

public class UserDto implements Serializable {

	public Long id;
    private String name;
    private String email;
    private String password;
    private Boolean active ;
    private String userType;
	private String legalCompanyName;
	public Boolean profileActive;


	public UserDto() {
	}

	public UserDto(Long id, String name, String email, String password, Boolean active, String userType, String legalCompanyName, Boolean profileActive) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.active = active;
		this.userType = userType;
		this.legalCompanyName = legalCompanyName;
		this.profileActive = profileActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLegalCompanyName() {
		return legalCompanyName;
	}

	public void setLegalCompanyName(String legalCompanyName) {
		this.legalCompanyName = legalCompanyName;
	}

	public Boolean getProfileActive() {
		return profileActive;
	}

	public void setProfileActive(Boolean profileActive) {
		this.profileActive = profileActive;
	}

	public UserDto(Long id, String name, String email, String userType, Boolean profileActive) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.userType = userType;
		this.profileActive = profileActive;
	}
}
