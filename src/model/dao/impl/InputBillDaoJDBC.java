package model.dao.impl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.InputBillDao;
import model.entities.InputBill;

public class InputBillDaoJDBC implements InputBillDao {

	@Override
	public void insert(InputBill obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InputBill obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByMesAno(String competencia) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputBill findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputBill findByCompetencia(String competencia) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InputBill> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
