package at.romboe.abstimmung.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigInfo {

	@Autowired
	private ConfigProperties config;


	public void print() {
		System.out.println("client.url=" + config.getClient().getUrl());
	}
}
