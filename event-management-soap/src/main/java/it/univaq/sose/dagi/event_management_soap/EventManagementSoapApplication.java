package it.univaq.sose.dagi.event_management_soap;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class EventManagementSoapApplication {
	
	//@Value("${server.port}"): Injects the value of the server.port property into the port variable. This represents the port on which the Spring Boot application is listening.
	//@Value("${cxf.path}"): Injects the value of the cxf.path property into the cxfPath variable. This represents the basic path of the CXF services in the application.
	@Value("${server.port}")
	private String port;
	
	@Value("${cxf.path}")
	private String cxfPath;

	//The main method starts the Spring Boot application. SpringApplication.run is the method that bootstraps
	//and launches the application, creating the Spring application context.
	public static void main(String[] args) {
		SpringApplication.run(EventManagementSoapApplication.class, args);
	}
	
	//This method runs when the application is fully started and ready to accept requests. The @EventListener annotation specifies that this method should be called in response to ApplicationReadyEvent.
	//System.out.println("Application started ... launching browser now"): prints a log message to the console.
	//browse(String.format("http://localhost:%s%s/", port, cxfPath)): Constructs the URL using the port and cxfPath and calls the browse method to open this URL in the browser.
	@EventListener({ApplicationReadyEvent.class})
	void applicationReadyEvent() {
	    System.out.println("Application started ... launching browser now");
	    browse(String.format("http://localhost:%s%s/", port, cxfPath));
	}

	//This method opens a URL in the system's default browser.
	//Desktop.isDesktopSupported(): checks whether the Desktop API is supported by the operating system.
	//.browse(new URI(url)): if Desktop is supported, use the Desktop API to open the URL in the browser.
	//Runtime.getRuntime().exec(...): if Desktop is not supported, use a system command to open the URL in the browser. This approach is specific to Windows, using rundll32 to handle the URL.
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
