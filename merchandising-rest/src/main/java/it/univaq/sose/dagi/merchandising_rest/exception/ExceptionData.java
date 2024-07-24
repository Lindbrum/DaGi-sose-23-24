package it.univaq.sose.dagi.merchandising_rest.exception;

import org.springframework.http.HttpStatus;

//This is a record class used to encapsulate details about an exception or error in a structured format.
public record ExceptionData(HttpStatus status, int code, String message) {

}
