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
import model.Account;
import model.Group;

/**
 *
 * @author Ngo Tung Son
 */
public class AccountDBContext extends DBContext {

    public Account getAccount(String username, String password) {
        try {
            String sql = "SELECT aid ,ausername FROM Account \n"
                    + "WHERE ausername = ?\n"
                    + "AND apassword = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("aid"));
                account.setUsername(username);
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    public ArrayList<Group> getGroups(Integer id) {
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "SELECT g.gid,g.gname FROM Groups g INNER JOIN Account_Group ag "
                    + " ON g.gid = ag.gid "
                    + " AND ag.aid = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                 Group g = new Group();
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                groups.add(g);
             
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
        return groups;
    }

    public void insert(Account account) {
        try {
            String sql = "INSERT INTO [Account]\n"
                    + "           (ausername\n"
                    + "           ,apassword)\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, account.getUsername());
            stm.setString(2, account.getPassword());
            stm.execute();
            for (Group group : account.getGroups()) {
                String sql_account_group = "INSERT INTO [Account_Group]\n"
                        + "           ([gid]\n"
                        + "           ,[username])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?)";
                PreparedStatement stm_account_group = 
                        connection.prepareStatement(sql_account_group);
                stm_account_group.setInt(1, group.getId());
                stm_account_group.setString(2, account.getUsername());
                stm_account_group.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
