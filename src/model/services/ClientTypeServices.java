package model.services;

import java.util.List;

import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.entities.ClientType;

public class ClientTypeServices {
	
	private ClientTypeDao dao = DaoFactory.createClientTypeDao();
	
	public List<ClientType> findAll() {
		return dao.findAll();
	}
	
	public void saverOrUpdate(ClientType obj) {
		if (obj.getIdType() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(ClientType obj) {
		dao.deleteById(obj.getIdType());
	}
}
