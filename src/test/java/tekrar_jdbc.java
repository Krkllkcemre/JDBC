import java.sql.*;

public class tekrar_jdbc
{
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sys?serverTimezone=UTC", "root", "1234");
        Statement st = con.createStatement();

        String dropÖgrenci="drop table ögrenci";

        if(!st.execute(dropÖgrenci))
        {
            System.out.println("Ögrenci tablosu silindi");
        }

        String createÖgrenci ="create table ögrenci (id int, isim varchar(20) , sınıf char(4) , bölüm varchar(10)) ";

        if (!st.execute(createÖgrenci))
        {
            System.out.println("Ögrenci tablosu oluşturuldu");
        }

        String insertData="insert into ögrenci values (101,'emre','11','testing')";
        //ögrenci tablosuna bir satır eklendi

        String [] gueries={" insert into ögrenci values (102,'sena','10','edebiyat') ", " insert into ögrenci values (103,'nuh','10','makine') "
                        ," insert into ögrenci values (104,'ali','11','develop') ", " insert into ögrenci values (105,'nur','10','tıp')"};
        //ögrenci satırına 4 adet eklendi
        int count=0;
        for (String each:gueries)
        {count+=st.executeUpdate(each);}
        //Kaç adet eklendigi yazılır

        //Yazdırmak istersek
        System.out.println("================ Ögrenci Tablosu ================");

        String selectÖgrenci="select* from ögrenci";
        ResultSet ögrenciTablosu=st.executeQuery(selectÖgrenci);

        while (ögrenciTablosu.next())
        {
            System.out.println(ögrenciTablosu.getInt(1) + " " + ögrenciTablosu.getString(2) + " " + ögrenciTablosu.getString(3) + " " + ögrenciTablosu.getString(4));
        }

        con.close();
        st.close();
        ögrenciTablosu.close();

    }
}
