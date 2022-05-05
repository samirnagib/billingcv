package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.OwnerDao;
import model.entities.Owner;

public class OwnerServices {
	
	private OwnerDao dao = DaoFactory.createOwnerDao();
	
	public List<Owner> findAll() {
		return dao.findAll();
	}
	
	public void saverOrUpdate(Owner obj) {
		if (obj.getIdOwner()==null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(Owner obj) {
		dao.deleteById(obj.getIdOwner());
	}
	
	public boolean searchByName(String onwerName) {
		
		return searchByName(onwerName);
	}
	
	public Owner findOwnerByName(String onwerName) {
		return dao.findByName(onwerName);
	}
	
}
