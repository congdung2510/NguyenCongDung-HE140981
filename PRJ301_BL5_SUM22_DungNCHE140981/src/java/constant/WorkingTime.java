package constant;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author admin
 */
public class WorkingTime {

    public static final LocalTime START_TIME = LocalTime.of(9,0,0);
    public static final LocalTime ENT_TIME = LocalTime.of(17, 0, 0);
    public static final Integer REQUIRE_WORKING_PART_TIME = 3;
    public static final Integer REQUIRE_WORKING_FULL_TIME = 7;
}