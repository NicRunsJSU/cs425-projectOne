package edu.jsu.mcis.cs425.project1;

import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
//attempting to fix parsing error
public class Database {
    
    String sessID;
    
    private Connection getConnection() {
        
        Connection conn = null;
        
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception stinkystinky) { stinkystinky.printStackTrace(); }
        
        return conn;

    }
    
    public String getQueryResults(String sessionID) throws SQLException {
    
        
        StringBuilder tbl = new StringBuilder();
        String query;
        ResultSet result = null;
        
        this.sessID = sessionID;
        Connection defcon = getConnection();
        
        query = "SELECT * FROM registrations r WHERE sessionid = ?;";
        
        try { 
            PreparedStatement statement = defcon.prepareStatement(query);
            statement.setString(1, sessID);
            Boolean thereAreResults = statement.execute();
            
            if (thereAreResults) {
                result = statement.getResultSet();
                
                //  MAKE TABLE
                tbl.append("<table>");
                
                //POPULATE TABLE
                
                while (result.next()){
                    
                    tbl.append("<tr>");
                    tbl.append("<td>").append(result.getString("id")).append("</td>");
                    tbl.append("<td>").append(result.getString("firstname")).append("</td>");
                    tbl.append("<td>").append(result.getString("lastname")).append("</td>");
                    tbl.append("<td>").append(result.getString("displayname")).append("</td>");
                    tbl.append("<td>").append(sessionID).append("</td>");
                    tbl.append("</tr>");
                    
                }
                
                tbl.append("</table>");
                     
                
            }
            
            
        }
        catch(Exception e){System.err.println(e);}
        
 
        
        return (tbl.toString());
    }
    
    public String addRegistrationInfo(String firstName, String lastName, String displayName, String sessionID) throws SQLException{
        
        
        int id = 0;
        int result = 0;
        String query;
        String disname;
        String registrationCode;
        ResultSet keys;
        JSONObject json = new JSONObject();
        String results = "";
        
        
        query = "INSERT INTO registrations (firstname, lastname, displayname, sessionid)"  + "VALUES (?, ?, ?, ?); ";
        Connection defcon = getConnection();
        
        try {
            
            PreparedStatement statement = defcon.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, displayName);
            statement.setString(4, sessionID);
            
            result = statement.executeUpdate();
            
            if (result ==1){
                
                keys = statement.getGeneratedKeys();
                if (keys.next()){
                    id = keys.getInt(1);
                }    
            }
            
            String code = String.format("%06d", id);
            registrationCode = "R";
            registrationCode += code;
            
            json.put("registration_code", registrationCode);
            json.put("displayname", displayName);
            
            results = JSONValue.toJSONString(json);
            
        }
        catch(Exception bleepblop){
            System.out.println(bleepblop.toString());
        }
        
      
           
            return (results.trim());
        
        }
    }
  

