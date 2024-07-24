package it.univaq.sose.dagi.feedback_prosumer_rest;

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

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;

import it.univaq.sose.dagi.feedback_prosumer_rest.client.CustomerRESTFeignClient;
import it.univaq.sose.dagi.feedback_prosumer_rest.client.FeedbackSOAPClient;


//The FeedbackProsumerRestApplication class is the main entry point for a Spring Boot application that provides a
//RESTful API for generating feedback reports on events. It is configured to run a JAX-RS server with Swagger documentation.
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FeedbackProsumerRestApplication {

	@Autowired
	private Bus bus;
	
	@Autowired
	private FeedbackSOAPClient feedbackClient;
	
	@Autowired
	private CustomerRESTFeignClient customerClient;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//The main method obviously starts the Spring Boot application, initializing the Spring context and running the application.
	public static void main(String[] args) {
		SpringApplication.run(FeedbackProsumerRestApplication.class, args);
	}

	//This method creates and configures a JAX-RS server using JAXRSServerFactoryBean. It sets up the server with a Bus instance, an implementation
	//of the FeedbackProsumerApi interface, and a JSON provider. The method also configures the OpenAPI feature to enable
	//Swagger documentation, including setting its title, description, and other metadata.
	@Bean
	public Server rsServer(@Value("${swagger.definition.version}") String apiVersion) {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new FeedbackProsumerApiImpl(feedbackClient, customerClient)));
		endpoint.setAddress("/");
		endpoint.setProvider(new JacksonJsonProvider());
		endpoint.setFeatures(Arrays.asList(createOpenApiFeature(apiVersion)));
		return endpoint.create();
	}

	//This method sets up the OpenAPI feature for the server. It configures the Swagger UI, sets the title
	//and description of the API, specifies contact information, and provides licensing details.
	@Bean
	public OpenApiFeature createOpenApiFeature(String apiVersion) {
		final OpenApiFeature openApiFeature = new OpenApiFeature();
		openApiFeature.setPrettyPrint(true);
		openApiFeature.setTitle("Feedback report Prosumer");
		openApiFeature.setContactName("DaGi team");
		openApiFeature.setDescription("This is a RESTful API that collects the feedbacks left for an event and generates a report.");
		openApiFeature.setVersion(apiVersion);
		openApiFeature.setContactEmail("dario.dercole@student.univaq.it");
		openApiFeature.setLicense("Apache 2.0");
		openApiFeature.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
		openApiFeature.setSupportSwaggerUi(true);
		openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/feedback-prosumer/openapi.json").queryConfigEnabled(true));
		return openApiFeature;
	}
	
	//The applicationReadyEvent method is triggered when the application is fully started.
	//It prints a message to the console and attempts to open the application's Swagger UI in the default web browser.
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
