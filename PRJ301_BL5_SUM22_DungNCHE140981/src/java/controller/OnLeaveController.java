/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import constant.WorkingTime;
import dal.EmployeeDBContext;
import dal.OnLeaveDBContext;
import dto.DailyDetail;
import dto.SearchOnLeaveData;
import dto.SearchOnLeaveResponse;
import helper.CustomPrinter;
import helper.DateTimeHelper;
import helper.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.*;
import java.util.*;

import model.OnLeave;

/**
 * @author admin
 */
public class OnLeaveController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet OnLeaveController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OnLeaveController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idReq = request.getParameter("id");
        String fromDateReq = request.getParameter("fromDate");
        String toDateReq = request.getParameter("toDate");

        Integer id = null;
        if (idReq != null && !idReq.isEmpty()) {
            id = Validator.validateEid(idReq);
        }
        Date fromDate = DateTimeHelper.stringToDate(Validator.validateString(fromDateReq));
        Date toDate = DateTimeHelper.stringToDate(Validator.validateString(toDateReq));
        toDate = DateTimeHelper.toEndDay(toDate);
        
        int dateStart = DateTimeHelper.getDayOfMonth(fromDate);
        int dateEnd = DateTimeHelper.getDayOfMonth(toDate);
        
        OnLeaveDBContext onLeaveDBContext = new OnLeaveDBContext();
        EmployeeDBContext employeeDBContext = new EmployeeDBContext();

        List<OnLeave> onLeaves = onLeaveDBContext.selectManyOnLeave(id, fromDate, toDate);

        List<SearchOnLeaveData> onLeaveResponses = new ArrayList<>();

        for (OnLeave o :
                onLeaves) {
            int dayOffFrom = DateTimeHelper.getDayOfMonth(o.getOffFrom());
            int dayOffTo = DateTimeHelper.getDayOfMonth(o.getOffTo());

            SearchOnLeaveData searchOnLeaveData = SearchOnLeaveData.builder().id(o.getEid()).build();
            double totalDayOff;

                double checkedWork = DateTimeHelper.calculateTimeNotWorking(o.getOffFrom(), o.getOffTo());
                totalDayOff = checkedWork + (dayOffTo - dayOffFrom -1);

            if(onLeaveResponses.contains(searchOnLeaveData)){
                int currentIndex = onLeaveResponses.indexOf(searchOnLeaveData);
                SearchOnLeaveData currentResponse = onLeaveResponses.get(currentIndex);
                totalDayOff += currentResponse.getTotalLeave();
            }
            String reason = o.getReason();
            List<Integer> dayOffs = getDayOffs(dayOffFrom, dayOffTo);

            for (Integer day :
                    dayOffs) {
                DailyDetail dailyDetail = DailyDetail.builder()
                        .isAbsent(true)
                        .day(day)
                        .dayInWeek(DateTimeHelper.getDayOfWeek(fromDate, day))
                        .isAbsentPartTime(checkIsPartTime(day, o.getOffFrom(), o.getOffTo()))
                        .offFrom(getOffFrom(day, o.getOffFrom()))
                        .offTo(getOffTo(day, o.getOffTo()))
                        .reason(reason)
                        .build();
                if (onLeaveResponses.contains(searchOnLeaveData)) {
                    int currentIndex = onLeaveResponses.indexOf(searchOnLeaveData);
                    SearchOnLeaveData currentResponse = onLeaveResponses.get(currentIndex);
                    List<DailyDetail> currentDailyDetail = currentResponse.getDayInMonth();
                    currentDailyDetail.remove(dailyDetail);
                    currentDailyDetail.add(dailyDetail);
                    Collections.sort(currentDailyDetail);
                    onLeaveResponses.remove(currentIndex);
                    onLeaveResponses.add(SearchOnLeaveData.builder()
                            .id(o.getEid())
                            .name(employeeDBContext.getEmployeeName(o.getEid()))
                            .dayInMonth(currentDailyDetail)
                            .totalLeave(totalDayOff)
                            .build());

                } else {
                    List<DailyDetail> list = getDefaultDailyDetail(dateStart, dateEnd,fromDate);
                    list.remove(dailyDetail);
                    list.add(dailyDetail);
                    onLeaveResponses.add(SearchOnLeaveData.builder()
                            .id(o.getEid())
                            .name(employeeDBContext.getEmployeeName(o.getEid()))
                            .dayInMonth(list)
                            .totalLeave(totalDayOff)
                            .build());
                }

            }
        }

        Collections.sort(onLeaveResponses);

        SearchOnLeaveResponse searchOnLeaveResponse = SearchOnLeaveResponse.builder()
                .data(onLeaveResponses)
                .startDay(DateTimeHelper.getDayOfMonth(fromDate))
                .endDay(DateTimeHelper.getDayOfMonth(toDate))
                .build();
        request.setAttribute("onLeaves", searchOnLeaveResponse);

        request.getRequestDispatcher("view/on_leave.jsp").forward(request, response);
    }

    private List<DailyDetail> getDefaultDailyDetail(Integer dayStart, Integer dayEnd, Date date){
        List<DailyDetail> detailList = new ArrayList<>();
        for (int i = dayStart; i <= dayEnd; i++){
            detailList.add(DailyDetail.builder()
                            .day(i)
                            .dayInWeek(DateTimeHelper.getDayOfWeek(date, i))
                            .isAbsent(false)
                            .isAbsentPartTime(false)
                            .reason(null)
                            .offFrom(null)
                            .offTo(null)
                    .build());
        }
        return detailList;
    }

    private Date getOffFrom(Integer day, Date offFrom) {
        LocalDateTime offFromTime = Instant.ofEpochMilli(offFrom.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
        if(offFromTime.getDayOfMonth() == day){
            return  offFrom;
        }else {
            return Date.from(
                    offFromTime.withDayOfMonth(day)
                            .withHour(WorkingTime.START_TIME.getHour())
                            .withMinute(WorkingTime.START_TIME.getMinute())
                            .withSecond(WorkingTime.START_TIME.getSecond()
                            ).atZone(ZoneId.systemDefault())
                            .toInstant()
            );
        }
    }

    private Date getOffTo(Integer day, Date offTo) {
        LocalDateTime offToTime =  Instant.ofEpochMilli(offTo.getTime())
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime();
        if(offToTime.getDayOfMonth() == day){
            return  offTo;
        }else {
            return Date.from(
                    offToTime.withDayOfMonth(day)
                            .withHour(WorkingTime.ENT_TIME.getHour())
                            .withMinute(WorkingTime.ENT_TIME.getMinute())
                            .withSecond(WorkingTime.ENT_TIME.getSecond()
                            ).atZone(ZoneId.systemDefault())
                            .toInstant()
            );
        }
    }

    private Boolean checkIsPartTime(Integer day, Date offFrom, Date offTo) {
        if (day == DateTimeHelper.getDayOfMonth(offFrom)) {
            int timeToOut = DateTimeHelper.timeToEnd(offFrom);
            if(timeToOut >= WorkingTime.REQUIRE_WORKING_PART_TIME && timeToOut <= WorkingTime.REQUIRE_WORKING_FULL_TIME){
                return true;
            }else{
                return false;
            }
        } else if (day == DateTimeHelper.getDayOfMonth(offTo)) {
            int timeToOut = DateTimeHelper.timeToStart(offTo);
            if(timeToOut >= WorkingTime.REQUIRE_WORKING_PART_TIME && timeToOut <= WorkingTime.REQUIRE_WORKING_FULL_TIME){
                return false;
            }else{
                return true;
            }
        } else {
            return false;
        }
    }

    private List<Integer> getDayOffs(Integer dayOffFrom, Integer dayOffTo) {
        List<Integer> dayOffs = new ArrayList<>();
        for (int i = dayOffFrom; i <= dayOffTo; i++) {
            dayOffs.add(i);
        }
        return dayOffs;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String eidStr = request.getParameter("eid");
        String dayOffFrom = request.getParameter("offFrom");
        String dayOffTo = request.getParameter("offTo");
        String reason = request.getParameter("reason");
        Integer eid = Validator.validateEid(eidStr);
        Validator.validateString(dayOffFrom);
        Validator.validateString(dayOffTo);

        Date offFrom = DateTimeHelper.stringToDate(dayOffFrom);
        Date offTo = DateTimeHelper.stringToDate(dayOffTo);

        OnLeave onLeave = OnLeave.builder()
                .eid(eid)
                .offFrom(offFrom)
                .offTo(offTo)
                .reason(reason)
                .build();

        OnLeaveDBContext onLeaveDBContext = new OnLeaveDBContext();
        onLeaveDBContext.insertOnLeave(onLeave);
        CustomPrinter.alert(response, "Insert success");

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
