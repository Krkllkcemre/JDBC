import java.sql.*;

public class JDBS01_Query01
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1 - Ilgili Driver'i yuklemeliyiz. MySQL kullandigimizi bildiriyoruz.
        // Driver'i bulamama ihtimaline karsi forName metodu icin ClassNotFoundException
        // method signature'imiza exception olarak firlatmamizi istiyor.


        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2 . Baglantıyı oluşturmak için username ve password girmeliyiz.

        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");

        // 3 - SQL query'leri icin bir Statement objesi olusturup , javada SQL
        // sorgularimiz icin bir alan acacagiz.


        Statement st= con.createStatement();

        // 4- SQL guery'lerimiz yazıp çalıştımalıyız.

        ResultSet veri= st.executeQuery("select*from personel");

        //Sonuçları göre bilmek için iteration ile set içerisindeki elemanlari
        //While döngüsü ile yazdıralım

        while (veri.next())
        {
            System.out.println(veri.getInt(1) + " " + veri.getString(2)+ " "+veri.getString(3)
                    + " " +veri.getInt(4) + " " +veri.getString(5) );
        }

        //Ooluşturulan baglantıları kapatalım.

        con.close();
        st.close();
        veri.close();

    }
}
