package model.dao.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.ClientTypeDao;
import model.entities.ClientType;

public class ClientTypeDaoJDBC implements ClientTypeDao {

	private Connection conn;
	
	public ClientTypeDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(ClientType obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ClientType obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClientType findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
