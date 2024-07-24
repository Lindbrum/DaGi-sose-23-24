package it.univaq.sose.dagi.event_merch_prosumer_rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//The DateCompatibleObjectMapper class extends the ObjectMapper from the Jackson library to handle JSON serialization and deserialization.
public class DateCompatibleObjectMapper extends ObjectMapper {
	
	/**
	 * 
	 */
	
	//It includes a constructor that initializes the object mapper and registers the JavaTimeModule. 
	//This module enables the mapper to correctly handle Java 8 date and time types, ensuring that dates and times are serialized and deserialized in a format compatible with JSON standards.
	//The class also has a serialVersionUID field, which is used for serialization compatibility.
	private static final long serialVersionUID = 945053608713308863L;

	public DateCompatibleObjectMapper() {
		super();
		registerModule(new JavaTimeModule());
	}
}
