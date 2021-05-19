package service;

import dao.DBQueries;
import dao.JDBCUtils;
import exceptions.FormatException;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dao.DBMethods.generateCardNumber;

public class Methods {

        public static void main(String[] args) {
            Methods main = new Methods();
            main.getCardFromOwner(28);
    //        System.out.println(main.checkBalance(new BigInteger("40817810658370000096")));
    //        main.addMoney(new BigInteger("40817810658370000096"), 1000.00);
    //        System.out.println(main.checkBalance(new BigInteger("40817810658370000096")));
    //        main.createCard(new BigInteger("40817810658370000096"));
       }

    /*
     * Метод создаёт новую карту, привязанную к счёту
     * @param account - счёт, к которому нужно открыть карту
     */
    public void createCard(BigInteger account) throws FormatException {
        if (!isAccountNumberValid(account)){
            throw new FormatException("Номер счёта должен содержать 20 цифр");
        }
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_ACCOUNT_FROM_ID)) {
            preparedStatement.setString(1, account.toString());
            ResultSet rs = preparedStatement.executeQuery();
            int accountId = -1;
            int clientId = -1;
            while (rs.next()) {
                accountId = rs.getInt("id");
                clientId=rs.getInt("client");
            }
            long cardNumber = generateCardNumber();
            addCard(cardNumber, accountId, clientId);
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public boolean isAccountNumberValid (BigInteger account){
        return account.toString().length()==20;
    }
    public boolean isCardNumberValid (long card){
        return String.valueOf(card).length()==16;
    }
    /*
     * Метод добавляет новую карту в базу данных
     * @param cardNumber - номер карты
     * @param accountId - уникальный ключ счёта
     * @param client - уникальный код владельца карты
     */
    public void addCard(long cardNumber, int accountId, int client) throws FormatException {
        if (!isCardNumberValid(cardNumber)){
            throw new FormatException("Номер карты должен содержать 16 цифр");
        }
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.INSERT_CARD)) {
            statement.setLong(1, cardNumber);
            statement.setInt(2, accountId);
            statement.setInt(2, client);
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /*
     * Метод выводит номера карт, принадлежащие заданному владельцу
     * @param owner - уникальный номер владельца карты
     */
    public void getCardFromOwner(int owner) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_CARDS_FOR_OWNER)) {
            preparedStatement.setInt(1, owner);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String card = rs.getString("card");
                System.out.println("Cards for id " + owner);
                System.out.println(card);
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /*
     * Метод вносит сумму на счёт
     * @param account - номер счёта
     * @param sum - сумма для внесения
     */
    public void addMoneyToAcc(BigInteger account, double sum) {
        double balance = checkAccountBalance(account);
        if (balance == -1.0) {
            System.out.println("Данного счёта в бaзе не существует");
        } else {
            balance += sum;
            try (Connection connection = JDBCUtils.getConnection();
                 PreparedStatement statement = connection.prepareStatement(DBQueries.UPDATE_BALANCE)) {
                statement.setDouble(1, balance);
                statement.setString(2, account.toString());
                statement.executeUpdate();
            } catch (SQLException e) {
                JDBCUtils.printSQLException(e);
            }
        }
    }

    /*
     * Метод вносит сумму на счёт через карту
     * @param card - номер карты
     * @param sum - сумма для внесения
     */
    public void addMoneyToCard(long card, double sum) {
        BigInteger account = getAccountNumber(card);
        if (account != null) {
            addMoneyToAcc(account, sum);
        }
    }

    /*
     * Метод возвращает балланс по счёту
     * @param account - номер счёта, балланс которого нужно узнать
     * @return - возвращает балланс счёта либо возвращает -1, если счёта не существует
     */
    public double checkAccountBalance(BigInteger account) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.SELECT_BALANCE)) {
            preparedStatement.setString(1, account.toString());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return rs.getDouble("balance");
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);

        }
        return -1.0;
    }

    /*
     * Метод возвращает балланс по карте
     * @param card - номер карты, балланс которой проверяем
     * @return - возвращает балланс либо возвращает -1, если счёта не существует
     */
    public double checkCardBalance(long card) {
        BigInteger account = getAccountNumber(card);
        if (account != null) {
            return checkAccountBalance(account);
        } else {
            return -1.0;
        }
    }

    /*
     * Метод возвращает номер счёта, к которому привязана карта
     * @param card - номер карты
     * @return - возвращает номер счёта либо возвращает null, если счёта не существует
     */
    public BigInteger getAccountNumber(long card) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_ACCOUNT_FROM_CARD)) {
            preparedStatement.setLong(1, card);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new BigInteger(rs.getString("account"));
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return null;
    }
}
