package model.dao;

import java.util.List;

import model.entities.InputBill;

public interface InputBillDao {

	void insert(InputBill obj);
	void update(InputBill obj);
	void deleteById(Integer id);
	void deleteByMesAno(String competencia);
	InputBill findById(Integer id);
	List<InputBill> findByCompetencia(String competencia);
	List<InputBill> findByCompetenciaAndClient(String competencia, String Server);
	List<InputBill> findAll();
	List<InputBill> listCpts();
	List<InputBill> listDistinctClient();
	InputBill findCPT(String competencia);
	
	
}
