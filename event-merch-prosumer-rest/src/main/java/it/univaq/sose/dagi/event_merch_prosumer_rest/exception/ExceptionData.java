package it.univaq.sose.dagi.event_merch_prosumer_rest.exception;

import org.springframework.http.HttpStatus;

public record ExceptionData(HttpStatus status, int code, String message) {

}
