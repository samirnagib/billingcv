package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.InputBillDao;
import model.entities.InputBill;

public class InputBillServices {
	
	private InputBillDao dao = DaoFactory.createInputBillDao();
	
	public List<InputBill> findAll() {
		return dao.findAll();
	}
	
	public List<InputBill> findByCompetencia(String competencia) {
		return dao.findByCompetencia(competencia);
	}
	
	public List<InputBill> findByCompetenciaAndClient(String competencia, String Server) {
		return dao.findByCompetenciaAndClient(competencia, Server);
	}
	
	public void saveORupdate(InputBill obj) {
		if (obj.getId_billTag() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
	
	public void remove(InputBill obj) {
		dao.deleteById(obj.getId_billTag());
	}
	
	public void removeCompetencia(String competencia) {
		dao.deleteByMesAno(competencia);
	}
	
	public  List<InputBill> listCompetencia(){
		return dao.listCpts();
	}
	
	public List<InputBill> listDistinctClient() {
		return dao.listDistinctClient();
	}
	

}
