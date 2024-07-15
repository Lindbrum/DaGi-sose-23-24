package it.univaq.sose.dagi.merchandising_rest.exception;

import org.springframework.http.HttpStatus;

public record ExceptionData(HttpStatus status, int code, String message) {

}
