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
import model.dao.UsersDao;
import model.entities.Users;

public class UsersDaoJDBC implements UsersDao {

	private Connection conn;
	
	public UsersDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Users obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO users ( userlogin, userpasswd, userFullName, userEmail, userLevelAccess) VALUES ( ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getUserLogin());
			st.setString(2,obj.getUserPasswd());
			st.setString(3,obj.getUserFullName());
			st.setString(4,obj.getUserEmail());
			st.setInt(5,obj.getUserLevelAccess());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setUserId(id);
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
	public void update(Users obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE users SET userlogin = ?, userpasswd = ?, userFullName = ?, userEmail = ?, userLevelAccess = ? WHERE idUser = ?");
			
			st.setString(1, obj.getUserLogin());
			st.setString(2, obj.getUserPasswd());
			st.setString(3, obj.getUserFullName());
			st.setString(4, obj.getUserEmail());
			st.setInt(5, obj.getUserLevelAccess());
			st.setInt(6, obj.getUserId());
			
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
			st = conn.prepareStatement("DELETE FROM users WHERE idUser = ?");
			
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
	public Users findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT users.* FROM users WHERE idUser = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Users user = instantiateUsers(rs);
				
				return user;
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

	private Users instantiateUsers(ResultSet rs) throws SQLException {
		Users user = new Users();
		user.setUserId(rs.getInt("idUser"));
		user.setUserLogin(rs.getString("userlogin"));
		user.setUserPasswd(rs.getString("userpasswd"));
		user.setUserFullName(rs.getString("userFullName"));
		user.setUserEmail(rs.getString("userEmail"));
		user.setUserLevelAccess(rs.getInt("userLevelAccess"));
		return user;
	}

	@Override
	public Users findByName(String name) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "SELECT users.* FROM users WHERE userFullName like ? ESCAPE '!'";
		try {
			name = name
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			st = conn.prepareStatement( query );
			st.setString(1, name +"%");
			rs = st.executeQuery();
			if (rs.next()) {
				Users user = instantiateUsers(rs);
				
				return user;
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
	public List<Users> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT users.* FROM users");
			rs = st.executeQuery();
			List<Users> list = new ArrayList<>();
			while (rs.next()) {
				Users user = instantiateUsers(rs);
				list.add(user);
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

}
