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


	
	public Integer getIdClient() {
		return idClient;
	}



	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}



	public String getClientName() {
		return clientName;
	}



	public void setClientName(String clientName) {
		this.clientName = clientName;
	}



	public String getClientHostname() {
		return clientHostname;
	}



	public void setClientHostname(String clientHostname) {
		this.clientHostname = clientHostname;
	}



	public Integer getIdType() {
		return idType;
	}



	public void setIdType(Integer idType) {
		this.idType = idType;
	}



	public Integer getIdOwner() {
		return idOwner;
	}



	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}



	public ClientType getClientType() {
		return clientType;
	}



	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}



	public Owner getOwner() {
		return owner;
	}



	public void setOwner(Owner owner) {
		this.owner = owner;
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
