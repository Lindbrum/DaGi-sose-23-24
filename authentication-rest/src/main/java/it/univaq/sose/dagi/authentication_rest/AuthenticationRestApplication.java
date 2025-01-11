package it.univaq.sose.dagi.authentication_rest;

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

import it.univaq.sose.dagi.authentication_rest.service.CustomerServiceImpl;
import it.univaq.sose.dagi.authentication_rest.service.OrganizerServiceDummyImpl;
import it.univaq.sose.dagi.authentication_rest.service.OrganizerServiceImpl;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthenticationRestApplication {

	//@Autowired: The bus field is automatically injected from the Spring context. 
	//Bus is a component of CXF that manages service configuration and integration.
	@Autowired
	private Bus bus;
	
	@Autowired
	private CustomerServiceImpl customerService;
	
	@Autowired
	private OrganizerServiceImpl organizerService;
	
	//"port" is the port number on which the application listens.
	//"cxfPath" is the context path for the CXF services.
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//This method starts the SpringBoot application
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationRestApplication.class, args);
	}

	//This method creates and configures a REST server using Apache CXF's JAXRSServerFactoryBean. 
	//Sets the CXF bus, defines the service implementation as AuthRestApiImpl, and specifies the base address as "/".
	//It uses JacksonJsonProvider to handle JSON serialization and deserialization
	//and adds the OpenAPI features configured in the createOpenApiFeature method.
	@Bean
	public Server rsServer(@Value("${swagger.definition.version}") String apiVersion) {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setServiceBeans(Arrays.<Object>asList(new AuthRestApiImpl(customerService, organizerService)));
		endpoint.setAddress("/");
		endpoint.setProvider(new JacksonJsonProvider());
		endpoint.setFeatures(Arrays.asList(createOpenApiFeature(apiVersion)));
		return endpoint.create();
	}

	//This method configures the OpenApiFeature, which provides the OpenAPI specification for API documentation.
	//Set various attributes such as title, contact name, description, version, contact email, license and Swagger UI interface support.
	//This setup allows to view and test the API via an interactive web interface.
	@Bean
	public OpenApiFeature createOpenApiFeature(String apiVersion) {
		final OpenApiFeature openApiFeature = new OpenApiFeature();
		openApiFeature.setPrettyPrint(true);
		openApiFeature.setTitle("Authentication provider");
		openApiFeature.setContactName("DaGi team");
		openApiFeature.setDescription("This is a RESTful API that provide authentication and allows fetching of user info.");
		openApiFeature.setVersion(apiVersion);
		openApiFeature.setContactEmail("dario.dercole@student.univaq.it");
		openApiFeature.setLicense("Apache 2.0");
		openApiFeature.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html");
		openApiFeature.setSupportSwaggerUi(true);
		openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/authentication-rest/openapi.json").queryConfigEnabled(true));
		return openApiFeature;
	}
	
	//This method listens to the ApplicationReadyEvent, which is emitted when the application is fully started and ready to receive requests.
	//When the event is captured, print a message to the console (Application started...) and call the browse method to launch the default browser to the service URL.
	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
	    System.out.println("Application started ... launching browser now");
	    browse(String.format("http://localhost:%s%s/services", port, cxfPath));
	}

	//This method attempts to open the specified URL in the system's default browser.
	//Check if the desktop is supported using Desktop.isDesktopSupported(), and if it is, ".browse(new URI(url))" to open the URL.
	//If the desktop is not supported, use a system command to open the URL.
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
