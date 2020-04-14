package at.romboe.abstimmung.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties
public class ConfigProperties {

	private Client client;
	
	@Data
	public static class Client {
		private String url;
		private int port;
	}
}