package it.univaq.sose.dagi.event_merch_prosumer_rest.exception;

import org.springframework.http.HttpStatus;


//The ExceptionData record is used to encapsulate details about an exception. It includes three fields:
//HttpStatus status, which indicates the HTTP status code related to the exception;
//int code, representing a custom error code for additional context;
//String message, which provides a descriptive message about the error.
//This record structure facilitates clear and structured error reporting.
public record ExceptionData(HttpStatus status, int code, String message) {

}
