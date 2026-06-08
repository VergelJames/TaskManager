package jamesph.TaskManager.exception;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String id;
    private final String errorCode;
    private final String message;
    private final int status;
    private final String reason;
    private final Instant timestamp;
    private final Map<String, Object> details;
    private final String path;

    public ErrorResponse(String id, String errorCode, String message, int status, String reason, Instant timestamp, Map<String, Object> details, String path) {
        this.id = id;
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
        this.reason = reason;
        this.timestamp = timestamp;
        this.details = details == null ? Collections.emptyMap() : Map.copyOf(details);
        this.path = path;
    }
}
