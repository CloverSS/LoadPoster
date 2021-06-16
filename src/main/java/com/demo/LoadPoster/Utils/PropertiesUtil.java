package com.demo.LoadPoster.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Properties getProperties(String path) throws IOException {
		//InputStream inputStream = Thread.currentThread().getContextClassLoader()
		//		.getResourceAsStream("C:\\Program Files\\eclipse\\workspace\\LoadPoster\\src\\main\\java\\com\\demo\\LoadPoster\\Utils\\config.properties");
		InputStream inputStream = new FileInputStream(new File("C:\\Program Files\\eclipse\\workspace\\LoadPoster\\src\\main\\java\\com\\demo\\LoadPoster\\Utils\\config.properties"));
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}