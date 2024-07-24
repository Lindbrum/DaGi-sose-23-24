package it.univaq.sose.dagi.soap_proxy_prosumer_rest.jackson;

import com.fasterxml.jackson.jakarta.rs.cfg.Annotations;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

public class DateCompatibleJacksonJsonProvider extends JacksonJsonProvider {

	public DateCompatibleJacksonJsonProvider() {
		super(new DateCompatibleObjectMapper(),
			new Annotations[]{Annotations.JACKSON, Annotations.JAKARTA_XML_BIND});
	}
}