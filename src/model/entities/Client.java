package model.entities;

import java.io.Serializable;

public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idClient;
	private String clientName;
	private String clientHostname;
	private Integer idType;
	private Integer idOwner;
	
	private ClientType clientType;
	private Owner owner;
	
	
	public Client() {
	}


	public Client(Integer idClient, String clientName, String clientHostname, Integer idType, Integer idOwner,
			ClientType clientType, Owner owner) {
		this.idClient = idClient;
		this.clientName = clientName;
		this.clientHostname = clientHostname;
		this.idType = idType;
		this.idOwner = idOwner;
		this.clientType = clientType;
		this.owner = owner;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idClient == null) ? 0 : idClient.hashCode());
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
		Client other = (Client) obj;
		if (idClient == null) {
			if (other.idClient != null)
				return false;
		} else if (!idClient.equals(other.idClient))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", clientName=" + clientName + ", clientHostname=" + clientHostname
				+ ", idType=" + idType + ", idOwner=" + idOwner + ", clientType=" + clientType + ", owner=" + owner
				+ "]";
	}

	
	

}
