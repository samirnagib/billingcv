package application;

import java.util.ArrayList;
import java.util.List;

import gui.util.Utils;
import model.dao.ClientDao;
import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.dao.InputBillDao;
import model.dao.OwnerDao;
import model.dao.UsersDao;
import model.dao.impl.ClientDaoJDBC;
import model.entities.Client;
import model.entities.InputBill;
import model.entities.Users;

public class Teste {

	public static void main(String[] args) {
		
		InputBillDao ibDao = DaoFactory.createInputBillDao();	
		
		// System.out.println("Teste InputBill Find ALL");
		
				
		//List<InputBill> comp = ibDao.listCpts();
		
		List<InputBill> cli = ibDao.findAll();
		
		
	
//		System.out.println("Teste InputBill List Competencia");
//		for (InputBill ib : comp ) {
//			System.out.println(ib);
//			System.out.println("--------------------------------");
//		}


		System.out.println("Teste InputBill List Client");
		for (InputBill ib : cli ) {
			System.out.println(ib);
			System.out.println("--------------------------------");
		}
	
		
		
	}
		
		
		
		
		
	}


