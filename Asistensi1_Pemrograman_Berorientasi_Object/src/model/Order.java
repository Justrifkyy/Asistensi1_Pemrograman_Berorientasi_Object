package model;

public class Order {
    private final String namaProduk;
    private final int jumlah;

    public Order(String namaProduk, int jumlah) {
        if (namaProduk == null || namaProduk.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong.");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus positif.");
        }
        this.namaProduk = namaProduk.trim();
        this.jumlah = jumlah;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public int getJumlah() {
        return jumlah;
    }

    @Override
    public String toString() {
        return namaProduk + " x" + jumlah;
    }
}