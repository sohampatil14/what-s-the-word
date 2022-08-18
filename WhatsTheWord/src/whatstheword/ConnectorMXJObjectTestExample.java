/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whatstheword;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import com.mysql.management.MysqldResource;
import com.mysql.management.MysqldResourceI;
import com.mysql.management.util.QueryUtil;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ConnectorMXJObjectTestExample {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    public static Connection conn = null;
    public Statement statement = null;
    public PreparedStatement preparedStatement = null;
    String synonym1; 
    String antonym1;
    String meaning1;
    String sentence1;
    String synonym2; 
    String antonym2;
    String meaning2;
    String sentence2;
    String synonym3; 
    String antonym3;
    String meaning3;
    String sentence3;
    String synonym4; 
    String antonym4;
    String meaning4;
    String sentence4;
    boolean s1; 
    boolean s2; 
    boolean s3; 
    boolean s4;
    
    public ConnectorMXJObjectTestExample() throws Exception {
        File ourAppDir = new File(System.getProperty(JAVA_IO_TMPDIR));
        File databaseDir = new File(ourAppDir, "mysql-mxj");
        int portNumber = Integer.parseInt(System.getProperty("c-mxj_test_port",
                "3336"));
        String userName = "olyanren";
        String password = "1987";
        MysqldResource mysqldResource = startDatabase(databaseDir, portNumber,
                userName, password);
        Class.forName(DRIVER);
        try {
            String dbName = "our_test_app";
            String url = "jdbc:mysql://localhost:" + portNumber + "/" + dbName
                    + "?" + "createDatabaseIfNotExist=true"
                    ;
            conn = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT VERSION()";
            String queryForString = new QueryUtil(conn).queryForString(sql);
            try (InputStream in = new FileInputStream("src/whatstheword/whatstheword.sql")) {
                importSQL(conn,in);
            }
            System.out.println("------------------------");
            System.out.println(sql);
            System.out.println("------------------------");
            System.out.println(queryForString);
            System.out.println("------------------------");
            System.out.flush();
            Thread.sleep(100); // wait for System.out to finish flush
        } 
        
        finally {
            try {
                System.out.println("Server Started");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void importSQL(Connection conn, InputStream in) throws SQLException
{
    Scanner s = new Scanner(in);
    s.useDelimiter("(;(\r)?\n)|(--\n)");
    Statement st = null;
    try
    {
        st = conn.createStatement();
        while (s.hasNext())
        {
            String line = s.next();
            if (line.startsWith("/*!") && line.endsWith("*/"))
            {
                int i = line.indexOf(' ');
                line = line.substring(i + 1, line.length() - " */".length());
            }

            if (line.trim().length() > 0)
            {
                st.execute(line);
            }
        }
    }
    finally
    {
        if (st != null) st.close();
    }
}
    
    public static MysqldResource startDatabase(File databaseDir, int port, String userName, String password) {
        MysqldResource mysqldResource = new MysqldResource(databaseDir);
        Map database_options = new HashMap();
        database_options.put(MysqldResourceI.PORT, Integer.toString(port));
        database_options.put(MysqldResourceI.INITIALIZE_USER, "true");
        database_options.put(MysqldResourceI.INITIALIZE_USER_NAME, userName);
        database_options.put(MysqldResourceI.INITIALIZE_PASSWORD, password);
  
        mysqldResource.start("test-mysqld-thread", database_options);
        if (!mysqldResource.isRunning()) {
            throw new RuntimeException("MySQL did not start.");
        }
        System.out.println("MySQL is running.");
        return mysqldResource;
    }
    
    public void getWordS1(String word) throws SQLException {
        String query = "Select * from s1_formal where word= ? ";
        PreparedStatement find = conn.prepareStatement(query);
        find.setString(1,word);
        ResultSet rs = find.executeQuery();
        if (rs.next()) {
            s1 = true;
            rs.beforeFirst();
            while(rs.next()){
                synonym1 = rs.getString("synonym");
                antonym1 = rs.getString("antonym");
                meaning1 = rs.getString("meaning");
                sentence1 = rs.getString("sentence");
            }
        }
        else {
            s1 = false;
        }
        
    }
    
    public void getWordS2(String word) throws SQLException {
        String query = "Select * from s2_informal where word= ? ";
        PreparedStatement find = conn.prepareStatement(query);
        find.setString(1,word);
        ResultSet rs = find.executeQuery();
        if (rs.next()) {
            s2 = true;
            rs.beforeFirst();
            while(rs.next()){
                synonym2 = rs.getString("synonym");
                antonym2 = rs.getString("antonym");
                meaning2 = rs.getString("meaning");
                sentence2 = rs.getString("sentence");
            }
        }
        else {
            s2 = false;
        }
    }
    
    public void getWordS3(String word) throws SQLException {
        String query = "Select * from s3_public_speaking where word= ? ";
        PreparedStatement find = conn.prepareStatement(query);
        find.setString(1,word);
        ResultSet rs = find.executeQuery();
        if (rs.next()) {
            s3 = true;
            rs.beforeFirst();
            while(rs.next()){
                synonym3 = rs.getString("synonym");
                antonym3 = rs.getString("antonym");
                meaning3 = rs.getString("meaning");
                sentence3 = rs.getString("sentence");
            }
        }
        else {
            s3 = false;
        }
    }
    
    public void getWordS4(String word) throws SQLException {
        String query = "Select * from s4_any where word= ? ";
        PreparedStatement find = conn.prepareStatement(query);
        find.setString(1,word);
        ResultSet rs = find.executeQuery();
        if (rs.next()) {
            s4 = true;
            rs.beforeFirst();
            while(rs.next()){
                synonym4 = rs.getString("synonym");
                antonym4 = rs.getString("antonym");
                meaning4 = rs.getString("meaning");
                sentence4 = rs.getString("sentence");
            }
        }
        else {
            s4 = false;
        }
    }
    
    public LinkedHashMap<String,String> getUsers(){
        String sorgu = "Select * from users";
	LinkedHashMap<String,String> info = new LinkedHashMap<>();
	try{
		statement = conn.createStatement();
	    ResultSet rs = statement.executeQuery(sorgu);
		while(rs.next()){
                    info.put(rs.getString("UserName"),rs.getString("PhoneNo"));
		}
                statement.executeQuery(sorgu);
	
	} catch (SQLException ex) {
            Logger.getLogger(ConnectorMXJObjectTestExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return info;  
    }
    
    void loadDictionaryData(JTable jTable1) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from dictionary");
            
            while(jTable1.getRowCount() > 0) {
                ((DefaultTableModel)jTable1.getModel()).removeRow(0);
            }
            
            int col = rs.getMetaData().getColumnCount();
            
            while(rs.next()) {
                Object [] rows = new Object[col];
                for(int i = 1; i <= col; i++)
                {
                    rows[i-1] = rs.getObject(i);
                }
                ((DefaultTableModel)jTable1.getModel()).insertRow(rs.getRow() -1, rows);
            }
            
            rs.close();
            st.close();
            
        } catch (Exception ex) {
            System.out.println("Error: "+ ex);
        }
        
    }

}