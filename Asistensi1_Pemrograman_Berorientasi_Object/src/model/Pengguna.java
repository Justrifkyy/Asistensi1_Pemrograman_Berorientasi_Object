package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Pengguna {
    private final String nama;
    private final String username;
    private final String password;
    private final List<Order> daftarOrder;

    public Pengguna(String nama, String username, String password) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        }
        this.nama = nama.trim();
        this.username = username.trim();
        this.password = password.trim();
        this.daftarOrder = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Order> getDaftarOrder() {
        return new ArrayList<>(daftarOrder);
    }

    public void tambahOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order tidak boleh null.");
        }
        daftarOrder.add(order);
    }
}