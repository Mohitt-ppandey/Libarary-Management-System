package dao;

import model.Member;
import util.DBConnection;

import java.sql.*;

public class MemberDAO {

    public void addMember(Member member) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO members (name, email, membership_id) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, member.getName());
                stmt.setString(2, member.getEmail());
                stmt.setString(3, member.getMembershipId());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Other methods like updateMember, deleteMember, etc.
}
