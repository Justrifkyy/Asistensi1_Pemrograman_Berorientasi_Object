package manager;

import model.Produk;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final List<Produk> daftarProduk;

    public ProductManager() {
        this.daftarProduk = new ArrayList<>();
    }

    public void tambahProduk(String nama, int harga, int stok) {
        daftarProduk.add(new Produk(nama, harga, stok));
    }

    public void tambahStok(int index, int jumlah) {
        if (index < 0 || index >= daftarProduk.size()) {
            throw new IllegalArgumentException("Produk tidak ditemukan.");
        }
        daftarProduk.get(index).tambahStok(jumlah);
    }

    public void editProduk(int index, String nama, int harga, int stok) {
        if (index < 0 || index >= daftarProduk.size()) {
            throw new IllegalArgumentException("Produk tidak ditemukan.");
        }
        Produk p = daftarProduk.get(index);
        p.setNama(nama);
        p.setHarga(harga);
        p.setStok(stok);
    }

    public void hapusProduk(int index) {
        if (index < 0 || index >= daftarProduk.size()) {
            throw new IllegalArgumentException("Produk tidak ditemukan.");
        }
        daftarProduk.remove(index);
    }

    public List<Produk> getDaftarProduk() {
        return new ArrayList<>(daftarProduk);
    }
}