package dao;

public class DBQueries {
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    public static final String SELECT_ALL = "SELECT * FROM ";
    public static final String COUNT = "SELECT COUNT(*) as count FROM ";


    public static final String CREATE_TABLE_CARDS = "CREATE TABLE IF NOT EXISTS cards (\r\n" +
            "  card LONG(16)NOT NULL PRIMARY KEY,\r\n" +
            "  account INT(10) NOT NULL,\r\n" +
            "  client INT(10) NOT NULL\r\n" +
            "  );";
    public static final String GET_CARDS_FOR_OWNER = "SELECT card FROM cards WHERE client = ?";
    public static final String INSERT_CARD = "INSERT INTO cards (card, account, client) VALUES(?, ?, ?);";
    public static final String GET_ACCOUNT_FROM_CARD = "SELECT account FROM cards WHERE card = ?";


    public static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS accounts (\r\n" +
            "  id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" +
            "  account VARCHAR(20) NOT NULL,\r\n" +
            "  client INT(10) NOT NULL,\r\n" +
            "  balance DOUBLE(20)\r\n" +
            "  );";
    public static final String SELECT_BALANCE = "SELECT balance FROM accounts WHERE account = ?";
    public static final String UPDATE_BALANCE = "update accounts set balance = ? where account = ?;";
    public static final String GET_ACCOUNT_FROM_ID = "SELECT id, client FROM accounts WHERE account = ?";



    public static final String CREATE_TABLE_CLIENTS = "create table IF NOT EXISTS clients (\r\n" +
            "  id int(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,\r\n" +
            "  name varchar(50) NOT NULL,\r\n" +
            "  phone varchar(12) NOT NULL\r\n" +
            "  );";
}
