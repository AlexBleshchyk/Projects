package com.tsg.flooring.dao;

public interface FlooringAuditDao {
	
	public void writeAuditEntry(String entry) throws FlooringPersistenceException;
	
	public void writeAuditStartSession() throws FlooringPersistenceException;
}
