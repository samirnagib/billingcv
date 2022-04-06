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
		
		System.out.println("Teste InputBill Find ALL");
		
		List<InputBill> list = ibDao.findAll();
		
		List<InputBill> cpt = ibDao.findByCompetencia("NOV 2021");
		
		for (InputBill ib : list ) {
//			System.out.println("________________");
//			System.out.println(ib.getIb_ano_mes());
//			System.out.println(ib.getClient().getClientName());
//			System.out.println(ib.getClient().getClientHostname());
//			System.out.println(ib.getBilltag().getBilltagName());
//			System.out.println(ib.getCv_agent());
//			System.out.println(ib.getCv_backupset());
//			System.out.println(ib.getCv_subclient());
//			System.out.println(ib.getCv_storagepolicy());
//			System.out.println(ib.getCv_copyname());
//			System.out.println(ib.getCv_febackupsize());
//			System.out.println(ib.getCv_fearchivesize());
//			System.out.println(ib.getCv_primaryappsize());
//			System.out.println(ib.getCv_protectedappsize());
//			System.out.println(ib.getCv_mediasize());
//			System.out.println(ib.getIb_taxcalculated());
//			System.out.println(ib.getClient().getClientType().getTypeName());
//			System.out.println(ib.getClient().getOwner().getOwName());
//			System.out.println(ib.getClient().getOwner().getOwProjectArea());
//			System.out.println("________________");
			System.out.println(ib);
			System.out.println("--------------------------------");
		}
		System.out.println("Teste InputBill Find Competencia");
		for (InputBill ib : cpt ) {
//			System.out.println("--------------------------------");
//			System.out.println(ib.getIb_ano_mes());
//			System.out.println(ib.getClient().getClientName());
//			System.out.println(ib.getClient().getClientHostname());
//			System.out.println(ib.getBilltag().getBilltagName());
//			System.out.println(ib.getCv_agent());
//			System.out.println(ib.getCv_backupset());
//			System.out.println(ib.getCv_subclient());
//			System.out.println(ib.getCv_storagepolicy());
//			System.out.println(ib.getCv_copyname());
//			System.out.println(ib.getCv_febackupsize());
//			System.out.println(ib.getCv_fearchivesize());
//			System.out.println(ib.getCv_primaryappsize());
//			System.out.println(ib.getCv_protectedappsize());
//			System.out.println(ib.getCv_mediasize());
//			System.out.println(ib.getIb_taxcalculated());
//			System.out.println(ib.getClient().getClientType().getTypeName());
//			System.out.println(ib.getClient().getOwner().getOwName());
//			System.out.println(ib.getClient().getOwner().getOwProjectArea());
//			System.out.println("--------------------------------");
			System.out.println(ib);
			System.out.println("--------------------------------");
		}
		
		
		}
		
		
		
		
		
	}


