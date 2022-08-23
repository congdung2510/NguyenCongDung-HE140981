/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class CustomPrinter {
    public static void alert(HttpServletResponse response,String msg){
        response.setContentType("text/html");
        PrintWriter writer;
        try {
            writer = response.getWriter();
        } catch (IOException ex) {
            Logger.getLogger(CustomPrinter.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Can not write to response");
        }
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('"+msg+"');");
        writer.println("</script>");
    }
}
