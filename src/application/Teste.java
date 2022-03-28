package application;

import java.util.ArrayList;
import java.util.List;

import gui.util.Utils;
import model.dao.ClientDao;
import model.dao.DaoFactory;
import model.dao.UsersDao;
import model.dao.impl.ClientDaoJDBC;
import model.entities.Client;
import model.entities.Users;

public class Teste {

	public static void main(String[] args) {
		
		ClientDao clientDAO = DaoFactory.createClientDao();
		
		System.out.println("Teste FindAll");
		Client client = clientDAO.findByName("SQLPROD02");
		
			
		System.out.println(client);
		
		}
		
		
		
		
		
	}


