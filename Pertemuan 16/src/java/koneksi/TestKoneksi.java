package koneksi;

import java.sql.Connection;

public class TestKoneksi {

    public static void main(String[] args) {

        KoneksiDB koneksi =
        new KoneksiDB();

        Connection conn =
        koneksi.getKoneksi();

        if(conn != null){

            System.out.println(
            "TEST DATABASE : PASS"
            );

            System.out.println(
            "Database berhasil terkoneksi"
            );

        }else{

            System.out.println(
            "TEST DATABASE : FAIL"
            );

            System.out.println(
            "Database gagal terkoneksi"
            );

        }

    }

}