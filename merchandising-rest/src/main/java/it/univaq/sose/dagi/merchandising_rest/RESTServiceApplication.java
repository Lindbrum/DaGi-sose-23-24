package it.univaq.sose.dagi.merchandising_rest;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import it.univaq.sose.dagi.merchandising_rest.service.MerchandiseServiceDummyImpl;

//This is the main application class for the Spring Boot RESTful service that provides access and management
//of event merchandise data. It is annotated with @SpringBootApplication, which marks
//it as the entry point for the Spring Boot application and enables component
//scanning, auto-configuration, and property support.
@SpringBootApplication
@EnableDiscoveryClient
public class RESTServiceApplication {

	//The class injects dependencies such as Bus and MerchandiseServiceDummyImpl using the @Autowired annotation.
	//These dependencies are used to configure and start the JAX-RS server.
	@Autowired
	private Bus bus;
	
	@Autowired
	private MerchandiseServiceDummyImpl merchService;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//This method uses SpringApplication.run to launch the Spring Boot application, initializing the context and starting the embedded server.
	public static void main(String[] args) {
		SpringApplication.run(RESTServiceApplication.class, args);
	}

	//method, annotated with @Bean, configures a JAX-RS server using JAXRSServerFactoryBean. It sets up the server with the provided Bus, MerchandiseApiImpl
	//service, and additional features like JSON support and OpenAPI documentation. This server listens for HTTP requests and delegates them to the MerchandiseApiImpl service.
	@Bean
	public Server rsServer(@Value("${swagger.definition.version}") String apiVersion) {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new MerchandiseApiImpl(merchService)));
		endpoint.setAddress("/");
		endpoint.setProvider(new JacksonJsonProvider());
		endpoint.setFeatures(Arrays.asList(createOpenApiFeature(apiVersion)));
		return endpoint.create();
	}

	//method, also annotated with @Bean, configures OpenAPI documentation for the RESTful API. It sets various metadata fields such as title, description, version, contact
	//information, and license. This feature enables Swagger UI for interactive API documentation, enhancing usability and providing a visual interface for exploring the API.
	@Bean
	public OpenApiFeature createOpenApiFeature(String apiVersion) {
		final OpenApiFeature openApiFeature = new OpenApiFeature();
		openApiFeature.setPrettyPrint(true);
		openApiFeature.setTitle("Merchandise provider");
		openApiFeature.setContactName("DaGi team");
		openApiFeature.setDescription("This is a RESTful API that allows management and access to data about event merchandise.");
		openApiFeature.setVersion(apiVersion);
		openApiFeature.setContactEmail("dario.dercole@student.univaq.it");
		openApiFeature.setLicense("Apache 2.0");
		openApiFeature.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
		openApiFeature.setSupportSwaggerUi(true);
		openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/merchandise-rest/openapi.json").queryConfigEnabled(true));
		return openApiFeature;
	}
	
	//method, annotated with @EventListener(ApplicationReadyEvent.class), executes when the application has fully started.
	//It opens a browser window to the service URL, which includes the port and path configurations, allowing quick access
	//to the API’s documentation and services.
	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
	    System.out.println("Application started ... launching browser now");
	    browse(String.format("http://localhost:%s%s/services", port, cxfPath));
	}

	//This method is a utility function that attempts to open a URL in the default web browser. It uses Desktop if supported
	//or falls back to Runtime.exec for environments where Desktop is not available. This method is called after the application
	//starts to automatically open the service’s API documentation in a browser.
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