package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DB;
import db.DbException;


import model.dao.InputBillDao;
import model.entities.BillTags;
import model.entities.Client;
import model.entities.ClientType;
import model.entities.InputBill;
import model.entities.Owner;

public class InputBillDaoJDBC implements InputBillDao {

	private Connection conn;
	
	public InputBillDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	
	private InputBill instantiateBill(ResultSet rs, BillTags billTags, Client client, ClientType clientType, Owner owner) throws SQLException {
		InputBill fatura = new InputBill();
		fatura.setId_billTag(rs.getInt("id_billTag"));
		fatura.setIb_ano_mes(rs.getString("ib_ano_mes"));
		fatura.setBilltag(billTags);
		fatura.setClient(client);
		fatura.setCv_agent(rs.getString("cv_agent"));
		fatura.setCv_instance(rs.getString("cv_instance"));
		fatura.setCv_backupset(rs.getString("cv_backupset"));
		fatura.setCv_subclient(rs.getString("cv_subclient"));
		fatura.setCv_storagepolicy(rs.getString("cv_storagepolicy"));
		fatura.setCv_copyname(rs.getString("cv_copyname"));
		fatura.setCv_febackupsize(rs.getDouble("cv_febackupsize"));
		fatura.setCv_fearchivesize(rs.getDouble("cv_fearchivesize"));
		fatura.setCv_primaryappsize(rs.getDouble("cv_primaryappsize"));
		fatura.setCv_protectedappsize(rs.getDouble("cv_protectedappsize"));
		fatura.setCv_mediasize(rs.getDouble("cv_mediasize"));
		fatura.setIb_taxcalculated(rs.getDouble("ib_taxcalculated"));
		fatura.setClientType(clientType);
		fatura.setOwner(owner);
		
		return fatura;
	}
	
	private BillTags instantiatebillTags(ResultSet rs) throws SQLException {
		BillTags billTags = new BillTags();
		billTags.setIdbillTag(rs.getInt("idbillTag"));
		billTags.setBilltagName(rs.getString("billtagName"));
		billTags.setBillPriceTB(rs.getDouble("billPriceTB"));
		return billTags;
	}
	
	private Owner instantiateOwner(ResultSet rs) throws SQLException {
		Owner owner = new Owner();
		owner.setIdOwner(rs.getInt("idOwner"));
		owner.setOwName(rs.getString("owName"));
		owner.setOwProjectArea(rs.getString("owProjectArea"));
		owner.setOwAR(rs.getString("owAR"));
		
		return owner;
	}

	private ClientType instatiateClientType(ResultSet rs) throws SQLException {
		ClientType clientType = new ClientType();
		clientType.setIdType(rs.getInt("idType"));
		clientType.setTypeName(rs.getString("typeName"));
		return clientType;
	}
	
