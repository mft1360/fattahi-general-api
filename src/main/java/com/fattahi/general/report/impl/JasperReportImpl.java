package com.fattahi.general.report.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsPdfView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fattahi.general.genum.ReportType;
import com.fattahi.general.report.IJasperReport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component
public class JasperReportImpl implements IJasperReport {

	@Autowired
	private ApplicationContext appContext;

	@Override
	public ResponseEntity<byte[]> getReport(Object dataSource, String fileName, ReportType reportType) {
		return getReport(dataSource, fileName, null, false, reportType);
	}

	@Override
	public ResponseEntity<byte[]> getReport(Object dataSource, String fileName, Map<String, Object> params,
			ReportType reportType) {
		return getReport(dataSource, fileName, params, false, reportType);
	}

	@Override
	public ResponseEntity<byte[]> getReportByJson(Object dataSource, String fileName, ReportType reportType) {
		return getReport(dataSource, fileName, null, true, reportType);
	}

	@Override
	public ResponseEntity<byte[]> getReportByJson(Object dataSource, String fileName, Map<String, Object> params,
			ReportType reportType) {
		return getReport(dataSource, fileName, params, true, reportType);
	}

	@Override
	public ModelAndView getReportModelAndView(Object dataSource, String fileName, ReportType reportType) {
		JasperReportsPdfView view = new JasperReportsPdfView();
		view.setUrl("classpath:/report/" + fileName + ".jrxml");
		view.setApplicationContext(appContext);
		Map<String, Object> params = new HashMap<>();
		params.put("datasource", dataSource);
		return new ModelAndView(view, params);
	}

	@SuppressWarnings({ "resource", "unchecked", "rawtypes" })
	private ResponseEntity<byte[]> getReport(Object dataSource, String fileName, Map<String, Object> params,
			boolean json, ReportType reportType) {
		byte[] array = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			Resource resource = new ClassPathXmlApplicationContext()
					.getResource("classpath:/report/" + fileName + ".jrxml");
			JasperDesign jasperDesign = JRXmlLoader.load(resource.getInputStream());
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			if (params == null) {
				params = new HashMap<>();
			}
			params.put("dateDay", new Date());
			JasperPrint jasperPrint = null;
			if (json) {
				String jsonData = null;
				ObjectMapper mapper = new ObjectMapper();
				jsonData = mapper.writeValueAsString(dataSource);
				InputStream is = new ByteArrayInputStream(jsonData.getBytes("UTF-8"));
				JsonDataSource ds = new JsonDataSource(is);
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, params,
						new JRBeanCollectionDataSource((Collection<?>) dataSource));
			}
			Exporter exporter = null;
			boolean html = false;
			HttpHeaders headers = new HttpHeaders();
			switch (reportType) {
			case PDF: {
				exporter = new JRPdfExporter();
				headers.setContentType(MediaType.APPLICATION_PDF);
				break;
			}
			case XLSX: {
				exporter = new JRXlsxExporter();
				headers.setContentType(
						new MediaType("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
				headers.set("Content-Disposition", "attachment; filename=test.xls");
				break;
			}
			case HTML: {
				exporter = new HtmlExporter();
				exporter.setExporterOutput(new SimpleHtmlExporterOutput(out));
				html = true;
				headers.set("Content-disposition", "attachment; filename=report.html");
				break;
			}
			case CSV: {
				exporter = new JRCsvExporter();
				headers.setContentType(MediaType.TEXT_PLAIN);
				headers.set("Content-disposition", "attachment; filename=report.csv");
				break;
			}
			case XML: {
				exporter = new JRXmlExporter();
				headers.set("Content-disposition", "attachment; filename=report.xml");
				break;
			}
			default:
				throw new JRException("Unknown report format: ");
			}
			if (!html) {
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
			}
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.exportReport();
			array = out.toByteArray();
			headers.setCacheControl("no-cache");
			headers.setContentLength(array.length);
			return new ResponseEntity<byte[]>(array, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
