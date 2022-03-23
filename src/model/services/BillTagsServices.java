package model.services;

import java.util.List;

import model.dao.BillTagsDao;
import model.dao.DaoFactory;
import model.entities.BillTags;

public class BillTagsServices {

	private BillTagsDao dao = DaoFactory.createBillTagsDao();
	
	public List<BillTags> findAll() {
		return dao.findAll();
	}
	
	public void saverOrUpdate(BillTags obj) {
		if (obj.getIdbillTag() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(BillTags obj) {
		dao.deleteById(obj.getIdbillTag());
	}
}
