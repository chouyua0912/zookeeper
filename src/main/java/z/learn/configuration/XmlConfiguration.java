package z.learn.configuration;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;

public class XmlConfiguration {

    public static void main(String[] args) {
        Configurations configs = new Configurations();
        try {
            XMLConfiguration config = configs.xml("paths.xml");
            // access configuration properties
            String stage = config.getString("processing[@stage]");
            List<String> paths = config.getList(String.class, "processing.paths.path");
        } catch (ConfigurationException cex) {
            // Something went wrong
            cex.printStackTrace();
        }
    }
}
