package application;

import java.util.ArrayList;
import java.util.List;

import gui.util.Utils;
import model.dao.ClientDao;
import model.dao.ClientTypeDao;
import model.dao.DaoFactory;
import model.dao.UsersDao;
import model.dao.impl.ClientDaoJDBC;
import model.entities.Client;
import model.entities.Users;

public class Teste {

	public static void main(String[] args) {
		
		ClientDao clientDAO = DaoFactory.createClientDao();
		ClientTypeDao ctDAO = DaoFactory.createClientTypeDao();
		
		
		System.out.println("Teste Search by name");
		
		if ( ctDAO.searchByName("Servidor Físico")) {
			System.out.println("Achou");
		} else {
			System.out.println("nao achou");
		}
		
		
		
			
		
		
		}
		
		
		
		
		
	}


