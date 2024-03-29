package chess.db;

public class DatabaseConfiguration {
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "3306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String MYSQL_CONNECT_URL_FORMAT_WITHOUT_DB = "jdbc:mysql://%s:%s?%s";
    private static final String MYSQL_CONNECT_URL_FORMAT = "jdbc:mysql://%s:%s/%s?%s";

    public static String getUrlWithoutDb() {
        return MYSQL_CONNECT_URL_FORMAT_WITHOUT_DB.formatted(HOST, PORT, OPTION);
    }

    public static String getUrl() {
        return MYSQL_CONNECT_URL_FORMAT.formatted(HOST, PORT, DATABASE, OPTION);
    }


    public static String getUsername() {
        return USERNAME;
    }

    public static String getPassword() {
        return PASSWORD;
    }
}
