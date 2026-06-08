package jamesph.TaskManager.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class CustomException extends RuntimeException {
    private final String id;
    private final String errorCode;
    private final HttpStatus status;
    private final Instant timestamp;
    private final Map<String, Object> details;

    public CustomException(String message, String errorCode, HttpStatus status) {
        this(message, errorCode, status, null, null);
    }

    public CustomException(String message, String errorCode, HttpStatus status, Throwable cause, Map<String, Object> details) {
        super(message, cause);
        this.id = UUID.randomUUID().toString();
        this.errorCode = errorCode == null ? "UNKNOWN" : errorCode;
        this.status = status == null ? HttpStatus.INTERNAL_SERVER_ERROR : status;
        this.timestamp = Instant.now();
        this.details = details == null ? Collections.emptyMap() : Collections.unmodifiableMap(details);
    }

    public String getId() {
        return id;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public Map<String, Object> toMap() {
        return Map.of(
                "id", id,
                "errorCode", errorCode,
                "message", getMessage(),
                "status", status.value(),
                "timestamp", timestamp.toString(),
                "details", details
        );
    }

    public static CustomException of(String message, String errorCode, HttpStatus status) {
        return new CustomException(message, errorCode, status);
    }
}
