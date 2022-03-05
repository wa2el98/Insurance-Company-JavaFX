package connectivity;

import Home.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;



import java.sql.*;

public class ConnectionClass {

    static Connection conn = null;
    

    public ConnectionClass() {
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException { 

        try {
            if(conn==null) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn= DriverManager.getConnection("jdbc:mysql://localhost/proj1?serverTimezone=IST", "root", "wael1998");
                System.out.println("SQL connection succeed");
            }
        }catch(Exception e){System.out.println(e);}
           return conn;
    }

    public static ObservableList<Customers> getData() throws SQLException, ClassNotFoundException {

        Connection connection1 = getConnection();

        ObservableList<Customers> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps =connection1.prepareStatement("SELECT * FROM proj1.ClientDetails");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                list.add(new Customers(rs.getString("name"),rs.getString("lastName"),rs.getString("date"),rs.getString("remarks"),rs.getString("insurance")));
            }

        }catch (Exception e) {System.out.println(e);}

        return list;
    }
    public static void InsertData(String data){
        try {
            Connection connection1 = ConnectionClass.getConnection();
            PreparedStatement posted = connection1.prepareStatement("INSERT INTO proj1.ClientDetails (name,lastName,date,remarks,insurance) VALUES"+data);
            posted.executeUpdate();
            System.out.println("Saved to DB");
        }catch(Exception e){System.out.println(e);}
    }
    
    
    
	public static String[] getDev() throws SQLException, ClassNotFoundException {

		Connection connection1 = ConnectionClass.getConnection();
		String a[]=new String[2];

		 try {
	            PreparedStatement ps =connection1.prepareStatement("SELECT * FROM proj1.DeveloperDetails");
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()){
	            	a[0] = rs.getString("student_name_1");
	        		a[1] = rs.getString("version");
	            }

	        }catch (Exception e) {System.out.println(e);}
		
		
		
		return a;

	}
}
