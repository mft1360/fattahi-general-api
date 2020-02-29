package com.fattahi.general.report;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import com.fattahi.general.genum.ReportType;

public interface IJasperReport {

	ResponseEntity<byte[]> getReport(Object dataSource, String fileName, ReportType reportType);

	ResponseEntity<byte[]> getReport(Object dataSource, String fileName, Map<String, Object> params,
			ReportType reportType);

	ResponseEntity<byte[]> getReportByJson(Object dataSource, String fileName, ReportType reportType);

	ResponseEntity<byte[]> getReportByJson(Object dataSource, String fileName, Map<String, Object> params,
			ReportType reportType);

	ModelAndView getReportModelAndView(Object dataSource, String fileName, ReportType reportType);

}
