package it.univaq.sose.dagi.customer_client.jackson;

import com.fasterxml.jackson.jaxrs.cfg.Annotations;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class DateCompatibleJacksonJsonProvider extends JacksonJsonProvider {

	public DateCompatibleJacksonJsonProvider() {
		super(new DateCompatibleObjectMapper(),
			new Annotations[]{Annotations.JACKSON, Annotations.JAXB});
	}
}