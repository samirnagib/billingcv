package model.dao.impl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.BillTagsDao;
import model.entities.BillTags;

public class BillTagsDaoJDBC implements BillTagsDao {

	private Connection conn;
	
	public BillTagsDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void insert(BillTags obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO billTags ( billtagName,billPriceTB) VALUES ( ?, ? )",Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getBilltagName());
			st.setDouble(2, obj.getBillPriceTB());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdbillTag(id);
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
	public void update(BillTags obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE billTags SET billtagName = ?, billPriceTB = ? WHERE idbillTag = ?");
			
			st.setString(1, obj.getBilltagName());
			st.setDouble(2,obj.getBillPriceTB());
			st.setInt(3, obj.getIdbillTag());
			
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
			st = conn.prepareStatement("DELETE FROM billTags WHERE idbillTag = ?");
			
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
	public BillTags findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT billTags.* FROM billTags WHERE idbillTag = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				BillTags billTags = instantiateBillTags(rs);
				
				return billTags;
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
	public List<BillTags> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT billTags.* FROM billTags");
			rs = st.executeQuery();
			List<BillTags> list = new ArrayList<>();
			while (rs.next()) {
				BillTags billtags = instantiateBillTags(rs);
				list.add(billtags);
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
	
	private BillTags instantiateBillTags(ResultSet rs) throws SQLException {
		
		BillTags billtags = new BillTags();
		
		billtags.setIdbillTag(rs.getInt("idbillTag"));
		billtags.setBilltagName(rs.getString("billtagName"));
		billtags.setBillPriceTB(rs.getDouble("billPriceTB"));
		
		return billtags;
	}


	@Override
	public BillTags findByName(String Name) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "select billTags.* from billTags where billtagName like ? ESCAPE '!'";
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
				BillTags bill = instantiateBillTags(rs);
				return bill;
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

}
