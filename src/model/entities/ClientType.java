package model.entities;

import java.io.Serializable;

public class ClientType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idType;
	private String typeName;
	
	public ClientType() {
	}

	public ClientType(Integer idType, String typeName) {
		this.idType = idType;
		this.typeName = typeName;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
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
		ClientType other = (ClientType) obj;
		if (idType == null) {
			if (other.idType != null)
				return false;
		} else if (!idType.equals(other.idType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ClientType [idType=" + idType + ", typeName=" + typeName + "]";
	}
	
	
	

}
