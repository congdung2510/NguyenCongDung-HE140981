/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.AccountDBContext;
import dal.GroupDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Group;

/**
 *
 * @author Hello Ngo Tung Son handsome
 */
public class RegisterController extends HttpServlet {
   
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        GroupDBContext db = new GroupDBContext();
        ArrayList<Group> groups = db.getGroups();
        request.setAttribute("groups", groups);
        request.getRequestDispatcher("view/register.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] gids = request.getParameterValues("gid");
        ArrayList<Group> groups = new ArrayList<>();
        if(gids !=null)
        {
            for (String gid : gids) {
                int id = Integer.parseInt(gid);
                Group g = new Group();
                g.setId(id);
                groups.add(g);
            }
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setGroups(groups);
        AccountDBContext db = new AccountDBContext();
        db.insert(account);
        response.getWriter().println("inserted successful!");
    }

    


}
