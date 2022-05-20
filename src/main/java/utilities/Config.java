package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	Properties prop = new Properties();
	String fileName = "config.properties";

	public Config()
	{
		loadConfig();
	}

	private void loadConfig()
	{
		var path =
				System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator +
						"resources" + File.separator + fileName;
		try (FileInputStream fis = new FileInputStream(path)) {
			prop.load(fis);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public String getProperty(String name)
	{
		return prop.getProperty(name);
	}

}
