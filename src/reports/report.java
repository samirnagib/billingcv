package reports;

import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import db.DB;
import db.DbException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.view.JasperViewer;

public class report {
	
	private static Properties loadRepProperties() {
		try (FileInputStream fs = new FileInputStream("report.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	
	public static void callRelatorio(String reportName, String windowsTitle) {
		String appPath = null;
		String fileName = reportName + ".jrxml";
		String repFolder = null;
		String reportFullName;
		String pdfFolder = null;
		
		try {
			appPath = new File(".").getCanonicalPath();
			Properties data = loadRepProperties();
			repFolder = data.getProperty("repfolder");
			
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} finally {
			reportFullName = appPath + repFolder + fileName;
			System.out.println("app path: " + appPath);
			System.out.println("repfold : " + repFolder);
			System.out.println("filename: " + fileName);
			System.out.println("pdf name: " + reportName + ".pdf");
			System.out.println("-");
			
		}
		System.out.println(appPath + repFolder + reportName + ".pdf");
		
		try {
			JasperReport jasperReport =  JasperCompileManager.compileReport(reportFullName);
			Connection conn = DB.getConnection();
			Map<String, Object> hm = new HashMap<String,Object>();
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, conn);
			JasperViewer oia = new JasperViewer(print, false);
			
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(appPath + repFolder + reportName+".pdf");
	        exporter.setExporterOutput(exporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
			
			
			oia.setVisible(true);
			oia.setExtendedState(Frame.MAXIMIZED_BOTH);
			oia.setTitle("Vizualizar Impressão: " + windowsTitle);
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}	
	}
	

	public static void callRelatorioChargeBack(String reportName, String windowsTitle, String Query) {
		
		JRDesignQuery qry = new JRDesignQuery();
		
		String appPath = null;
		String fileName = reportName + ".jrxml";
		String repFolder = null;
		String reportFullName;
		String pdfFolder = null;
		
		try {
			appPath = new File(".").getCanonicalPath();
			Properties data = loadRepProperties();
			repFolder = data.getProperty("repfolder");
			
			
		} catch (IOException e1) {
			
			e1.printStackTrace();
		} finally {
			reportFullName = appPath + repFolder + fileName;
			System.out.println("app path: " + appPath);
			System.out.println("repfold : " + repFolder);
			System.out.println("filename: " + fileName);
			System.out.println("pdf name: " + reportName + ".pdf");
			System.out.println("-");
			
		}
		System.out.println(appPath + repFolder + reportName + ".pdf");
		
		try {
			Connection conn = DB.getConnection();
			
			qry.setText(Query);
			
			JasperDesign JD = JRXmlLoader.load(reportFullName);
			JD.setQuery(qry);
			JasperReport jasperReport = JasperCompileManager.compileReport(JD);
			
			Map<String, Object> hm = new HashMap<String,Object>();
			
			JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, conn);
			JasperViewer oia = new JasperViewer(print, false);
			
			JRPdfExporter exporter = new JRPdfExporter();
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(appPath + repFolder + reportName+".pdf");
	        exporter.setExporterOutput(exporterOutput);
			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	        exporter.setConfiguration(configuration);
	        exporter.exportReport();
			
			
			oia.setVisible(true);
			oia.setExtendedState(Frame.MAXIMIZED_BOTH);
			oia.setTitle("Vizualizar Impressão: " + windowsTitle);
			
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}	
	}


}
