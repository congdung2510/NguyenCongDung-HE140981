/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dal.AccountDBContext;
import helper.CustomPrinter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Account;
import model.Group;

/**
 *
 * @author Ngo Tung Son
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        AccountDBContext db = new AccountDBContext();
        Account account = db.getAccount(username, password);
        if(account!=null)
        {
            account.setGroups(db.getGroups(account.getId()));
            HttpSession session = req.getSession();
            session.setAttribute("id", account.getId());
            session.setAttribute("username", account.getUsername());
            StringBuilder builder = new StringBuilder();
            for(Group g : account.getGroups()){
                builder.append(g.getName()).append(" ,");
            }
            if(builder.length() > 0){
                session.setAttribute("role", builder.toString().substring(0, builder.length() - 2));
            }
            if(account.getUsername().equals("mra")){
                req.getRequestDispatcher("view/on_leave.jsp").forward(req, resp);
            }else{
                req.getRequestDispatcher("view/insert_on_leave.jsp").forward(req, resp);
            }
            
        }
        else
        {
            CustomPrinter.alert(resp, "Invalid username or password!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/login.jsp").forward(req, resp);
    }
    
    
}
