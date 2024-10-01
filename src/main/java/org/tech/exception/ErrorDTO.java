package org.tech.exception;

import java.time.LocalDateTime;

public class ErrorDTO {
    private String message;
    private LocalDateTime timestamp;

    public ErrorDTO(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
