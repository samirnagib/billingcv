package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
			st = conn.prepareStatement("INSERT INTO ClientType ( typeName ) VALUES ( ? )", Statement.RETURN_GENERATED_KEYS);
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
			st = conn.prepareStatement("UPDATE ClientType SET typeName = ? WHERE idType = ?");
			
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
			st = conn.prepareStatement("DELETE FROM ClientType WHERE idType = ?");
			
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT ClientType.* FROM ClientType WHERE idType = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				ClientType ClientType = instantiateClientType(rs);
				
				return ClientType;
			}
			return null;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<ClientType> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT owner.* FROM owner");
			rs = st.executeQuery();
			List<ClientType> list = new ArrayList<>();
			while (rs.next()) {
				ClientType ClientType = instantiateClientType(rs);
				list.add(ClientType);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
	private ClientType instantiateClientType(ResultSet rs) throws SQLException {
		ClientType ClientType = new ClientType();
		ClientType.setIdType(rs.getInt("idType"));
		ClientType.setTypeName(rs.getString("typeName"));
		
		return ClientType;
	}
	

}
