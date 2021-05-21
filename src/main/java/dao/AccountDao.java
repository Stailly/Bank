package dao;

import db.DBQueries;
import db.JDBCUtils;
import model.Account;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account> {

    @Override
    public Account get(int id) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.SELECT_ACC)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(id);
                account.setOwner(rs.getInt("client"));
                account.setNumber(new BigInteger(rs.getString("account")));
                account.setBalance(rs.getDouble("balance"));
                return account;
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DBQueries.COUNT.concat("accounts"));
            ResultSet rs = statement.getResultSet();
            rs.next();
            int count = rs.getInt("count");
            for (int i = 0; i < count; i++) {
                Account account = get(i);
                if (account != null) {
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return accounts;
    }

    @Override
    public void save(Account account) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.INSERT_ACC)) {
            statement.setString(1, account.getNumber().toString());
            statement.setInt(2, account.getOwner());
            statement.setDouble(3, account.getBalance());
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }


    @Override
    public void delete(Account account) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.DELETE_FROM_TABLE_ACC)) {
            statement.setString(1, account.getNumber().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    /**
     * Метод вносит сумму на счёт
     *
     * @param account - номер счёта
     * @param sum     - сумма для внесения
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

    /**
     * Метод возвращает балланс по счёту
     *
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

    /**
     * Метод возвращает номер счёта, к которому привязана карта
     *
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