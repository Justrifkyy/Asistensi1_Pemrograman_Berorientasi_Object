package manager;

import model.Transaksi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionManager {
    private final Map<String, List<Transaksi>> transaksiPerPengguna;

    public TransactionManager() {
        this.transaksiPerPengguna = new HashMap<>();
    }

    public void tambahTransaksi(Transaksi transaksi) {
        if (transaksi == null) {
            throw new IllegalArgumentException("Transaksi tidak boleh null.");
        }
        String username = transaksi.getUsername();
        transaksiPerPengguna.computeIfAbsent(username, k -> new ArrayList<>()).add(transaksi);
    }

    public List<Transaksi> getTransaksiByUsername(String username) {
        if (username == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(transaksiPerPengguna.getOrDefault(username.trim(), new ArrayList<>()));
    }

    public List<Transaksi> getSemuaTransaksi() {
        List<Transaksi> semuaTransaksi = new ArrayList<>();
        for (List<Transaksi> transaksiList : transaksiPerPengguna.values()) {
            semuaTransaksi.addAll(transaksiList);
        }
        return semuaTransaksi;
    }

    public Map<String, List<Transaksi>> getTransaksiPerPengguna() {
        return new HashMap<>(transaksiPerPengguna);
    }
}