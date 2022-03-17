package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UsersDao;
import model.entities.Users;

public class UsersServices {
	
	private UsersDao dao = DaoFactory.createUsersDao();
	
	public List<Users> findAll() {
		return dao.findAll();
	}
	
}
