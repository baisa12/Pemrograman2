package manajemenproduk;

import java.util.Stack;

public class StackProduk {

    Stack<Produk> stack = new Stack<>();

    public void tambahProduk(Produk p) {
        stack.push(p);
        System.out.println("Produk ditambahkan : " + p.nama);
    }

    public Produk hapusProduk() throws Exception {
        if (stack.isEmpty()) {
            throw new Exception("Stok kosong!");
        }

        Produk p = stack.pop();
        System.out.println("Produk dihapus : " + p.nama);

        return p;
    }

    public Stack<Produk> getData() {
        return stack;
    }
}