package com.global.ProjectManagement.Services;

import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Entity.Log;
import com.global.ProjectManagement.Repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository;

	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public void logAction(String action, String level, String message, String username, Long entityId,
			String entityName, String details) {
		Log log = new Log(action, level, message, username, entityId, entityName, details);
		logRepository.save(log);
	}

	public void logInsert(String message, String username, Long entityId, String entityName) {
		logAction("INSERT", "INFO", message, username, entityId, entityName, null);
	}

	public void logError(String action, String message, String username, String details) {
		logAction(action, "ERROR", message, username, null, null, details);
	}
}
