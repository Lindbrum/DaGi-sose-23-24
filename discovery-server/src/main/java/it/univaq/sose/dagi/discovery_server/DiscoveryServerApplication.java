package it.univaq.sose.dagi.discovery_server;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

	// "port" is the port number on which the application listens.
	// "cxfPath" is the context path for the CXF services.
	@Value("${server.port}")
	private String port;

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}

	// This method listens to the ApplicationReadyEvent, which is emitted when the
	// application is fully started and ready to receive requests.
	// When the event is captured, print a message to the console (Application
	// started...) and call the browse method to launch the default browser to the
	// service URL.
	@EventListener({ ApplicationReadyEvent.class })
	void applicationReadyEvent() {
		System.out.println("Application started ... launching browser now");
		browse(String.format("http://localhost:%s/", port));
	}

	// This method attempts to open the specified URL in the system's default
	// browser.
	// Check if the desktop is supported using Desktop.isDesktopSupported(), and if
	// it is, ".browse(new URI(url))" to open the URL.
	// If the desktop is not supported, use a system command to open the URL.
	public static void browse(String url) {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				desktop.browse(new URI(url));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(new String[] { "rundll32", "url.dll,FileProtocolHandler", url });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
