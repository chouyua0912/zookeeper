package z.learn.configuration;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class PropertiesConfiguration {

    public static void main(String[] args) {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("config.properties"));
            // access configuration properties
            String host = config.getString("database.host");
            System.out.println(host);
            System.out.println(config.getString("database.port"));
            config.setProperty("database.port", 8200);
            System.out.println(config.getString("database.port"));
        } catch (ConfigurationException cex) {
            // Something went wrong
            cex.printStackTrace();
        }
    }
}
