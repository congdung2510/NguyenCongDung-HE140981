/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.DateTimeHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.Group;
import model.TimeSheet;

/**
 *
 * @author Ngo Tung Son
 */
public class EmployeeDBContext extends DBContext {

    public ArrayList<Employee> getEmployees(Date begin, Date end) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "SELECT e.eid,e.ename,t.tid,t.checkin,t.checkout\n"
                    + "FROM Employee e\n"
                    + "	LEFT JOIN Timesheet t\n"
                    + "	ON e.eid = t.eid\n"
                    + "	WHERE \n"
                    + "	t.checkin >= ?\n"
                    + "	AND\n"
                    + "	t.checkin <= ?"
                    + " ORDER BY e.id";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setTimestamp(1, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(begin)));
            stm.setTimestamp(2, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(end)));
            ResultSet rs = stm.executeQuery();
            Employee curEmp = new Employee();
            curEmp.setId(-1);
            while (rs.next()) {
                int eid = rs.getInt("eid");
                if(eid != curEmp.getId())
                {
                    curEmp = new Employee();
                    curEmp.setId(eid);
                    curEmp.setName(rs.getString("ename"));
                    employees.add(curEmp);
                }
                TimeSheet t = new TimeSheet();
                t.setEmployee(curEmp);
                t.setId(rs.getInt("tid"));
                t.setCheckin(DateTimeHelper.getDateFrom(rs.getTimestamp("checkin")));
                t.setCheckout(DateTimeHelper.getDateFrom(rs.getTimestamp("checkout")));
                curEmp.getTimesheets().add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;
    }

    public String getEmployeeName(Integer eid) {
        String sqlQuery = "SELECT ename from Employee WHERE eid = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, eid);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()){
                return result.getString("ename");
            }else{
                throw new IllegalArgumentException("Eid not exist!");
            }
        }catch (Exception ex){
            throw new RuntimeException("Can not query Employee by eid: "+ ex.getMessage());
        }
    }
}
