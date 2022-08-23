/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import helper.DateTimeHelper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OnLeave;

/**
 *
 * @author admin
 */
public class OnLeaveDBContext extends DBContext {

    public void insertOnLeave(OnLeave onLeave) {

        String sqlQuery = "INSERT INTO OnLeave(eid, oOffFrom, oOffTo, reason) "
                + " VALUES( ? , ?, ? , ? )";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, onLeave.getEid());
            preparedStatement.setTimestamp(2, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(onLeave.getOffFrom())));
            preparedStatement.setTimestamp(3, DateTimeHelper.getTimeStamp(DateTimeHelper.removeTime(onLeave.getOffTo())));
            preparedStatement.setString(4, onLeave.getReason());
            boolean execute = preparedStatement.execute();
            if (execute) {
                Logger.getLogger(OnLeaveDBContext.class.getName()).log(Level.SEVERE, "Insert OnLeave success");
            } else {
                Logger.getLogger(OnLeaveDBContext.class.getName()).log(Level.SEVERE, "Insert OnLeave failed");
            }
        } catch (Exception e) {
            Logger.getLogger(OnLeaveDBContext.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("Can not insert OnLeave to db: " + e.getMessage());
        }
    }

    public List<OnLeave> selectManyOnLeave(Integer id, Date fromDate, Date toDate) {
      

        String sqlQuery = "SELECT * FROM OnLeave WHERE oOffFrom >= ? and oOffTo <= ? ";
        if(id != null) {
            sqlQuery += "id = ?";
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setTimestamp(1, DateTimeHelper.getTimeStamp(fromDate));
            preparedStatement.setTimestamp(2, DateTimeHelper.getTimeStamp(toDate));
            if(id != null){
                preparedStatement.setInt(3, id);
            }
            ResultSet result = preparedStatement.executeQuery();
            List<OnLeave> onLeaves = new ArrayList<>();
            while (result.next()){
                OnLeave onLeave = OnLeave.builder()
                        .id(result.getInt("oid"))
                        .eid(result.getInt("eid"))
                        .offFrom(result.getDate("oOffFrom"))
                        .offTo(result.getDate("oOffTo"))
                        .reason(result.getString("reason"))
                        .build();

                onLeaves.add(onLeave);
            }
            return onLeaves;

        }catch (Exception ex){
            throw new RuntimeException("Can not select OnLeave from database: "+ ex.getMessage());
        }

    }
}
