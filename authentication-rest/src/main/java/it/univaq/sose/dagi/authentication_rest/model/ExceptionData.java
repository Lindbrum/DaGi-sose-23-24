package it.univaq.sose.dagi.authentication_rest.model;

import org.springframework.http.HttpStatus;

public record ExceptionData(HttpStatus status, int code, String message) {

}
