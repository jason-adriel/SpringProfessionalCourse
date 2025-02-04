package org.jasonadriel.dto;

import java.util.List;

public class ErrorDto {
    public List<String> invalidFields;
    public String errorMessage;

    public ErrorDto() {}

    public ErrorDto(List<String> invalidFields, String errorMessage) {
        this.invalidFields = invalidFields;
        this.errorMessage = errorMessage;
    }

    public List<String> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(List<String> invalidFields) {
        this.invalidFields = invalidFields;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
