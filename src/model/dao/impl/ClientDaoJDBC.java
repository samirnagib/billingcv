package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.ClientDao;
import model.entities.Client;
import model.entities.ClientType;
import model.entities.Owner;

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
			st.setInt(3, obj.getClientType().getIdType());
			st.setInt(4, obj.getOwner().getIdOwner());
			
			// teste
			System.out.println(obj.getClientName());
			System.out.println(obj.getClientHostname());
			System.out.println(obj.getClientType().getIdType());
			System.out.println(obj.getOwner().getIdOwner());
			
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
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE clientes SET clientName = ?, clientHostname = ?, idType = ? , idOwner = ? WHERE idClient = ?");
			
			// INSERT INTO clientes ( clientName, clientHostname, idType, idOwner 
			
			st.setString(1, obj.getClientName());
			st.setString(2, obj.getClientHostname());
			st.setInt(3, obj.getClientType().getIdType());
			st.setInt(4, obj.getOwner().getIdOwner());
			st.setInt(5, obj.getIdClient());
			
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
			st = conn.prepareStatement("DELETE FROM clientes WHERE idClient = ?");
			
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
	public Client findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT clientes.*, clientType.typeName, owner.owName FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner WHERE clientes.idClient = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				
				ClientType clientType = instatiateClientType(rs);
				Owner owner = instantiateOwner(rs);
				Client client = instatiateClient(rs,clientType,owner);
				
				return client;
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

	private Client instatiateClient(ResultSet rs, ClientType clientType, Owner owner) throws SQLException {
		Client client = new Client();
		client.setIdClient(rs.getInt("idClient"));
		client.setClientName(rs.getString("clientName"));
		client.setClientHostname(rs.getString("clientHostname"));
		client.setOwner(owner);
		client.setClientType(clientType);
		return client;
	}

	private Owner instantiateOwner(ResultSet rs) throws SQLException {
		Owner owner = new Owner();
		owner.setIdOwner(rs.getInt("idOwner"));
		owner.setOwName(rs.getString("owName"));
		return owner;
	}

	private ClientType instatiateClientType(ResultSet rs) throws SQLException {
		ClientType clientType = new ClientType();
		clientType.setIdType(rs.getInt("idType"));
		clientType.setTypeName(rs.getString("typeName"));
		return clientType;
	}

	@Override
	public Client findByName(String Name) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "SELECT clientes.*, clientType.typeName, owner.owName FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner WHERE clientes.clientName LIKE ? ESCAPE '!'";
		
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
				
				ClientType clientType = instatiateClientType(rs);
				Owner owner = instantiateOwner(rs);
				Client client = instatiateClient(rs,clientType,owner);
				
				return client;
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
	public List<Client> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT clientes.*, clientType.typeName, owner.owName FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner ORDER BY clientes.clientName");
			
			rs = st.executeQuery();
			
			List<Client> list = new ArrayList<>();
			Map<Integer, Owner> mapOwner = new HashMap<>();
			Map<Integer, ClientType> mapClientType = new HashMap<>();
			
			while (rs.next()) {
				
				Owner ow = mapOwner.get(rs.getInt("idOwner"));
				if (ow == null) {
					ow = instantiateOwner(rs);
					mapOwner.put(rs.getInt("idOwner"), ow);
				}
				
				ClientType cType = mapClientType.get(rs.getInt("idType"));
				if (cType == null ) {
					cType = instatiateClientType(rs);
					mapClientType.put(rs.getInt("idType"), cType);
				}
				
				 
				Client obj = instatiateClient(rs, cType, ow);
				list.add(obj);
				
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

	

	@Override
	public List<Client> findByClientType(ClientType clientType) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT clientes.*, clientType.typeName, owner.owName FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner WHERE clientes.idType = ? ORDER BY clientes.clientName");
			st.setInt(1, clientType.getIdType());
			rs = st.executeQuery();
			
			List<Client> list = new ArrayList<>();
			Map<Integer, Owner> mapOwner = new HashMap<>();
			Map<Integer, ClientType> mapClientType = new HashMap<>();
			
			while (rs.next()) {
				
				Owner ow = mapOwner.get(rs.getInt("idOwner"));
				if (ow == null) {
					ow = instantiateOwner(rs);
					mapOwner.put(rs.getInt("idOwner"), ow);
				}
				
				ClientType cType = mapClientType.get(rs.getInt("idType"));
				if (cType == null ) {
					cType = instatiateClientType(rs);
					mapClientType.put(rs.getInt("idType"), cType);
				}
				
				 
				Client obj = instatiateClient(rs, cType, ow);
				list.add(obj);
				
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

	@Override
	public List<Client> findByOwner(Owner owner) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT clientes.*, clientType.typeName, owner.owName FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER join owner ON clientes.idOwner = owner.idOwner WHERE clientes.idOwner = ? ORDER BY clientes.clientName");
			st.setInt(1, owner.getIdOwner());
			rs = st.executeQuery();
			
			List<Client> list = new ArrayList<>();
			Map<Integer, Owner> mapOwner = new HashMap<>();
			Map<Integer, ClientType> mapClientType = new HashMap<>();
			
			while (rs.next()) {
				
				Owner ow = mapOwner.get(rs.getInt("idOwner"));
				if (ow == null) {
					ow = instantiateOwner(rs);
					mapOwner.put(rs.getInt("idOwner"), ow);
				}
				
				ClientType cType = mapClientType.get(rs.getInt("idType"));
				if (cType == null ) {
					cType = instatiateClientType(rs);
					mapClientType.put(rs.getInt("idType"), cType);
				}
				
				 
				Client obj = instatiateClient(rs, cType, ow);
				list.add(obj);
				
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

	@Override
	public boolean searchByName(String clientName) {
		
			String search;
			String compare;
			Client client = new Client();
			
			search = clientName;
			client = findByName(clientName);
			
			if (client != null) {
				
				compare = client.getClientName();
						
				if ( search.equalsIgnoreCase(compare)) {
					return true;
				}
			}
			return false;
		
	}

}
