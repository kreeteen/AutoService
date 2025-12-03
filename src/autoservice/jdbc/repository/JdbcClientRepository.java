package autoservice.jdbc.repository;

import autoservice.domain.model.Client;
import autoservice.domain.repository.ClientRepository;
import autoservice.jdbc.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcClientRepository implements ClientRepository {

    @Override
    public Client save(Client client) {
        String sql;
        if (client.getClientId() == null) {
            // INSERT
            sql = "INSERT INTO clients (first_name, last_name, middle_name, phone) VALUES (?, ?, ?, ?) RETURNING client_id";
        } else {
            // UPDATE
            sql = "UPDATE clients SET first_name = ?, last_name = ?, middle_name = ?, phone = ? WHERE client_id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getFirstName());
            stmt.setString(2, client.getLastName());
            stmt.setString(3, client.getMiddleName());
            stmt.setString(4, client.getPhone());

            if (client.getClientId() == null) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    client.setClientId(Long.valueOf(rs.getLong("client_id")));
                }
            } else {
                stmt.setLong(5, client.getClientId());
                stmt.executeUpdate();
            }

            return client;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения клиента", e);
        }
    }

    @Override
    public Optional<Client> findById(Long id) {
        String sql = "SELECT * FROM clients WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToClient(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска клиента по ID", e);
        }
    }

    @Override
    public List<Client> findAll() {
        String sql = "SELECT * FROM clients ORDER BY client_id";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clients.add(mapResultSetToClient(rs));
            }
            return clients;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех клиентов", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM clients WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления клиента", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM clients WHERE client_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования клиента", e);
        }
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        return new Client(
                Long.valueOf(rs.getLong("client_id")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("middle_name"),
                rs.getString("phone")
        );
    }
}
