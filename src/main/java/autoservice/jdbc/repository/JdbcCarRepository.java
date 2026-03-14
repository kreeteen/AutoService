package autoservice.jdbc.repository;

import autoservice.domain.model.Car;
import autoservice.domain.repository.CarRepository;
import autoservice.jdbc.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCarRepository implements CarRepository {

    @Override
    public Car save(Car car) {
        String sql;
        if (car.getCarId() == null) {
            sql = "INSERT INTO cars (client_id, license_plate, brand, model, manufacture_date) " +
                    "VALUES (?, ?, ?, ?, ?) RETURNING car_id";
        } else {
            sql = "UPDATE cars SET client_id = ?, license_plate = ?, brand = ?, model = ?, " +
                    "manufacture_date = ? WHERE car_id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, car.getClientId());
            stmt.setString(2, car.getLicensePlate());
            stmt.setString(3, car.getBrand());
            stmt.setString(4, car.getModel());
            stmt.setDate(5, Date.valueOf(car.getManufactureDate()));

            if (car.getCarId() == null) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    car.setCarId(Long.valueOf(rs.getLong("car_id")));
                }
            } else {
                stmt.setLong(6, car.getCarId());
                stmt.executeUpdate();
            }

            return car;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения автомобиля", e);
        }
    }

    @Override
    public Optional<Car> findById(Long id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToCar(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска автомобиля по ID", e);
        }
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM cars ORDER BY car_id";
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
            return cars;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех автомобилей", e);
        }
    }

    @Override
    public List<Car> findByClientId(Long clientId) {
        String sql = "SELECT * FROM cars WHERE client_id = ? ORDER BY car_id";
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, clientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
            return cars;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска автомобилей клиента", e);
        }
    }

    @Override
    public List<Car> findByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM cars WHERE license_plate = ?";
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, licensePlate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cars.add(mapResultSetToCar(rs));
            }
            return cars;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска автомобилей по номерному знаку", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления автомобиля", e);
        }
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM cars WHERE car_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки существования автомобиля", e);
        }
    }

    private Car mapResultSetToCar(ResultSet rs) throws SQLException {
        return new Car(
                Long.valueOf(rs.getLong("car_id")),
                Long.valueOf(rs.getLong("client_id")),
                rs.getString("license_plate"),
                rs.getString("brand"),
                rs.getString("model"),
                rs.getDate("manufacture_date").toLocalDate()
        );
    }
}