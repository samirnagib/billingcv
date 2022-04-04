package model.dao;

import java.util.List;

import model.entities.BillTags;

public interface BillTagsDao {

	void insert(BillTags obj);
	void update(BillTags obj);
	void deleteById(Integer id);
	BillTags findById(Integer id);
	List<BillTags> findAll();
	BillTags findByName(String billtagName);
	
}
