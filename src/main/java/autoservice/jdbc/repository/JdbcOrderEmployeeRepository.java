package autoservice.jdbc.repository;

import autoservice.domain.model.OrderEmployee;
import autoservice.domain.repository.OrderEmployeeRepository;
import autoservice.jdbc.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderEmployeeRepository implements OrderEmployeeRepository {

    @Override
    public OrderEmployee save(OrderEmployee orderEmployee) {
        String checkSql = "SELECT COUNT(*) FROM orderemployees WHERE employee_id = ? AND order_id = ?";
        String sql;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setLong(1, orderEmployee.getEmployeeId());
            checkStmt.setLong(2, orderEmployee.getOrderId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // UPDATE
                sql = "UPDATE orderemployees SET role_in_order = ? WHERE employee_id = ? AND order_id = ?";
            } else {
                // INSERT
                sql = "INSERT INTO orderemployees (employee_id, order_id, role_in_order) VALUES (?, ?, ?)";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (sql.startsWith("UPDATE")) {
                    stmt.setString(1, orderEmployee.getRole());
                    stmt.setLong(2, orderEmployee.getEmployeeId());
                    stmt.setLong(3, orderEmployee.getOrderId());
                } else {
                    stmt.setLong(1, orderEmployee.getEmployeeId());
                    stmt.setLong(2, orderEmployee.getOrderId());
                    stmt.setString(3, orderEmployee.getRole());
                }
                stmt.executeUpdate();
            }

            return orderEmployee;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения назначения сотрудника", e);
        }
    }

    @Override
    public List<OrderEmployee> findAll() {
        String sql = "SELECT * FROM orderemployees ORDER BY order_id, employee_id";
        List<OrderEmployee> assignments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                assignments.add(mapResultSetToOrderEmployee(rs));
            }
            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех назначений", e);
        }
    }

    @Override
    public List<OrderEmployee> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM orderemployees WHERE order_id = ? ORDER BY employee_id";
        List<OrderEmployee> assignments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignments.add(mapResultSetToOrderEmployee(rs));
            }
            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска назначений по заказу", e);
        }
    }

    @Override
    public List<OrderEmployee> findByEmployeeId(Long employeeId) {
        String sql = "SELECT * FROM orderemployees WHERE employee_id = ? ORDER BY order_id";
        List<OrderEmployee> assignments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                assignments.add(mapResultSetToOrderEmployee(rs));
            }
            return assignments;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска назначений по сотруднику", e);
        }
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        String sql = "DELETE FROM orderemployees WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления назначений по заказу", e);
        }
    }

    private OrderEmployee mapResultSetToOrderEmployee(ResultSet rs) throws SQLException {
        return new OrderEmployee(
                Long.valueOf(rs.getLong("employee_id")),
                Long.valueOf(rs.getLong("order_id")),
                rs.getString("role_in_order")
        );
    }
}