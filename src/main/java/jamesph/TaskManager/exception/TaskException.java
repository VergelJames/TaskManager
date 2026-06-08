package jamesph.TaskManager.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

/**
 * Task-related production exceptions and factory helpers.
 */
public class TaskException extends CustomException {

    private static final String PREFIX = "TASK";

    private TaskException(String message, String errorCode, HttpStatus status, Throwable cause, Map<String, Object> details) {
        super(message, errorCode, status, cause, details);
    }

    public static TaskException notFound(UUID taskUuid) {
        return new TaskException("Task not found: " + taskUuid, PREFIX + "_NOT_FOUND", HttpStatus.NOT_FOUND, null, Map.of("taskUuid", taskUuid));
    }

    public static TaskException validation(String message) {
        return new TaskException(message, PREFIX + "_VALIDATION", HttpStatus.BAD_REQUEST, null, null);
    }

    public static TaskException conflict(String message) {
        return new TaskException(message, PREFIX + "_CONFLICT", HttpStatus.CONFLICT, null, null);
    }

    public static TaskException internal(String message, Throwable cause) {
        return new TaskException(message, PREFIX + "_INTERNAL", HttpStatus.INTERNAL_SERVER_ERROR, cause, null);
    }
}
