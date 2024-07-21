package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class DateCompatibleObjectMapper extends ObjectMapper {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 945053608713308863L;

	public DateCompatibleObjectMapper() {
		super();
		registerModule(new JavaTimeModule());
	}
}
