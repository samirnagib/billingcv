package model.entities;

import java.io.Serializable;

public class Users implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String userLogin;
	private String userPasswd;
	private String userFullName;
	private String userEmail;
	private Integer userLevelAccess;
	
	
	
	public Users() {
			}

	public Users(Integer userId, String userLogin, String userPasswd, String userFullName, String userEmail,
			Integer userLevelAccess) {
		this.userId = userId;
		this.userLogin = userLogin;
		this.userPasswd = userPasswd;
		this.userFullName = userFullName;
		this.userEmail = userEmail;
		this.userLevelAccess = userLevelAccess;
		
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserLevelAccess() {
		return userLevelAccess;
	}

	public void setUserLevelAccess(Integer userLevelAccess) {
		this.userLevelAccess = userLevelAccess;
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userLogin=" + userLogin + ", userPasswd=" + userPasswd + ", userFullName="
				+ userFullName + ", userEmail=" + userEmail + ", userLevelAccess=" + userLevelAccess
				+  "]";
	}


	
}
