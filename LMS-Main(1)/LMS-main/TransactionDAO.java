package dao;

import model.Transaction;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public void addTransaction(Transaction transaction) {
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "INSERT INTO transactions (book_id, member_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, transaction.getBookId());
                stmt.setInt(2, transaction.getMemberId());
                stmt.setDate(3, new java.sql.Date(transaction.getBorrowDate().getTime()));
                stmt.setDate(4, new java.sql.Date(transaction.getReturnDate().getTime()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByMember(int memberId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE member_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, memberId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(rs.getInt("transaction_id"));
                    transaction.setBookId(rs.getInt("book_id"));
                    transaction.setMemberId(rs.getInt("member_id"));
                    transaction.setBorrowDate(rs.getDate("borrow_date"));
                    transaction.setReturnDate(rs.getDate("return_date"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Other methods for borrowing/returning operations
}
