package reports;

import java.awt.Frame;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;


import db.DB;
import db.DbException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {
	
	private static Connection conn;
	private static String Path;
	
	public static void callReport(String reportName, String title, HashMap<String, Object> filter) {
		try {
			conn = DB.getConnection();
			Path = "D:\\hds\\Billing\\workspace\\billingcv\\src\\reports\\" + reportName + ".jasper";
			JasperPrint print = JasperFillManager.fillReport(Path, filter, conn);
			JasperViewer oia = new JasperViewer(print, false);
			oia.setVisible(true);
			oia.setExtendedState(Frame.MAXIMIZED_BOTH);
			oia.setTitle(title);
			
		} catch (Exception e) {
			e.getMessage();
		}
		
	}

}
