package manager;

import model.Pengguna;
import model.Admin;
import model.UserBiasa;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private final List<Pengguna> daftarPengguna;

    public UserManager() {
        this.daftarPengguna = new ArrayList<>();
    }

    public void signUp(String nama, String username, String password, boolean isAdmin) {
        // Validasi input
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username tidak boleh kosong.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password tidak boleh kosong.");
        }
        // Validasi username unik
        for (Pengguna p : daftarPengguna) {
            if (p.getUsername().equals(username.trim())) {
                throw new IllegalArgumentException("Username sudah digunakan, silakan pilih username lain.");
            }
        }
        Pengguna pengguna = isAdmin ? new Admin(nama, username, password) : new UserBiasa(nama, username, password);
        daftarPengguna.add(pengguna);
    }

    public Pengguna signIn(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        for (Pengguna p : daftarPengguna) {
            if (p.getUsername().equals(username.trim()) && p.getPassword().equals(password.trim())) {
                return p;
            }
        }
        return null;
    }
}