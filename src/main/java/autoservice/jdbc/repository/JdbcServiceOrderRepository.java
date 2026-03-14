package autoservice.jdbc.repository;

import autoservice.domain.model.ServiceOrder;
import autoservice.domain.repository.ServiceOrderRepository;
import autoservice.jdbc.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcServiceOrderRepository implements ServiceOrderRepository {

    @Override
    public ServiceOrder save(ServiceOrder order) {
        String sql;
        if (order.getOrderId() == null) {
            sql = "INSERT INTO serviceorders (car_id, created_date, description, " +
                    "repair_description, completion_date, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?) RETURNING order_id";
        } else {
            sql = "UPDATE serviceorders SET car_id = ?, created_date = ?, description = ?, " +
                    "repair_description = ?, completion_date = ?, status = ? WHERE order_id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, order.getCarId());
            stmt.setDate(2, Date.valueOf(order.getCreatedDate()));
            stmt.setString(3, order.getDescription());

            if (order.getRepairDescription() != null) {
                stmt.setString(4, order.getRepairDescription());
            } else {
                stmt.setNull(4, Types.VARCHAR);
            }

            if (order.getCompletionDate() != null) {
                stmt.setDate(5, Date.valueOf(order.getCompletionDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setString(6, order.getStatus());

            if (order.getOrderId() == null) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    order.setOrderId(Long.valueOf(rs.getLong("order_id")));
                }
            } else {
                stmt.setLong(7, order.getOrderId());
                stmt.executeUpdate();
            }

            return order;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения заказа", e);
        }
    }

    @Override
    public Optional<ServiceOrder> findById(Long id) {
        String sql = "SELECT * FROM serviceorders WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToServiceOrder(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска заказа по ID", e);
        }
    }

    @Override
    public List<ServiceOrder> findAll() {
        String sql = "SELECT * FROM serviceorders ORDER BY order_id";
        List<ServiceOrder> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(mapResultSetToServiceOrder(rs));
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех заказов", e);
        }
    }

    @Override
    public List<ServiceOrder> findByCarId(Long carId) {
        String sql = "SELECT * FROM serviceorders WHERE car_id = ? ORDER BY order_id";
        List<ServiceOrder> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, carId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToServiceOrder(rs));
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска заказов по автомобилю", e);
        }
    }

    @Override
    public List<ServiceOrder> findByStatus(String status) {
        String sql = "SELECT * FROM serviceorders WHERE status = ? ORDER BY order_id";
        List<ServiceOrder> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orders.add(mapResultSetToServiceOrder(rs));
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска заказов по статусу", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM serviceorders WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления заказа", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM serviceorders WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования заказа", e);
        }
    }

    private ServiceOrder mapResultSetToServiceOrder(ResultSet rs) throws SQLException {
        Long orderId = Long.valueOf(rs.getLong("order_id"));
        Long carId = Long.valueOf(rs.getLong("car_id"));
        LocalDate createdDate = rs.getDate("created_date").toLocalDate();
        String description = rs.getString("description");

        String repairDescription = rs.getString("repair_description");
        if (rs.wasNull()) {
            repairDescription = null;
        }

        Date completionDateSql = rs.getDate("completion_date");
        LocalDate completionDate = null;
        if (completionDateSql != null) {
            completionDate = completionDateSql.toLocalDate();
        }

        String status = rs.getString("status");

        return new ServiceOrder(
                orderId, carId, createdDate, description,
                repairDescription, completionDate, status
        );
    }
}