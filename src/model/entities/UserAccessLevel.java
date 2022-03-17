package model.entities;

import java.io.Serializable;

public class UserAccessLevel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idLevel;
	private String LevelAccess;
	
	public UserAccessLevel() {
	}

	public UserAccessLevel(Integer idLevel, String levelAccess) {
		this.idLevel = idLevel;
		LevelAccess = levelAccess;
	}

	public Integer getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(Integer idLevel) {
		this.idLevel = idLevel;
	}

	public String getLevelAccess() {
		return LevelAccess;
	}

	public void setLevelAccess(String levelAccess) {
		LevelAccess = levelAccess;
	}

	@Override
	public String toString() {
		return "" + idLevel + " - " + LevelAccess;
	}

	
	
}
