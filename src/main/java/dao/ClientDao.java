package dao;
import db.DBQueries;
import db.JDBCUtils;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements Dao<Client> {

    @Override
    public Client get(int id) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.SELECT_CLIENT)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Client client = new Client(rs.getString("name"), rs.getString("phone"));
                client.setId(id);
                return client;
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(DBQueries.COUNT.concat("clients"));
            ResultSet rs = statement.getResultSet();
            rs.next();
            int count = rs.getInt("count");
            for (int i = 0; i < count; i++) {
                Client client = get(i);
                if (client != null) {
                    clients.add(client);
                }
            }
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
        return clients;
    }

    @Override
    public void save(Client client) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.INSERT_CLIENT)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }


    @Override
    public void delete(Client client) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.DELETE_FROM_TABLE_CLIENTS)) {
            statement.setInt(1, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

    public void update(Client client, Client newClient) {
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement statement = connection.prepareStatement(DBQueries.UPDATE_CLIENT)) {
            statement.setString(1, newClient.getName());
            statement.setString(2, newClient.getPhone());
            statement.setInt(3, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            JDBCUtils.printSQLException(e);
        }
    }

}
