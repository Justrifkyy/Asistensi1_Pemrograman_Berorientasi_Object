package ui;

import java.util.ArrayList;
import manager.ProductManager;
import manager.TransactionManager;
import manager.UserManager;
import model.Order;
import model.Pengguna;
import model.Produk;
import model.Transaksi;
import model.UserBiasa;
import model.Admin;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner input;
    private final UserManager userManager;
    private final ProductManager productManager;
    private final TransactionManager transactionManager;

    public ConsoleUI(UserManager userManager, ProductManager productManager, TransactionManager transactionManager) {
        this.input = new Scanner(System.in);
        this.userManager = userManager;
        this.productManager = productManager;
        this.transactionManager = transactionManager;
    }

    public void start() {
        int pilihan;
        do {
            System.out.println("\n=== Menu Utama ===");
            System.out.println("1. Sign Up");
            System.out.println("2. Sign In");
            System.out.println("3. Lihat Katalog");
            System.out.println("4. Keluar");
            System.out.print("Pilih menu: ");
            try {
                pilihan = input.nextInt();
                input.nextLine();
                switch (pilihan) {
                    case 1:
                        menuDaftar();
                        break;
                    case 2:
                        menuMasuk();
                        break;
                    case 3:
                        tampilkanProduk();
                        break;
                    case 4:
                        System.out.println("Program selesai.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Pilihan tidak valid.");
                input.nextLine();
                pilihan = 0;
            }
        } while (pilihan != 4);
    }

    private void menuDaftar() {
        int pilih;
        do {
            System.out.println("\nDaftar Sebagai:");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Batal");
            System.out.print("Pilih: ");
            try {
                pilih = input.nextInt();
                input.nextLine();
                if (pilih == 3) {
                    return;
                }
                if (pilih != 1 && pilih != 2) {
                    System.out.println("Pilihan tidak valid.");
                    continue;
                }

                System.out.print("Nama: ");
                String nama = input.nextLine();
                if (nama.trim().isEmpty()) {
                    System.out.println("Nama tidak boleh kosong.");
                    continue;
                }

                String username;
                boolean usernameValid = false;
                do {
                    System.out.print("Username: ");
                    username = input.nextLine();
                    if (username.trim().isEmpty()) {
                        System.out.println("Username tidak boleh kosong.");
                        continue;
                    }
                    try {
                        // Cek username tanpa mendaftar
                        for (Pengguna p : userManager.signIn(username, "") == null ? new ArrayList<Pengguna>() : List.of(userManager.signIn(username, ""))) {
                            if (p.getUsername().equals(username.trim())) {
                                throw new IllegalArgumentException("Username sudah digunakan, silakan pilih username lain.");
                            }
                        }
                        usernameValid = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!usernameValid);

                System.out.print("Password: ");
                String password = input.nextLine();
                if (password.trim().isEmpty()) {
                    System.out.println("Password tidak boleh kosong.");
                    continue;
                }

                userManager.signUp(nama, username, password, pilih == 1);
                System.out.println("Pendaftaran berhasil.");
                System.out.print("Ingin mendaftar lagi? (Ya/Tidak): ");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                pilih = 0;
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                input.nextLine();
                pilih = 0;
            }
        } while (pilih != 3 && !input.nextLine().equalsIgnoreCase("Tidak"));
    }

    private void menuMasuk() {
        System.out.print("Masukkan Username: ");
        String username = input.nextLine();
        System.out.print("Masukkan Password: ");
        String password = input.nextLine();

        Pengguna loginUser = userManager.signIn(username, password);
        if (loginUser == null) {
            System.out.println("Username atau password salah.");
            return;
        }

        menuSignin(loginUser);
    }

    private void menuSignin(Pengguna loginUser) {
        int pilih;
        do {
            System.out.println("\n=== Menu Setelah Login ===");
            System.out.println("1. Sign-Out");
            System.out.println("2. Product Katalog");
            System.out.println("3. Order Management");
            System.out.print("Pilih menu: ");
            try {
                pilih = input.nextInt();
                input.nextLine();
                switch (pilih) {
                    case 1:
                        System.out.println("Sign-Out berhasil.");
                        return;
                    case 2:
                        if (loginUser instanceof Admin) {
                            kelolaProduk();
                        } else if (loginUser instanceof UserBiasa) {
                            tampilkanProduk();
                        }
                        break;
                    case 3:
                        if (loginUser instanceof Admin admin) {
                            menuAdmin(admin);
                        } else if (loginUser instanceof UserBiasa user) {
                            menuUser(user);
                        }
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Pilihan tidak valid.");
                input.nextLine();
                pilih = 0;
            }
        } while (true);
    }

    private void menuAdmin(Admin admin) {
        System.out.println("\n=== Semua Transaksi ===");
        Map<String, List<Transaksi>> transaksiPerPengguna = transactionManager.getTransaksiPerPengguna();
        if (transaksiPerPengguna.isEmpty()) {
            System.out.println("Belum ada transaksi.");
        } else {
            for (Map.Entry<String, List<Transaksi>> entry : transaksiPerPengguna.entrySet()) {
                String username = entry.getKey();
                List<Transaksi> transaksiList = entry.getValue();
                int no = 1;
                for (Transaksi t : transaksiList) {
                    System.out.println("\nTransaksi #" + no++ + " (" + username + ")");
                    System.out.println(t);
                }
            }
        }
    }

    private void kelolaProduk() {
        int pilih = 0;
        do {
            System.out.println("\n=== Kelola Produk ===");
            System.out.println("1. Tambah Produk Baru");
            System.out.println("2. Tambah Stok Produk");
            System.out.println("3. Edit Produk");
            System.out.println("4. Hapus Produk");
            System.out.println("5. Lihat Produk");
            System.out.println("6. Kembali");
            System.out.print("Pilih menu: ");
            try {
                pilih = input.nextInt();
                input.nextLine();
                switch (pilih) {
                    case 1:
                        System.out.print("Nama produk: ");
                        String nama = input.nextLine();
                        System.out.print("Harga: ");
                        int harga = input.nextInt();
                        System.out.print("Stok: ");
                        int stok = input.nextInt();
                        input.nextLine();
                        productManager.tambahProduk(nama, harga, stok);
                        System.out.println("Produk berhasil ditambahkan.");
                        break;
                    case 2:
                        tampilkanProduk();
                        System.out.print("Pilih produk: ");
                        int index = input.nextInt() - 1;
                        System.out.print("Jumlah tambahan stok: ");
                        int jumlah = input.nextInt();
                        input.nextLine();
                        productManager.tambahStok(index, jumlah);
                        System.out.println("Stok berhasil ditambahkan.");
                        break;
                    case 3:
                        tampilkanProduk();
                        System.out.print("Pilih produk: ");
                        int idx = input.nextInt() - 1;
                        input.nextLine();
                        System.out.print("Nama baru: ");
                        String namaBaru = input.nextLine();
                        System.out.print("Harga baru: ");
                        int hargaBaru = input.nextInt();
                        System.out.print("Stok baru: ");
                        int stokBaru = input.nextInt();
                        input.nextLine();
                        productManager.editProduk(idx, namaBaru, hargaBaru, stokBaru);
                        System.out.println("Produk berhasil diperbarui.");
                        break;
                    case 4:
                        tampilkanProduk();
                        System.out.print("Pilih produk: ");
                        int hapus = input.nextInt() - 1;
                        input.nextLine();
                        productManager.hapusProduk(hapus);
                        System.out.println("Produk berhasil dihapus.");
                        break;
                    case 5:
                        tampilkanProduk();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                input.nextLine();
            }
        } while (pilih != 6);
    }

    private void menuUser(UserBiasa user) {
        int pilih;
        do {
            System.out.println("\n=== Menu Orderan User ===");
            System.out.println("1. Tambah Orderan");
            System.out.println("2. Lihat Orderan Saya");
            System.out.println("3. Kembali");
            System.out.print("Pilih menu: ");
            try {
                pilih = input.nextInt();
                input.nextLine();
                switch (pilih) {
                    case 1:
                        tambahOrderan(user);
                        break;
                    case 2:
                        lihatOrderan(user);
                        break;
                    case 3:
                        System.out.println("Kembali ke menu sebelumnya.");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.out.println("Pilihan tidak valid.");
                input.nextLine();
                pilih = 0;
            }
        } while (true);
    }

    private void tambahOrderan(UserBiasa user) {
        Transaksi transaksiBaru = new Transaksi(user.getUsername());
        boolean lanjutOrder = true;

        while (lanjutOrder) {
            tampilkanProduk();
            System.out.print("Pilih produk (nomor): ");
            try {
                int idx = input.nextInt() - 1;
                input.nextLine();
                if (idx < 0 || idx >= productManager.getDaftarProduk().size()) {
                    System.out.println("Produk tidak valid.");
                    continue;
                }

                Produk p = productManager.getDaftarProduk().get(idx);
                System.out.print("Jumlah: ");
                int jumlah = input.nextInt();
                input.nextLine();

                p.kurangiStok(jumlah);
                Order order = new Order(p.getNama(), jumlah);
                transaksiBaru.tambahOrder(order);
                user.tambahOrder(order);
                System.out.println("Produk berhasil ditambahkan ke keranjang.");

                System.out.print("Ingin menambah produk lain? (ya/tidak): ");
                String lagi = input.nextLine();
                if (!lagi.equalsIgnoreCase("ya")) {
                    lanjutOrder = false;
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Input tidak valid.");
                input.nextLine();
            }
        }

        if (!transaksiBaru.getDaftarOrder().isEmpty()) {
            transactionManager.tambahTransaksi(transaksiBaru);
            tampilkanTransaksiBaru(user, transaksiBaru);
        } else {
            System.out.println("Tidak ada produk yang dipesan.");
        }
    }

    private void lihatOrderan(UserBiasa user) {
        System.out.println("\n=== Riwayat Pembelanjaan Anda ===");
        List<Transaksi> transaksiUser = transactionManager.getTransaksiByUsername(user.getUsername());
        if (transaksiUser.isEmpty()) {
            System.out.println("Belum ada pembelanjaan.");
        } else {
            int count = 1;
            for (Transaksi t : transaksiUser) {
                System.out.println("====== Orderan ke-" + count + " (" + user.getUsername() + ") ======");
                for (Order o : t.getDaftarOrder()) {
                    System.out.println("  - " + o.getNamaProduk() + " x" + o.getJumlah());
                }
                count++;
            }
        }
    }

    private void tampilkanTransaksiBaru(UserBiasa user, Transaksi transaksi) {
        List<Transaksi> transaksiUser = transactionManager.getTransaksiByUsername(user.getUsername());
        int urutan = transaksiUser.indexOf(transaksi) + 1;

        System.out.println("\n====== Orderan ke-" + urutan + " (" + user.getUsername() + ") ======");
        int total = 0;
        for (Order o : transaksi.getDaftarOrder()) {
            for (Produk p : productManager.getDaftarProduk()) {
                if (p.getNama().equals(o.getNamaProduk())) {
                    int subtotal = p.getHarga() * o.getJumlah();
                    System.out.println("- " + o.getNamaProduk() + " x" + o.getJumlah() + " = Rp" + subtotal);
                    total += subtotal;
                    break;
                }
            }
        }
        System.out.println("Total Belanja: Rp" + total);
    }

    private void tampilkanProduk() {
        System.out.println("\n=== Daftar Produk ===");
        List<Produk> produk = productManager.getDaftarProduk();
        if (produk.isEmpty()) {
            System.out.println("Belum ada produk.");
        } else {
            for (int i = 0; i < produk.size(); i++) {
                System.out.println((i + 1) + ". " + produk.get(i));
            }
        }
    }
}