package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ClientTypeDao;
import model.entities.ClientType;

public class ClientTypeDaoJDBC implements ClientTypeDao {

	private Connection conn;
	
	public ClientTypeDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(ClientType obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO clientType ( typeName ) VALUES ( ? )", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getTypeName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdType(id);
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
	public void update(ClientType obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE clientType SET typeName = ? WHERE idType = ?");
			
			st.setString(1, obj.getTypeName());
			st.setInt(6, obj.getIdType());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}

		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM clientType WHERE idType = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}	
		
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
