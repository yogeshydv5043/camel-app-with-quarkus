package org.tech.dto.response;

public class ValidationResponse {

    private boolean isValid;

    private String errorMessage;

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public ValidationResponse() {
    }

    public ValidationResponse(boolean isValid, String errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "ValidationResponse{" +
                "isValid=" + isValid +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
