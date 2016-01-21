package com.pigtrax.batch.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.pigtrax.batch.util.Constants;

public class BatchServlet extends HttpServlet {

	private static final long serialVersionUID = -5682411726932644158L;

	@Autowired
	private ProcessEngine processEngine;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		out.print("Get method not allowed");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		String resonse = "";
		try {
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put(Constants.EVENT_TYPE.toString(), request.getParameter(Constants.EVENT_TYPE.toString()));
			inputMap.put(Constants.HEADER.toString(), request.getParameter(Constants.HEADER.toString()));
			inputMap.put(Constants.SEPERATOR.toString(), request.getParameter(Constants.SEPERATOR.toString()));
			inputMap.put(Constants.FILE_TYPE.toString(), request.getParameter(Constants.FILE_TYPE.toString()));
			inputMap.put(Constants.USER_NAME.toString(), request.getParameter(Constants.USER_NAME.toString()));
			inputMap.put(Constants.DATA.toString(), request.getParameter(Constants.DATA.toString()));
			inputMap.put(Constants.COMPANY_ID.toString(), request.getParameter(Constants.COMPANY_ID.toString()));
			inputMap.put(Constants.PREMISE_ID.toString(), request.getParameter(Constants.PREMISE_ID.toString())); 
			processEngine.execute(inputMap);
			resonse = "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			resonse = "FAILURE";
		}
		
	}

	// for testing
	public static void main(String[] args) {
		ApplicationContext apContext = new ClassPathXmlApplicationContext("springConfig.xml");
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(Constants.EVENT_TYPE.toString(), "PREGNANCYINFO");
		inputMap.put(Constants.HEADER.toString(), "false");
		inputMap.put(Constants.SEPERATOR.toString(), ",");
		inputMap.put(Constants.FILE_TYPE.toString(), "csv");
		inputMap.put(Constants.USER_NAME.toString(), "pigtraxsuperadmin");
		inputMap.put(Constants.DATA.toString(), "d://test.csv");
		ProcessEngine p = apContext.getBean(ProcessEngine.class);
		p.execute(inputMap);
	}
}
