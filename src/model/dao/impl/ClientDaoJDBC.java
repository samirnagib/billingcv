package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;

public class ClientDaoJDBC implements ClientDao {
	
	private Connection conn;
	
	public ClientDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Client obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO clientes ( clientName, clientHostname, idType, idOwner ) VALUES ( ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getClientName());
			st.setString(2,obj.getClientHostname());
			st.setInt(3, obj.getIdType());
			st.setInt(4, obj.getIdOwner());
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdClient(id);
					
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}
	

	@Override
	public void update(Client obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		
		
	}

	@Override
	public Client findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Client findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
