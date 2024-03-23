package databases;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
    private static final String CONFIG_FILE = "C:\\Users\\alago\\OneDrive\\Desktop\\School\\L2\\JavaDev\\Projects\\JavaFinalProj\\src\\databases\\config.properties";
    private Properties properties;

    public DBConfig() {
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getURL() {
        return properties.getProperty("db.url");
    }

    public String getUser() {
        return properties.getProperty("db.user");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }
}
