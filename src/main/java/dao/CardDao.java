package dao;

import db.DBQueries;
import db.JDBCUtils;
import exceptions.FormatException;
import model.Card;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static db.DBMethods.generateCardNumber;

public class CardDao implements Dao<Card> {
    private final AccountDao accountDao = new AccountDao();

    @Override
    public Card get(int clientID) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.SELECT_CARD)) {
            statement.setInt(1, clientID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("account"));
                card.setNumber(rs.getLong("card"));
                card.setOwner(clientID);
                return card;
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return null;
    }

    @Override
    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DBQueries.COUNT.concat("cards"));
            ResultSet rs = statement.getResultSet();
            rs.next();
            int count = rs.getInt("count");
            for (int i = 0; i < count; i++) {
                Card card = get(i);
                if (card != null) {
                    cards.add(card);
                }
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return cards;
    }

    @Override
    public void save(Card card) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.INSERT_CARD)) {
            statement.setLong(1, card.getNumber());
            statement.setInt(2, card.getAccount());
            statement.setInt(3, card.getOwner());

            statement.execute();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    @Override
    public void delete(Card card) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.DELETE_FROM_TABLE_CARDS)) {
            statement.setInt(1, card.getOwner());
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /**
     * Метод вносит сумму на счёт через карту
     *
     * @param card - номер карты
     * @param sum  - сумма для внесения
     */
    public void addMoneyToCard(long card, double sum) {
        BigInteger account = accountDao.getAccountNumber(card);
        if (account != null) {
            accountDao.addMoneyToAcc(account, sum);
        }
    }

    /**
     * Метод возвращает балланс по карте
     *
     * @param card - номер карты, балланс которой проверяем
     * @return - возвращает балланс либо возвращает -1, если счёта не существует
     */
    public double checkCardBalance(long card) {
        BigInteger account = accountDao.getAccountNumber(card);
        if (account != null) {
            return accountDao.checkAccountBalance(account);
        } else {
            return -1.0;
        }
    }

    /**
     * Метод выводит номера карт, принадлежащие заданному владельцу
     *
     * @param owner - уникальный номер владельца карты
     */
    public void getCardFromOwner(int owner) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_CARDS_FOR_OWNER)) {
            preparedStatement.setInt(1, owner);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String card = rs.getString("card");
                System.out.println(card);
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /**
     * Метод создаёт новую карту, привязанную к счёту
     *
     * @param account - счёт, к которому нужно открыть карту
     */
    public void createCard(BigInteger account) throws FormatException {
        if (account.toString().length() != 20) {
            throw new FormatException("Номер счёта должен содержать 20 цифр");
        }
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_ACCOUNT_FROM_ID)) {
            preparedStatement.setString(1, account.toString());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Card card = new Card(rs.getInt("id"));
                card.setOwner(rs.getInt("client"));
                card.setNumber(generateCardNumber());
                save(card);
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /**
     * Метод создаёт новую карту, привязанную к счёту, где владелец карты и владелец счёта - разные люди
     *
     * @param account - номер счёта
     * @param owner   - уникальный номер клиента
     * @throws FormatException - если передан невалидный номер счёта
     */
    public void createCard(BigInteger account, int owner) throws FormatException {
        if (account.toString().length() != 20) {
            throw new FormatException("Номер счёта должен содержать 20 цифр");
        }
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DBQueries.GET_ACCOUNT_FROM_ID)) {
            preparedStatement.setString(1, account.toString());
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            Card card = new Card(rs.getInt("id"), owner);
            card.setNumber(generateCardNumber());
            save(card);
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

}
