package model.services;

import java.util.List;

import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.entities.Client;

public class ClientServices {
	
	private ClientDao dao = DaoFactory.createClientDao();
	
	public List<Client> findAll() {
		return dao.findAll();
	}
	
	public void saverOrUpdate(Client obj) {
		if (obj.getIdClient() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Client obj) {
		dao.deleteById(obj.getIdClient());
	}
}
