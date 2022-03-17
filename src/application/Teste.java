package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UsersDao;
import model.entities.Users;

public class Teste {

	public static void main(String[] args) {
		
		UsersDao userDao = DaoFactory.createUsersDao();

		//  find by id
		System.out.println("find by id");
		Users user = userDao.findById(2);
		System.out.println(user);

		//find all
		System.out.println("find all");
		List<Users> list = userDao.findAll();
		for (Users obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("");
		System.out.println("find by name");
		user = userDao.findByName("Viz");
		System.out.println(user);
		
		System.out.println("");
		System.out.println("Delete");
		Users iUser = new Users();
		/* iUser.setUserLogin("jose");
		iUser.setUserPasswd("@ROsas22");
		iUser.setUserFullName("José Maria Martins");
		iUser.setUserEmail("josemaria.martins@email.com");
		iUser.setUserLevelAccess(2); 
		iUser.setUserId(6); */
		userDao.deleteById(6); 
		
		List<Users> list2 = userDao.findAll();
		for (Users obj : list2) {
			System.out.println(obj);
		}
		
	}

}
