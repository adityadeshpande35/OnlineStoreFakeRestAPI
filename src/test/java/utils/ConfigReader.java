package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    Properties prop;
    private static final String CONFIG_FILE_PATH = ".\\src\\test\\resources\\config.properties";

    public ConfigReader() {
        prop = new Properties();
        try
            (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH))
            {
                prop.load(fis);

            }catch(IOException e)
            {
                e.printStackTrace();
                throw new RuntimeException("Failed to load config.properties file");

            }

        }

        public String getProperty(String key)
        {
           return prop.getProperty(key);

        }

    public int getIntProperty(String key)
    {
        return Integer.parseInt(prop.getProperty(key));

    }
    }


