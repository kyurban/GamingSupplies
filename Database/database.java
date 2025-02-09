package Database;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class database {
    private static final String CONFIG_FILE = "config.properties";
    private static String url;
    private static String username;
    private static String password;

    static {
        try (InputStream input = database.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IOException("Unable to find " + CONFIG_FILE);
            }
            Properties properties = new Properties();
            properties.load(input);

            url = properties.getProperty("db.url");
            username = properties.getProperty("db.user");
            password = properties.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
