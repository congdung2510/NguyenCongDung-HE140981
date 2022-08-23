package dto;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import lombok.*;

import java.util.List;

/**
 * @author admin
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchOnLeaveData implements Comparable<SearchOnLeaveData>{

    private Integer id;
    private String name;
    private List<DailyDetail> dayInMonth;
    private Double totalLeave;

    @Override
    public boolean equals(Object v) {
         if(((SearchOnLeaveData) v).getId() == this.id){
             return true;
         }else{
             return false;
         }
     }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(SearchOnLeaveData o) {
        return this.id - o.id;
    }
}
