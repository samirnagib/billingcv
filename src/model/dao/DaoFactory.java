package model.dao;

import db.DB;
import model.dao.impl.*;

public class DaoFactory {

	public static BillTagsDao createBillTagsDao() {
		return new BillTagsDaoJDBC();
	}
	
	public static ClientDao createClientDao() {
		return new ClientDaoJDBC();
	}
	
	public static ClientTypeDao createClientTypeDao() {
		return	new ClientTypeDaoJDBC();
	}
	
	public static InputBillDao createInputBillDao() {
		return new InputBillDaoJDBC();
	}

	public static OwnerDao createOwnerDao() {
		return new OwnerDaoJDBC();
	}

	public static UsersDao createUsersDao() {
		return new UsersDaoJDBC(DB.getConnection());
	}

	
}