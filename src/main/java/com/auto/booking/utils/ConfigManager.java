package com.auto.booking.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * This class is designed to keep all methods which are going to use in the API i.e URL, reading properties file.
 * @author dannymac
 *
 */
public class ConfigManager {
	private static ConfigManager manager;
	protected static String baseURL;
	private static final Properties properties = new Properties();
	private static final String path = System.getProperty("user.dir") + "/resources/config/config.properties";

	private ConfigManager() {
		this.readConfigFile();
	}

	public static ConfigManager getInstance() {
		if(manager == null) {
			synchronized (ConfigManager.class) {
				manager = new ConfigManager();
			}
		}
		return manager;
	}

	/**
	 * This method is used to return the base URL of the given API
	 * @return
	 */
	public String baseURL() {
		String baseURL = properties.getProperty("baseURL");
		return baseURL;
	}

	/**
	 * This method is used to read the data of the property file
	 */
	public void readConfigFile() {
		try {
			File filePath = new File(path);
			FileInputStream input = new FileInputStream(filePath);
			properties.load(input);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println(ex.getCause());
			ex.printStackTrace();
		}
	}
}
