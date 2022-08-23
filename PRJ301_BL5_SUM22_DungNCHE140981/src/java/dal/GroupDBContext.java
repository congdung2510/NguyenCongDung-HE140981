/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Group;

/**
 *
 * @author Ngo Tung Son
 */
public class GroupDBContext extends DBContext {

    public ArrayList<Group> getGroups() {
        ArrayList<Group> groups =new ArrayList<>();
        try {
            String sql = "SELECT [gid]\n"
                    + "      ,[name]\n"
                    + "  FROM [Group]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Group g = new Group();
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("name"));
                groups.add(g);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }
}
