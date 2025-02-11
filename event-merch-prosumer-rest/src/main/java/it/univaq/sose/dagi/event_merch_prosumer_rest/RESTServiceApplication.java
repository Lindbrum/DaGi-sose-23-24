package it.univaq.sose.dagi.event_merch_prosumer_rest;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.EventSOAPClient;
import it.univaq.sose.dagi.event_merch_prosumer_rest.clients.MerchandiseRESTFeignClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RESTServiceApplication {

	@Autowired
	private Bus bus;
	
	@Autowired
	private EventSOAPClient eventClient;
	
	@Autowired
	private MerchandiseRESTFeignClient merchClient;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//This method starts the Spring Boot application. It initializes the application context and launches the application.
	public static void main(String[] args) {
		SpringApplication.run(RESTServiceApplication.class, args);
	}

	//This method configures and creates a JAX-RS server for RESTful services. It sets up the server with
	//the necessary dependencies, providers, and features, including a custom JSON provider for date compatibility and OpenAPI documentation.
	@Bean
	public Server rsServer(@Value("${swagger.definition.version}") String apiVersion) {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new EventMerchProsumerApiImpl(merchClient, eventClient)));
		endpoint.setAddress("/");
		endpoint.setProvider(new DateCompatibleJacksonJsonProvider());
		endpoint.setFeatures(Arrays.asList(createOpenApiFeature(apiVersion)));
		return endpoint.create();
	}

	//This method creates and configures an OpenApiFeature object that provides OpenAPI documentation for the REST API.
	//It sets various properties such as title, description, version, contact information, and Swagger UI configuration.
	@Bean
	public OpenApiFeature createOpenApiFeature(String apiVersion) {
		final OpenApiFeature openApiFeature = new OpenApiFeature();
		openApiFeature.setPrettyPrint(true);
		openApiFeature.setTitle("Event-Merchandise aggregator prosumer");
		openApiFeature.setContactName("DaGi team");
		openApiFeature.setDescription("This is a RESTful API that fetch and aggregates info on an event with the list of merchandise.");
		openApiFeature.setVersion(apiVersion);
		openApiFeature.setContactEmail("dario.dercole@student.univaq.it");
		openApiFeature.setLicense("Apache 2.0");
		openApiFeature.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
		openApiFeature.setSupportSwaggerUi(true);
		openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/event-merch-prosumer-rest/openapi.json").queryConfigEnabled(true));
		return openApiFeature;
	}
	
	//This method listens for the ApplicationReadyEvent, which is triggered when the application is fully started.
	//It prints a message and opens a web browser to the application’s service URL.
	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
	    System.out.println("Application started ... launching browser now");
	    browse(String.format("http://localhost:%s%s/services", port, cxfPath));
	}

	//This method attempts to open a web browser to a specified URL. It uses the Desktop class if supported, or falls back to a runtime command for older systems.
	public static void browse(String url) {
	    if(Desktop.isDesktopSupported()){
	        Desktop desktop = Desktop.getDesktop();
	        try {
	            desktop.browse(new URI(url));
	        } catch (IOException | URISyntaxException e) {
	            e.printStackTrace();
	        }
	    }else{
	        Runtime runtime = Runtime.getRuntime();
	        try {
	            runtime.exec(new String[] {"rundll32", "url.dll,FileProtocolHandler", url});
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
}