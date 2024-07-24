package it.univaq.sose.dagi.sales_analysis_prosumer_rest;

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

import it.univaq.sose.dagi.sales_analysis_prosumer_rest.client.CustomerRESTClient;
import it.univaq.sose.dagi.sales_analysis_prosumer_rest.client.SoldTicketsSOAPClient;

//This constructor Initializes the Spring Boot application, configures beans for REST
//server, OpenAPI documentation, and handles application startup events.
@SpringBootApplication
@EnableDiscoveryClient
public class SalesAnalysisProsumerRestApplication {

	
	@Autowired
	private Bus bus;
	
	@Autowired
	private SoldTicketsSOAPClient ticketsClient;
	
	@Autowired
	private CustomerRESTClient customerClient;
	
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//Starts the Spring Boot application by invoking SpringApplication.run() with the class and command-line arguments.
	public static void main(String[] args) {
		SpringApplication.run(SalesAnalysisProsumerRestApplication.class, args);
	}

	//This method Sets up a JAXRSServerFactoryBean with the application bus, service beans, JSON provider,
	//and OpenAPI features, then creates and returns the server instance.
	@Bean
	public Server rsServer(@Value("${swagger.definition.version}") String apiVersion) {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new SalesAnalysisProsumerApiImpl(ticketsClient, customerClient)));
		endpoint.setAddress("/");
		endpoint.setProvider(new DateCompatibleJacksonJsonProvider());
		endpoint.setFeatures(Arrays.asList(createOpenApiFeature(apiVersion)));
		return endpoint.create();
	}

	//This method sets up and returns an OpenApiFeature with some prints, like API title, contact
	//details, description, version, license information, and Swagger UI configuration.
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
	
	//This method prints a message indicating the application has started and launches the default web browser to display the services URL.
	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
	    System.out.println("Application started ... launching browser now");
	    browse(String.format("http://localhost:%s%s/services", port, cxfPath));
	}

	//This method first checks if the Desktop class is supported by the current environment. If supported, it uses the .browse(new URI(url)) method to open
	//the specified URL in the default web browser. If the Desktop API is not available, it falls back to using the Runtime.exec() method with
	//a platform-specific command (rundll32 on Windows) to open the URL. In both cases, it catches and prints any
	//IOException or URISyntaxException that may occur during the operation.
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
