package com.tsg.flooring.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class FlooringAuditDaoImpl implements FlooringAuditDao{
	
	public static final String AUDIT_FILE = "audit.txt"; 
	
	@Override
	public void writeAuditEntry(String entry) throws FlooringPersistenceException {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
		}catch (IOException e) {
			throw new FlooringPersistenceException("Could not persist audit information.", e);
		}
		
		LocalDateTime timeStamp = LocalDateTime.now();
		String formatted = timeStamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
		out.println(formatted + " : " + entry);
		out.flush();
		out.close();
		
	}

	@Override
	public void writeAuditStartSession() throws FlooringPersistenceException {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
		}catch (IOException e) {
			throw new FlooringPersistenceException("Could not persist audit information.", e);
		}
		
		out.print("------------------------------------------");
		out.flush();
		out.close();
		
	}
	
}
