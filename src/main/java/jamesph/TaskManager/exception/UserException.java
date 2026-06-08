package jamesph.TaskManager.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

/**
 * User-related production exceptions and factory helpers.
 */
public class UserException extends CustomException {

    private static final String PREFIX = "USER";

    private UserException(String message, String errorCode, HttpStatus status, Throwable cause, Map<String, Object> details) {
        super(message, errorCode, status, cause, details);
    }

    public static UserException notFound(UUID userUuid) {
        return new UserException("User not found: " + userUuid, PREFIX + "_NOT_FOUND", HttpStatus.NOT_FOUND, null, Map.of("userUuid", userUuid));
    }

    public static UserException validation(String message) {
        return new UserException(message, PREFIX + "_VALIDATION", HttpStatus.BAD_REQUEST, null, null);
    }

    public static UserException conflict(String message) {
        return new UserException(message, PREFIX + "_CONFLICT", HttpStatus.CONFLICT, null, null);
    }

    public static UserException internal(String message, Throwable cause) {
        return new UserException(message, PREFIX + "_INTERNAL", HttpStatus.INTERNAL_SERVER_ERROR, cause, null);
    }
}
