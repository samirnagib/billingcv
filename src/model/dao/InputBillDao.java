package model.dao;

import java.util.List;

import model.entities.InputBill;

public interface InputBillDao {

	void insert(InputBill obj);
	void update(InputBill obj);
	void deleteById(Integer id);
	void deleteByMesAno(String competencia);
	InputBill findById(Integer id);
	
	List<InputBill> findAll();

}
