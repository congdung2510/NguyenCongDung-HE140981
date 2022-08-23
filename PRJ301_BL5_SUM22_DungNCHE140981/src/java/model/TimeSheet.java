/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import helper.DateTimeHelper;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ngo Tung Son
 */
public class TimeSheet {
    private int id;
    private Employee employee;
    private Date checkin;
    private Date checkout;
    
    private Date cidate;

    public Date getCidate() {
        return DateTimeHelper.removeTime(checkin);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }
    
    public float getWorkingHours()
    {
        if(checkout != null)
        {
            long diff = Math.abs(checkout.getTime() - checkin.getTime());
            long diffSeconds = diff / 1000 % 60;  
            long diffMinutes = diff / (60 * 1000) % 60; 
            long diffHours = diff / (60 * 60 * 1000);
            return (diffHours*1.0f) +  ((diffMinutes*1.0f) / 60);
        }
        else
            return -1;
    }
}
