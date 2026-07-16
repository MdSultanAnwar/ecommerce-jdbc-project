package com.ecommerce.dao;

import com.ecommerce.model.Cart;
import com.ecommerce.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /**
     * Adds a product to a customer's cart. Login required (customerId must be
     * a real, already-registered/found customer). If the product is already in
     * the cart, quantity is incremented instead of creating a duplicate row.
     * @throws ClassNotFoundException 
     */
    public void addToCart(int customerId, int productId, int quantity) throws SQLException, ClassNotFoundException {
        try (Connection conn = DBConnection.getConnection()) {
            String checkSql = "SELECT cart_id, quantity FROM cart WHERE customer_id = ? AND product_id = ?";
            try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                checkPs.setInt(1, customerId);
                checkPs.setInt(2, productId);
                try (ResultSet rs = checkPs.executeQuery()) {
                    if (rs.next()) {
                        int existingCartId = rs.getInt("cart_id");
                        int newQty = rs.getInt("quantity") + quantity;
                        String updateSql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
                        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                            updatePs.setInt(1, newQty);
                            updatePs.setInt(2, existingCartId);
                            updatePs.executeUpdate();
                        }
                        return;
                    }
                }
            }

            String insertSql = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
            try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                insertPs.setInt(1, customerId);
                insertPs.setInt(2, productId);
                insertPs.setInt(3, quantity);
                insertPs.executeUpdate();
            }
        }
    }

    public void removeFromCart(int customerId, int productId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }

    /** Returns cart rows joined with product name/price for a nice display. 
     * @throws ClassNotFoundException */
    public List<Cart> viewCart(int customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT c.*, p.product_name, p.price FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.customer_id = ?";
        List<Cart> items = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(rs.getInt("cart_id"));
                    cart.setCustomerId(rs.getInt("customer_id"));
                    cart.setProductId(rs.getInt("product_id"));
                    cart.setQuantity(rs.getInt("quantity"));
                    cart.setCreatedAt(rs.getTimestamp("created_at"));
                    cart.setUpdatedAt(rs.getTimestamp("updated_at"));
                    cart.setProductName(rs.getString("product_name"));
                    cart.setPrice(rs.getDouble("price"));
                    items.add(cart);
                }
            }
        }
        return items;
    }

    /** Same as viewCart but runs WITHIN an existing transaction (used by placeOrder). */
    List<Cart> viewCart(Connection conn, int customerId) throws SQLException {
        String sql = "SELECT c.*, p.product_name, p.price FROM cart c " +
                "JOIN products p ON c.product_id = p.product_id " +
                "WHERE c.customer_id = ?";
        List<Cart> items = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cart cart = new Cart();
                    cart.setCartId(rs.getInt("cart_id"));
                    cart.setCustomerId(rs.getInt("customer_id"));
                    cart.setProductId(rs.getInt("product_id"));
                    cart.setQuantity(rs.getInt("quantity"));
                    cart.setProductName(rs.getString("product_name"));
                    cart.setPrice(rs.getDouble("price"));
                    items.add(cart);
                }
            }
        }
        return items;
    }

    /** Empties a customer's cart after a successful order - WITHIN an existing transaction. */
    void clearCart(Connection conn, int customerId) throws SQLException {
        String sql = "DELETE FROM cart WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        }
    }
}
