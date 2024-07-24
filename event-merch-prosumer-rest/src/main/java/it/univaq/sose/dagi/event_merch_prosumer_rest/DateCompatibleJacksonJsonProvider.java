package it.univaq.sose.dagi.event_merch_prosumer_rest;

import com.fasterxml.jackson.jakarta.rs.cfg.Annotations;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

//The DateCompatibleJacksonJsonProvider class extends JacksonJsonProvider to use a custom ObjectMapper (likely DateCompatibleObjectMapper) for JSON processing.
//The constructor initializes the provider with this custom mapper and specifies that it supports both Jackson and Jakarta XML Bind annotations.
//This setup ensures the JSON provider is configured to handle specific date formats and annotation types according to the custom object mapper’s settings.
public class DateCompatibleJacksonJsonProvider extends JacksonJsonProvider {

	public DateCompatibleJacksonJsonProvider() {
		super(new DateCompatibleObjectMapper(),
			new Annotations[]{Annotations.JACKSON, Annotations.JAKARTA_XML_BIND});
	}
}