package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

import com.fasterxml.jackson.jakarta.rs.cfg.Annotations;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

public class DateCompatibleJacksonJsonProvider extends JacksonJsonProvider {

	public DateCompatibleJacksonJsonProvider() {
		super(new DateCompatibleObjectMapper(),
			new Annotations[]{Annotations.JACKSON, Annotations.JAKARTA_XML_BIND});
	}
}