package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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


import model.dao.InputBillDao;
import model.entities.InputBill;

public class InputBillDaoJDBC implements InputBillDao {

	private Connection conn;
	
	public InputBillDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public void insert(InputBill obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO inputBill ( ib_ano_mes, id_billTag, id_client, cv_agent, cv_instance, cv_backupset, cv_subclient, cv_storagepolicy, cv_copyname, cv_febackupsize, cv_fearchivesize, cv_primaryappsize, cv_protectedappsize, cv_mediasize, ib_taxcalculated) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getIb_ano_mes());
			st.setInt(2, obj.getBilltag().getIdbillTag());
			st.setInt(3, obj.getClient().getIdClient());
			st.setString(4, obj.getCv_agent());
			st.setString(5, obj.getCv_instance());
			st.setString(6, obj.getCv_backupset());
			st.setString(7, obj.getCv_subclient());
			st.setString(8, obj.getCv_storagepolicy());
			st.setString(9, obj.getCv_copyname());
			st.setDouble(10, obj.getCv_febackupsize());
			st.setDouble(11, obj.getCv_fearchivesize());
			st.setDouble(12, obj.getCv_primaryappsize());
			st.setDouble(13, obj.getCv_protectedappsize());
			st.setDouble(14, obj.getCv_mediasize());
			st.setDouble(15, obj.getIb_taxcalculated());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdInputBill(id);;
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
	public void update(InputBill obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE inputBill SET ib_ano_mes = ?, id_billTag = ?, id_client = ?, cv_agent = ?, cv_instance = ?, cv_backupset = ?, cv_subclient = ?, cv_storagepolicy = ?, cv_copyname = ?, cv_febackupsize = ?, cv_fearchivesize = ?, cv_primaryappsize = ?, cv_protectedappsize = ?, cv_mediasize = ?, ib_taxcalculated = ? WHERE idInputBill = ?");
			
			st.setString(1, obj.getIb_ano_mes());
			st.setInt(2, obj.getBilltag().getIdbillTag());
			st.setInt(3, obj.getClient().getIdClient());
			st.setString(4, obj.getCv_agent());
			st.setString(5, obj.getCv_instance());
			st.setString(6, obj.getCv_backupset());
			st.setString(7, obj.getCv_subclient());
			st.setString(8, obj.getCv_storagepolicy());
			st.setString(9, obj.getCv_copyname());
			st.setDouble(10, obj.getCv_febackupsize());
			st.setDouble(11, obj.getCv_fearchivesize());
			st.setDouble(12, obj.getCv_primaryappsize());
			st.setDouble(13, obj.getCv_protectedappsize());
			st.setDouble(14, obj.getCv_mediasize());
			st.setDouble(15, obj.getIb_taxcalculated());
			
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
			st = conn.prepareStatement("DELETE FROM inputBill WHERE idInputBill = ?");
			
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
	public void deleteByMesAno(String competencia) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM inputBill WHERE ib_ano_mes = ?");
			
			st.setString(1, competencia);
			
			st.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			
		}
		
	}

	@Override
	public InputBill findById(Integer id) {
		
		return null;
	}

	@Override
	public InputBill findByCompetencia(String competencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InputBill> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement("SELECT inputBill.idInputBill, inputBill.ib_ano_mes, clientes.clientName, clientes.clientHostname, billTags.billtagName, inputBill.cv_agent, inputBill.cv_instance, inputBill.cv_backupset,inputBill.cv_subclient, inputBill.cv_storagepolicy, inputBill.cv_copyname, inputBill.cv_febackupsize, inputBill.cv_fearchivesize, inputBill.cv_primaryappsize, inputBill.cv_protectedappsize, inputBill.cv_mediasize, inputBill.ib_taxcalculated, clientType.typeName, owner.owName, owner.owProjectArea, owner.owAR\r\n"  
					+ "from inputBill INNER JOIN billTags ON  inputBill.id_billTag = billTags.idbillTag INNER JOIN clientes ON inputBill.id_client = clientes.idClient INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner");
			
			rs = st.executeQuery();
			
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

}
