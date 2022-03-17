package model.dao;

import java.util.List;

import model.entities.Owner;

public interface OwnweDao {

	void insert(Owner obj);
	void update(Owner obj);
	void deleteById(Integer id);
	Owner findById(Integer id);
	List<Owner> findAll();

}
