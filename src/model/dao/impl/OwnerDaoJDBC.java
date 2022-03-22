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
import model.dao.OwnerDao;
import model.entities.Owner;


public class OwnerDaoJDBC implements OwnerDao {

	private Connection conn;
	
	public OwnerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void insert(Owner obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO owner ( owName, owEmail1, owEmail2, owProjectArea, owAR) VALUES ( ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,obj.getOwName());
			st.setString(2,obj.getOwEmail1());
			st.setString(3,obj.getOwEmail2());
			st.setString(4,obj.getOwProjectArea());
			st.setString(5,obj.getOwAR());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdOwner(id);
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
	public void update(Owner obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE owner SET owName = ?, owEmail1 = ?, owEmail2 = ?, owProjectArea = ?, owAR = ? WHERE idOwner = ?");
			
			st.setString(1, obj.getOwName());
			st.setString(2, obj.getOwEmail1());
			st.setString(3, obj.getOwEmail2());
			st.setString(4, obj.getOwProjectArea());
			st.setString(5, obj.getOwAR());
			st.setInt(6, obj.getIdOwner());
			
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
			st = conn.prepareStatement("DELETE FROM owner WHERE idOwner = ?");
			
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
	public Owner findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT owner.* FROM owner WHERE idOwner = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Owner owner = instantiateOwner(rs);
				
				return owner;
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
	public Owner findByName(String Name) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "SELECT owner.* FROM owner WHERE owName like ? ESCAPE '!'";
		try {
			Name = Name
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			st = conn.prepareStatement( query );
			st.setString(1, Name +"%");
			rs = st.executeQuery();
			if (rs.next()) {
				Owner owner = instantiateOwner(rs);
				
				return owner;
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
	public List<Owner> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT owner.* FROM owner");
			rs = st.executeQuery();
			List<Owner> list = new ArrayList<>();
			while (rs.next()) {
				Owner owner = instantiateOwner(rs);
				list.add(owner);
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

	private Owner instantiateOwner(ResultSet rs) throws SQLException {
		Owner owner = new Owner();
		owner.setIdOwner(rs.getInt("idOwner"));
		owner.setOwName(rs.getString("owName"));
		owner.setOwEmail1(rs.getString("owEmail1"));
		owner.setOwEmail2(rs.getString("owEmail2"));
		owner.setOwProjectArea(rs.getString("owProjectArea"));
		owner.setOwAR(rs.getString("owAR"));
		return owner;
	}
	
	
}
