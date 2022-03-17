package model.dao;

import java.util.List;

import model.entities.Owner;

public interface OwnerDao {

	void insert(Owner obj);
	void update(Owner obj);
	void deleteById(Integer id);
	Owner findById(Integer id);
	Owner findByName(String Name);
	List<Owner> findAll();

}
