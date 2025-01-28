package com.global.ProjectManagement.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "log")
@Getter
@Setter
@NoArgsConstructor
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String action;

	@Column(nullable = false)
	private String level;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(nullable = false)
	private LocalDateTime timestamp;

	private String username;

	private Long entityId;

	private String entityName;

	@Column(columnDefinition = "TEXT")
	private String details;
	

    public Log(String action, String level, String message, String username, Long entityId, String entityName, String details) {
        this.action = action;
        this.level = level;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.username = username;
        this.entityId = entityId;
        this.entityName = entityName;
        this.details = details;
    }
}
