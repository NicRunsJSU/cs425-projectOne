/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs425.project1;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;



@WebServlet(name = "registration", urlPatterns =  {"/registration"})
public class Registration extends HttpServlet {

    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        Database db = new Database();
        
        try {
            String table = db.getQueryResults(request.getParameter("code"));
            out.println(table);
     
        }
        catch(Exception frick){System.err.println(frick);
      
        }
        
        
        
        
    }
    

    
    
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("application/json;charset=UTF-8");
        
       
        String info = request.getParameter("code");
        String[] args = info.split(";");
        
        String fname = args[0];
        String lname = args[1];
        String disname = args[2];
        String sessid = args[3];
        
        Database db = new Database();
        String result = db.addRegistrationInfo(fname, lname, disname, sessid);
        
        try (PrintWriter output = response.getWriter()){
            System.out.println(result);
        }
        
        
        
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processGet(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        try {
            processPost(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
