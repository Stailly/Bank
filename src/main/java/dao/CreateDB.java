package dao;

import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

import static dao.DBMethods.generateAccountNumber;
import static dao.DBMethods.generateCardNumber;

public class CreateDB {
    private static CreateDB createDB;


    public static void main(String[] args) {
        createDB = new CreateDB();
        createDB.initClients();
        createDB.initAccounts();
        createDB.initCards();
    }

    private Double generateBalance() {
        double rand1 = new Random().nextDouble();
        int rand2 = new Random().nextInt(100);
        return Math.round(1000 * rand2 * rand1 * 100.00) / 100.00;
    }

    public void createTable(String query) {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void dropTable(String tableName) {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(tableName);
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void initClients() {
        createDB.dropTable(DBQueries.DROP_TABLE.concat("clients"));
        createDB.createTable(DBQueries.CREATE_TABLE_CLIENTS);
        createDB.insertClients();
    }

    public void initAccounts() {
        createDB.dropTable(DBQueries.DROP_TABLE.concat("accounts"));
        createDB.createTable(DBQueries.CREATE_TABLE_ACCOUNTS);
        createDB.insertAccounts();
    }

    public void initCards() {
        createDB.dropTable(DBQueries.DROP_TABLE.concat("cards"));
        createDB.createTable(DBQueries.CREATE_TABLE_CARDS);
        createDB.insertCards();
    }


    private void insertCards() {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            for (int i = 0; i < 100; i++) {
                long card = generateCardNumber();
                int account = new Random().nextInt(100);
                int client = new Random().nextInt(100);
                statement.execute(String.format("INSERT INTO cards (card, account, client) VALUES (%s,%s,%s)", card, account, client));
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void insertAccounts() {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            for (int i = 0; i < 100; i++) {
                double balance = generateBalance();
                int client = new Random().nextInt(100) + 1;
                BigInteger account = generateAccountNumber();
                statement.execute(String.format("INSERT INTO accounts (account, client, balance) VALUES (%s, %s, %s)", account, client, balance));
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void insertClients() {
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Rana Everett','+74944541443'),('Britanney Patton','+79883417884'),('Nevada Spears','+79338244037'),('Sandra Levy','+79737259774'),('Maya Knight','+79957619379'),('Jenna Merritt','+79925366024'),('Kelsey Moses','+79071050396'),('Beverly Nielsen','+79867187490'),('Hall Schwartz','+74278569367'),('Jane Lynch','+79949856584');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Signe Jefferson','+73419489720'),('Jaquelyn Reilly','+79567679597'),('Jordan Chaney','+74949837435'),('Orson Bates','+79531764035'),('Laurel Joseph','+79293629294'),('Gavin Leach','+79376121299'),('Nathaniel Jenkins','+79108082500'),('Shea Nelson','+78339375516'),('Colorado Armstrong','+73522688711'),('Mallory Holman','+79878302826');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Keegan Hickman','+79736201670'),('Nero Cortez','+78676786722'),('Emery Huff','+74741319731'),('Grace Lamb','+79739267116'),('Aline Alston','+73495702380'),('Lillian Vazquez','+74944298953'),('Macy Ashley','+79824367068'),('Dustin Nichols','+79791489108'),('Ainsley Pate','+79776750079'),('Kareem Hall','+79617619937');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Hilda Daugherty','+78783033206'),('Breanna Cruz','+79023650444'),('Dieter Dodson','+79595860705'),('Mufutau Calhoun','+78347159571'),('Kellie Mendoza','+73883900635'),('Gil Warner','+79656541507'),('Pamela Townsend','+79485017350'),('Cleo Knowles','+79209902252'),('Angelica Conway','+79007457439'),('Ray Leonard','+73844024847');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Sydney Jacobson','+78777983955'),('Imani Harris','+73956300414'),('Morgan Kinney','+79661320352'),('Dora Bennett','+78558442367'),('Ariana Rowe','+79384897130'),('Colin Reyes','+79929797710'),('Dieter Clements','+78136409798'),('Serina Trujillo','+79286577348'),('Kelsie Lawrence','+78352762763'),('Wing Gardner','+78345280153');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Nelle Sellers','+78412166334'),('Graham Dorsey','+78331078062'),('Nyssa Mccullough','+78138925002'),('Alexa Johnston','+79988956771'),('Jonas Munoz','+79693801852'),('Ursula Dale','+79581888547'),('Damon Gillespie','+73905436789'),('Zeus Kirk','+79211684849'),('Sade Vaughn','+79636327067'),('Julian Morgan','+79086090245');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Xander Jennings','+79104391234'),('Zenia Dyer','+79695309925'),('Buckminster Henson','+79652426304'),('Jelani Riggs','+74214046938'),('Troy Ryan','+73535572232'),('Nevada Bailey','+79524313219'),('Jack Spence','+79648998530'),('Bruno Navarro','+79532734505'),('Ciara Hughes','+79679372219'),('Cassidy Edwards','+78339770721');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Shay Ewing','+79633406060'),('Holmes Simpson','+73874381211'),('Kalia Branch','+79352162023'),('Jasper Cannon','+79339473608'),('Hamish Salazar','+79811212291'),('Declan Clark','+79406549621'),('Ignatius Macias','+79555426315'),('Lavinia Sosa','+79502197935'),('Simon Sloan','+73517681554'),('Drake Kirk','+79159882831');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Shana Mathis','+74863515145'),('Cruz Savage','+79089380884'),('Ross Clayton','+79874053810'),('Destiny Lee','+79298266312'),('Audrey Stephenson','+74746072358'),('Chadwick Abbott','+78486948740'),('Daquan Neal','+79731321132'),('Timon Cortez','+79751453252'),('Ginger Park','+73435156104'),('Justin Nixon','+78125641574');\n");
            statement.execute("INSERT INTO clients (name,phone) VALUES ('Mary Merritt','+78314915710'),('Dieter Velazquez','+73813800402'),('Yoshi Kramer','+79098985540'),('Alma Lucas','+79751663477'),('Quintessa Kirkland','+79867786259'),('Tarik Walsh','+79114439391'),('Summer Bauer','+73366966551'),('Chanda Levy','+78206649711'),('Eugenia Mcgee','+79315822415'),('Lawrence Wyatt','+79721113278');\n");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }
}

