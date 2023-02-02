import java.sql.*;

public class JDBC02_execute_executeUpdate
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {


        /*
    A) CREATE TABLE, DROP TABLE, ALTER TABLE gibi DDL ifadeleri icin sonuc kümesi (ResultSet)
       dondurmeyen metotlar kullanilmalidir. Bunun icin JDBC'de 2 alternatif bulunmaktadir.
        1) execute() metodu
        2) executeUpdate() metodu.
    B) - execute() metodu her tur SQL ifadesiyle kullanilabilen genel bir komuttur.
       - execute(), Boolean bir deger dondurur. DDL islemlerinde false dondururken,
         DML islemlerinde true deger dondurur.
       - Ozellikle, hangi tip SQL ifadesinin kullanilmasinin uygun oldugununn bilinemedigi
         durumlarda tercih edilmektedir.
    C) - executeUpdate() metodu ise INSERT, Update gibi DML islemlerinde yaygin kullanilir.
       - bu islemlerde islemden etkilenen satir sayisini dondurur.
       - Ayrıca, DDL islemlerinde de kullanilabilir ve bu islemlerde 0 dondurur.
 */
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

           /*======================================================================
                  ORNEK1: ogrenciler tablosunu siliniz.
           ========================================================================*/


        String dropQuery ="drop table calışan";

        // System.out.println(st.execute(dropQuery));

        if (!st.execute(dropQuery)){
            System.out.println("Calışan tablosu silindi!");
        }


        /*=======================================================================
          ORNEK2: calışan adinda bir tablo olusturunuz id int,
          birim VARCHAR(10), maas int
	    ========================================================================*/

       String createTable = "CREATE TABLE calışan" +
                             "(id INT, " +
                             "birim VARCHAR(10), " +
                             "maas INT)";

        if (!st.execute(createTable)){
            System.out.println("Calışanlar tablosu olusturuldu!");
        }



        /*=======================================================================
		  ORNEK3: isciler tablosuna yeni bir kayit (80, 'ARGE', 4000)
		  ekleyelim.
		========================================================================*/
        String insertData = "INSERT INTO calışan VALUES(80, 'ARGE', 4000)";

        System.out.println("Islemden etkilenen satir sayisi : " + st.executeUpdate(insertData));


        /*=======================================================================
          ORNEK4: Çalışan tablosuna birden fazla yeni kayıt ekleyelim.
            INSERT INTO isciler VALUES(70, 'HR', 5000)
            INSERT INTO isciler VALUES(60, 'LAB', 3000)
            INSERT INTO isciler VALUES(50, 'ARGE', 4000)
         ========================================================================*/

        String [] queries={" INSERT INTO calışan VALUES(70, 'HR', 5000)"
                            , "INSERT INTO calışan VALUES(60, 'LAB', 3000)"
                            , "INSERT INTO calışan VALUES(50, 'ARGE', 4000)"};

        int count=0;
        for (String each: queries) {
            count+=st.executeUpdate(each);
        }
        System.out.println(count + "Satır eklendi.");

        // Ayri ayri sorgular ile veritabanina tekrar tekrar ulasmak islemlerin
        // yavas yapilmasina yol acar. 10000 tane veri kaydi yapildigi dusunuldugunde
        // bu kotu bir yaklasimdir.

        System.out.println("=============== 2. Yontem ==============");

        // 2.YONTEM (addBatch ve executeBatch() metotlari ile)
        // ----------------------------------------------------
        // addBatch metodu ile SQL ifadeleri gruplandirilabilir ve executeBatch()
        // metodu ile veritabanina bir kere gonderilebilir.
        // executeBatch() metodu bir int [] dizi dondurur. Bu dizi her bir ifade sonucunda
        // etkilenen satir sayisini gosterir.

        String [] queries2 = {"INSERT INTO calışan VALUES(10, 'TEKNIK', 3000)",
                              "INSERT INTO calışan VALUES(20, 'KANTIN', 2000)",
                               "INSERT INTO calışan VALUES(30, 'ARGE', 5000)"};

        for (String each : queries2) { // Bu dongude her bir SQL komutunu torbaya atiyor
            st.addBatch(each);
        }

        st.executeBatch(); // Burada da tek seferde tum torbayi goturup Database'e isliyor

        System.out.println("Satirlar eklendi");
   /*=======================================================================
	      ORNEK5: calışan tablosuna goruntuleyin.
	     ========================================================================*/

        System.out.println("================ Calışan Tablosu ================");
        
        String selectQuery="select*from calışan";

        ResultSet calışanTablosu=st.executeQuery(selectQuery);

        while (calışanTablosu.next())
        {
            System.out.println(calışanTablosu.getInt(1) + " "
                              + calışanTablosu.getString(2) + " "
                              + calışanTablosu.getInt(3));
        }

        /*=======================================================================
		  ORNEK6: isciler tablosundaki maasi 5000'den az olan iscilerin maasina
		   %10 zam yapiniz.
		========================================================================*/

        String updateQuary= "update calışan set maas=maas*1.1 where maas<5000";

        int satir= st.executeUpdate(updateQuary);

        System.out.println(satir + " satir guncellendi!");


        System.out.println("================ Isciler Tablosu Maas Zamlari ================");

        ResultSet calışanlarTablosu2=st.executeQuery(selectQuery);

        while (calışanlarTablosu2.next())
        {
            System.out.println(calışanTablosu.getInt(1) + " "
                    + calışanTablosu.getString(2) + " "
                    + calışanTablosu.getInt(3));
        }

        /*=======================================================================
          ORNEK8: Isciler tablosundan birimi 'ARGE' olan iscileri siliniz.
         ========================================================================*/



        con.close();
        st.close();
        calışanTablosu.close();
        calışanlarTablosu2.close();

    }
}
