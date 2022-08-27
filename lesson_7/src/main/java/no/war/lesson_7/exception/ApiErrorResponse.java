package no.war.lesson_7.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApiErrorResponse {

    private String error;
    private String message;
}