package com.mitosis.timesheet.util;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;


 

public class JasperUtil  extends BaseService {
	
	public  void RenderJr(String reportFilePath,Map parameters,String pdfFilePath) throws Exception{
		
	
		JasperDesign jasperDesign = JRXmlLoader.load(reportFilePath);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		getEntityManager().getTransaction().begin();
		Session session = (Session)getEntityManager().getDelegate();
		SessionImpl sessionImpl = (SessionImpl) session;
		Connection conn = sessionImpl.connection();
		//Connection conn = (Connection) getEntityManager().unwrap(java.sql.Connection.class);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
		
		
		
		new File(pdfFilePath).deleteOnExit();
		
		JasperExportManager.exportReportToPdfFile(jasperPrint,pdfFilePath);
		
		
		
		
	}

}
