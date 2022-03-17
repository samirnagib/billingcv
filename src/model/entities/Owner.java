package model.entities;

import java.io.Serializable;

public class Owner implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idOwner;
	private String owName;
	private String owEmail1;
	private String owEmail2;
	private String owProjectArea;
	private String owAR;
	
	public Owner() {
		
	}

	public Owner(Integer idOwner, String owName, String owEmail1, String owEmail2, String owProjectArea, String owAR) {
		this.idOwner = idOwner;
		this.owName = owName;
		this.owEmail1 = owEmail1;
		this.owEmail2 = owEmail2;
		this.owProjectArea = owProjectArea;
		this.owAR = owAR;
	}

	public Integer getIdOwner() {
		return idOwner;
	}

	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}

	public String getOwName() {
		return owName;
	}

	public void setOwName(String owName) {
		this.owName = owName;
	}

	public String getOwEmail1() {
		return owEmail1;
	}

	public void setOwEmail1(String owEmail1) {
		this.owEmail1 = owEmail1;
	}

	public String getOwEmail2() {
		return owEmail2;
	}

	public void setOwEmail2(String owEmail2) {
		this.owEmail2 = owEmail2;
	}

	public String getOwProjectArea() {
		return owProjectArea;
	}

	public void setOwProjectArea(String owProjectArea) {
		this.owProjectArea = owProjectArea;
	}

	public String getOwAR() {
		return owAR;
	}

	public void setOwAR(String owAR) {
		this.owAR = owAR;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOwner == null) ? 0 : idOwner.hashCode());
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
		Owner other = (Owner) obj;
		if (idOwner == null) {
			if (other.idOwner != null)
				return false;
		} else if (!idOwner.equals(other.idOwner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Owner [idOwner=" + idOwner + ", owName=" + owName + ", owEmail1=" + owEmail1 + ", owEmail2=" + owEmail2
				+ ", owProjectArea=" + owProjectArea + ", owAR=" + owAR + "]";
	};
	
	

}
