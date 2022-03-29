package model.dao;

import java.util.List;

import model.entities.ClientType;

public interface ClientTypeDao {

	void insert(ClientType obj);
	void update(ClientType obj);
	void deleteById(Integer id);
	ClientType findById(Integer id);
	List<ClientType> findAll();
	ClientType findByName(String clientTypeName);
	boolean searchByName(String clientTypeName);
	
	
}
