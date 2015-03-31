package com.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
	
	private static Config config;
	
	public static Config getConfig(String configPath) throws FileNotFoundException, IOException {
		if(config == null) {
			config = new Config();
			config.initProperties(configPath);
		}
		return config;
	}
	
	public static Config getConfig() {
		if(config == null) {
			//TODO
		}
		return config;
	}
	
	private String SERVER_IP = "server.ip";
	private String SERVER_PORT = "server.port";
	private String WEBROOT = "webroot";
	
	private Properties properties;
	
	private void initProperties(String configPath) throws FileNotFoundException, IOException {
		properties = new Properties();
		properties.load(new FileReader(configPath));
	}
	
	public String getIp() {
		return properties.getProperty(SERVER_IP);
	}
	
	public int getPort() {
		return Integer.parseInt(properties.getProperty(SERVER_PORT));
	}
	
	public String getWebRoot() {
		return properties.getProperty(WEBROOT);
	}
	
	
}
