package dto;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author admin
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SearchOnLeaveResponse {

    private Integer startDay;
    private Integer endDay;
    private List<SearchOnLeaveData> data;
}
