package dto;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import lombok.*;

import java.util.Date;

/**
 * @author admin
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DailyDetail implements Comparable<DailyDetail>{

    private Boolean isAbsent;
    private Boolean isAbsentPartTime;
    private Integer day;
    private Integer dayInWeek;
    private Date offFrom;
    private Date offTo;
    private String reason;

    @Override
    public boolean equals(Object v){
        if(((DailyDetail) v).getDay() == this.day){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public int hashCode() {
        int hash = 11;
        hash = 23 * hash + (this.day != null ? this.day.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(DailyDetail o) {
        return this.day - o.day;
    }
}
