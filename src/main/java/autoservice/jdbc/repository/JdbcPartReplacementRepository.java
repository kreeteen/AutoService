package autoservice.jdbc.repository;

import autoservice.domain.model.PartReplacement;
import autoservice.domain.repository.PartReplacementRepository;
import autoservice.jdbc.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPartReplacementRepository implements PartReplacementRepository {

    @Override
    public PartReplacement save(PartReplacement partReplacement) {
        String sql;
        if (partReplacement.getReplacementId() == null) {
            sql = "INSERT INTO partreplacements (order_id, part_name, part_number, quantity) " +
                    "VALUES (?, ?, ?, ?) RETURNING replacement_id";
        } else {
            sql = "UPDATE partreplacements SET order_id = ?, part_name = ?, part_number = ?, " +
                    "quantity = ? WHERE replacement_id = ?";
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, partReplacement.getOrderId());
            stmt.setString(2, partReplacement.getPartName());
            stmt.setString(3, partReplacement.getPartNumber());
            stmt.setInt(4, partReplacement.getQuantity());

            if (partReplacement.getReplacementId() == null) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    partReplacement.setReplacementId(Long.valueOf(rs.getLong("replacement_id")));
                }
            } else {
                stmt.setLong(5, partReplacement.getReplacementId());
                stmt.executeUpdate();
            }

            return partReplacement;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения запчасти", e);
        }
    }

    @Override
    public List<PartReplacement> findAll() {
        String sql = "SELECT * FROM partreplacements ORDER BY replacement_id";
        List<PartReplacement> parts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                parts.add(mapResultSetToPartReplacement(rs));
            }
            return parts;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех запчастей", e);
        }
    }

    @Override
    public List<PartReplacement> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM partreplacements WHERE order_id = ? ORDER BY replacement_id";
        List<PartReplacement> parts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                parts.add(mapResultSetToPartReplacement(rs));
            }
            return parts;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска запчастей по заказу", e);
        }
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        String sql = "DELETE FROM partreplacements WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления запчастей по заказу", e);
        }
    }

    private PartReplacement mapResultSetToPartReplacement(ResultSet rs) throws SQLException {
        return new PartReplacement(
                Long.valueOf(rs.getLong("replacement_id")),
                Long.valueOf(rs.getLong("order_id")),
                rs.getString("part_name"),
                rs.getString("part_number"),
                rs.getInt("quantity")
        );
    }
}