	private Client instatiateClient(ResultSet rs, ClientType clientType, Owner owner) throws SQLException {
		Client client = new Client();
		client.setIdClient(rs.getInt("idClient"));
		client.setClientName(rs.getString("clientName"));
		client.setClientHostname(rs.getString("clientHostname"));
		client.setOwner(owner);
		client.setClientType(clientType);
		return client;
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement("SELECT inputBill.idInputBill, inputBill.id_billTag, inputBill.ib_ano_mes, inputBill.id_client,clientes.idClient, clientes.clientName, clientes.clientHostname, billTags.idbillTag, billTags.billtagName, billTags.billPriceTB, inputBill.cv_agent, inputBill.cv_instance, inputBill.cv_backupset,inputBill.cv_subclient, inputBill.cv_storagepolicy, inputBill.cv_copyname, inputBill.cv_febackupsize, inputBill.cv_fearchivesize, inputBill.cv_primaryappsize, inputBill.cv_protectedappsize, inputBill.cv_mediasize, inputBill.ib_taxcalculated, clientType.idType,clientType.typeName, owner.idOwner, owner.owName, owner.owProjectArea, owner.owAR\r\n"  
					+ "from inputBill INNER JOIN billTags ON  inputBill.id_billTag = billTags.idbillTag INNER JOIN clientes ON inputBill.id_client = clientes.idClient INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner WHERE idInputBill = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				BillTags bt = instantiatebillTags(rs);
				ClientType ct = instatiateClientType(rs);
				Owner ow = instantiateOwner(rs);
				Client cl = instatiateClient(rs, ct, ow);
				InputBill ib = instantiateBill(rs, bt, cl, ct, ow);
				return ib;
			}
		return null;
		
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<InputBill> findByCompetencia(String competencia) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement("SELECT inputBill.idInputBill, inputBill.id_billTag, inputBill.ib_ano_mes, inputBill.id_client,clientes.idClient, clientes.clientName, clientes.clientHostname, billTags.idbillTag, billTags.billtagName, billTags.billPriceTB, inputBill.cv_agent, inputBill.cv_instance, inputBill.cv_backupset,inputBill.cv_subclient, inputBill.cv_storagepolicy, inputBill.cv_copyname, inputBill.cv_febackupsize, inputBill.cv_fearchivesize, inputBill.cv_primaryappsize, inputBill.cv_protectedappsize, inputBill.cv_mediasize, inputBill.ib_taxcalculated, clientType.idType,clientType.typeName, owner.idOwner, owner.owName, owner.owProjectArea, owner.owAR\r\n"  
					+ "from inputBill INNER JOIN billTags ON  inputBill.id_billTag = billTags.idbillTag INNER JOIN clientes ON inputBill.id_client = clientes.idClient INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner\r\n"
					+ "WHERE inputBill.ib_ano_mes = ?");
			st.setString(1, competencia);
			rs = st.executeQuery();
			
			List<InputBill> list = new ArrayList<>();
			Map<Integer, BillTags> mapBT = new HashMap<>();
			Map<Integer, Client> mapClient = new HashMap<>();
			Map<Integer, ClientType> mapCT = new HashMap<>();
			Map<Integer, Owner> mapO = new HashMap<>();
			
			while (rs.next()) {
				BillTags bt = mapBT.get(rs.getInt("idbillTag"));
				if (bt == null) {
					bt = instantiatebillTags(rs);
					mapBT.put(rs.getInt("idbillTag"), bt);
				}
				
				
				ClientType ct = mapCT.get(rs.getInt("idType"));
				if (ct == null) {
					ct = instatiateClientType(rs);
					mapCT.put(rs.getInt("idType"), ct);
				}
				
				Owner ow = mapO.get(rs.getInt("idOwner"));
				if (ow == null ) {
					ow = instantiateOwner(rs);
					mapO.put(rs.getInt("idOwner"), ow);
				}

				Client cl = mapClient.get(rs.getInt("idClient"));
				if ( cl == null ) {
					cl = instatiateClient(rs, ct, ow);
					mapClient.put(rs.getInt("idClient"), cl);
				}
				
				InputBill obj = instantiateBill(rs, bt, cl, ct, ow);
				list.add(obj);
			
			}
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
			
	}

	
	
	
	@Override
	public List<InputBill> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conn.prepareStatement("SELECT inputBill.idInputBill, inputBill.id_billTag, inputBill.ib_ano_mes, inputBill.id_client,clientes.idClient, clientes.clientName, clientes.clientHostname, billTags.idbillTag, billTags.billtagName, billTags.billPriceTB, inputBill.cv_agent, inputBill.cv_instance, inputBill.cv_backupset,inputBill.cv_subclient, inputBill.cv_storagepolicy, inputBill.cv_copyname, inputBill.cv_febackupsize, inputBill.cv_fearchivesize, inputBill.cv_primaryappsize, inputBill.cv_protectedappsize, inputBill.cv_mediasize, inputBill.ib_taxcalculated, clientType.idType,clientType.typeName, owner.idOwner, owner.owName, owner.owProjectArea, owner.owAR\r\n"  
					+ "from inputBill INNER JOIN billTags ON  inputBill.id_billTag = billTags.idbillTag INNER JOIN clientes ON inputBill.id_client = clientes.idClient INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner");
			
			rs = st.executeQuery();
			
			List<InputBill> list = new ArrayList<>();
			Map<Integer, BillTags> mapBT = new HashMap<>();
			Map<Integer, Client> mapClient = new HashMap<>();
			Map<Integer, ClientType> mapCT = new HashMap<>();
			Map<Integer, Owner> mapO = new HashMap<>();
			
			while (rs.next()) {
				BillTags bt = mapBT.get(rs.getInt("idbillTag"));
				if (bt == null) {
					bt = instantiatebillTags(rs);
					mapBT.put(rs.getInt("idbillTag"), bt);
				}
				
				
				ClientType ct = mapCT.get(rs.getInt("idType"));
				if (ct == null) {
					ct = instatiateClientType(rs);
					mapCT.put(rs.getInt("idType"), ct);
				}
				
				Owner ow = mapO.get(rs.getInt("idOwner"));
				if (ow == null ) {
					ow = instantiateOwner(rs);
					mapO.put(rs.getInt("idOwner"), ow);
				}

				Client cl = mapClient.get(rs.getInt("idClient"));
				if ( cl == null ) {
					cl = instatiateClient(rs, ct, ow);
					mapClient.put(rs.getInt("idClient"), cl);
				}
				
				InputBill obj = instantiateBill(rs, bt, cl, ct, ow);
				list.add(obj);
			
			}
			return list;
			
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

}
