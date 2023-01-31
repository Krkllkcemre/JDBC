import java.sql.*;

public class JDBS02_Query01
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        /*=======================================================================
       	 ORNEK1: Id'si 1'den buyuk firmalarin ismini ve iletisim_isim'ini isim
       	 ters sirali yazdirin.
      	========================================================================*/

        /*
        CREATE TABLE firmalar
        (
        id INT,
        isim VARCHAR(20),
        iletisim_isim VARCHAR(20),
        CONSTRAINT firmalar_pk PRIMARY KEY (id, isim)
        );

       INSERT INTO firmalar VALUES
        (1, 'ACB', 'Ali Can'),
        (2, 'RDB', 'Veli Gul'),
        (3, 'KMN', 'Ayse Gulmez');
         */
        System.out.println("======================= ORNEK 1 ===========================");

        String selectquery = "SELECT isim, iletişim_isim " +
                             "FROM firmalar " +
                             "WHERE id>1 " +
                             "ORDER BY isim DESC";



        ResultSet data = st.executeQuery(selectquery);

        while(data.next()){
            System.out.println(data.getString("isim") + " " +
                    data.getString("iletişim_isim"));
        }

        /*=======================================================================
       	 ORNEK2: Iletisim isminde 'li' iceren firmalarin id'lerini ve isim'ini
       	  id sirali yazdirin.
      	========================================================================*/

        System.out.println("======================= ORNEK 2 ===========================");

        String selectQuery3 = "SELECT id, isim FROM firmalar WHERE iletişim_isim LIKE '%li%' ORDER BY id";

        ResultSet data2 = st.executeQuery(selectQuery3);

        while(data2.next()) {
            System.out.println(data2.getInt("id") + " " +
                    data2.getString("isim"));


        }

    }
}