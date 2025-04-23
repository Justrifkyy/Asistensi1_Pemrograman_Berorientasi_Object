package model;

import java.util.ArrayList;
import java.util.List;

public class Transaksi {
    private final String username;
    private final List<Order> daftarOrder;

    public Transaksi(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }
        this.username = username.trim();
        this.daftarOrder = new ArrayList<>();
    }

    public void tambahOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order tidak boleh null.");
        }
        daftarOrder.add(order);
    }

    public String getUsername() {
        return username;
    }

    public List<Order> getDaftarOrder() {
        return new ArrayList<>(daftarOrder);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pembelanjaan oleh ").append(username).append(":\n");
        for (Order o : daftarOrder) {
            sb.append("  - ").append(o).append("\n");
        }
        return sb.toString();
    }
}