package db;

import java.math.BigInteger;
import java.sql.*;
import java.util.Random;

import static db.DBQueries.*;


public class DBMethods {
    /*
     * Метод возвращает количество записей в таблице
     * @param tableName - имя таблицы
     * @return - возвращает количество записей либо возвращает -1, если таблицы не существует
     */
    private static int counter(String tableName) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT + tableName)) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return -1;
    }

    /*
     * Метод генерирует и возвращает уникальный номер счёта
     */
    public static BigInteger generateAccountNumber() {
        String builder = "40817810" + new Random().nextInt(10) +
                "5837" + String.format("%07d", counter("accounts"));
        return new BigInteger(builder);
    }

    /*
     * Метод генерирует и возвращает уникальный номер карты
     */
    public static long generateCardNumber() {
        String builder = (new Random().nextInt(10) < 6 ? 4 : 5) + String.format("%03d", new Random().nextInt(999))
                + 38 + String.format("%09d", counter("cards")) + new Random().nextInt(10);
        return Long.parseLong(builder);
    }
}
