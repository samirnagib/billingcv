package model.dao;

import java.util.List;

import model.entities.Client;
import model.entities.ClientType;
import model.entities.Owner;

public interface ClientDao {

	void insert(Client obj);
	void update(Client obj);
	void deleteById(Integer id);
	Client findById(Integer id);
	Client findByName(String name);
	List<Client> findAll();
	List<Client> findByClientType(ClientType clientType);
	List<Client> findByOwner(Owner owner);
	
	
}
