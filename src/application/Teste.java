package application;

import java.util.ArrayList;
import java.util.List;

import gui.util.Utils;
import model.dao.ClientDao;
import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.dao.OwnerDao;
import model.dao.UsersDao;
import model.dao.impl.ClientDaoJDBC;
import model.entities.Client;
import model.entities.Users;

public class Teste {

	public static void main(String[] args) {
		
		ClientDao clientDAO = DaoFactory.createClientDao();
		ClientTypeDao ctDAO = DaoFactory.createClientTypeDao();
		OwnerDao oDao = DaoFactory.createOwnerDao();
		
		
		System.out.println("Teste Search by name");
		
		if ( oDao.searchByName("Pole da Silva Sauro")) {
			System.out.println("Achou");
		} else {
			System.out.println("nao achou");
		}
		
		
		
			
		
		
		}
		
		
		
		
		
	}


