package autoservice.jdbc.repository;

import autoservice.domain.model.Employee;
import autoservice.domain.repository.EmployeeRepository;
import autoservice.jdbc.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcEmployeeRepository implements EmployeeRepository {

    @Override
    public Employee save(Employee employee) {
        String sql;
        if (employee.getEmployeeId() == null) {
            sql = "INSERT INTO employees (first_name, last_name, middle_name, address, date_of_birth, phone_number, " +
                    "position, salary, experience, work_schedule, seniority_bonus) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING employee_id";
        } else {
            sql = "UPDATE employees SET first_name = ?, last_name = ?, middle_name = ?, address = ?, " +
                    "date_of_birth = ?, phone_number = ?, position = ?, salary = ?, experience = ?, " +
                    "work_schedule = ?, seniority_bonus = ? WHERE employee_id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getMiddleName());
            stmt.setString(4, employee.getAddress());
            stmt.setDate(5, Date.valueOf(employee.getDateOfBirth()));
            stmt.setString(6, employee.getPhoneNumber());
            stmt.setString(7, employee.getPosition());
            stmt.setDouble(8, employee.getSalary());
            stmt.setInt(9, employee.getExperience());
            stmt.setString(10, employee.getWorkSchedule());
            stmt.setDouble(11, employee.getSeniorityBonus());

            if (employee.getEmployeeId() == null) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    employee.setEmployeeId(Long.valueOf(rs.getLong("employee_id")));
                }
            } else {
                stmt.setLong(12, employee.getEmployeeId());
                stmt.executeUpdate();
            }

            return employee;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения сотрудника", e);
        }
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToEmployee(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска сотрудника по ID", e);
        }
    }

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees ORDER BY employee_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
            return employees;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех сотрудников", e);
        }
    }

    @Override
    public List<Employee> findByPosition(String position) {
        String sql = "SELECT * FROM employees WHERE position = ? ORDER BY employee_id";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, position);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
            return employees;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска сотрудников по должности", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления сотрудника", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM employees WHERE employee_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования сотрудника", e);
        }
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        return new Employee(
                Long.valueOf(rs.getLong("employee_id")),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("middle_name"),
                rs.getString("address"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("phone_number"),
                rs.getString("position"),
                Double.valueOf(rs.getDouble("salary")),
                Integer.valueOf(rs.getInt("experience")),
                rs.getString("work_schedule"),
                Double.valueOf(rs.getDouble("seniority_bonus"))
        );
    }
}