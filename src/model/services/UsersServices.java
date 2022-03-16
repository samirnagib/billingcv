package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Users;

public class UsersServices {
	
	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		list.add(new Users(1,"admin","admin1","Administrador","admin@email.com",1));
		list.add(new Users(2,"oper","@oper1","Operador do sistema","oper@email.com",2));
		list.add(new Users(3,"viwer","V$datavase","Apenas Vizualizador","vizu@email.com",3));
		return list;
	}
	
}
