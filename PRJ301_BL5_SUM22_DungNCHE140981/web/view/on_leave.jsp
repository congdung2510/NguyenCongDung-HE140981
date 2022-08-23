<%-- 
    Document   : on_leave
    Created on : Aug 23, 2022, 7:40:27 AM
    Author     : admin
--%>

<%@ page import="dto.SearchOnLeaveResponse" %>
<%@ page import="dto.SearchOnLeaveData" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.DailyDetail" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="container">
    <h2>Take The Day Off</h2>
    <form action="/web/on-leave", method="get">
        Employee ID: <input type="text" name="id">
        Date of From: <input type="date" name="fromDate">
        Date of To: <input type="date" name="toDate">
        Search <input type="submit">
        </br>
        </br>
    </form>
</div>
<%
    if(request.getAttribute("onLeaves") != null){
%>
<table>
    <tr>
        <th>EmployeeId</th>
        <th>Employee Name</th>
            <%
                SearchOnLeaveResponse onLeaveResponse = (SearchOnLeaveResponse) request.getAttribute("onLeaves");
                int startDate = onLeaveResponse.getStartDay();
                int endDate = onLeaveResponse.getEndDay();
                List<SearchOnLeaveData> data = onLeaveResponse.getData();
            for(int i =startDate ; i <= endDate; i+=1) {
            %>
                <th><%=i%></th>
            <% } %>
    <th>Sum Take The Day Off</th>
    </tr>
    <%
        for(int i = 0; i < data.size(); i++){
            SearchOnLeaveData d = data.get(i);
    %>
        <tr>
            <td><%=d.getId()%></td>
            <td><%=d.getName()%></td>
            <%
                List<DailyDetail> dailyDetails = d.getDayInMonth();
                for (int j = 0; j < dailyDetails.size(); j++){
                    DailyDetail dl = dailyDetails.get(j);
                    String display = dl.getIsAbsent() ? "P:8" : "X:8";
                    if(dl.getIsAbsentPartTime()){
                        display = "P:4";
                    }
                    if(dl.getDayInWeek() == 6 || dl.getDayInWeek() == 7){
                    display = "";
                }
            %>
                <td><%=display%></td>
            <%
                }
            %>
            <td><%=d.getTotalLeave()%></td>
        </tr>
    <%
        }
    %>
</table>

<%
    }
%>

</body>

<style>
    body {
        background: rgb(238, 174, 202);
        background-color: #cccccc;
    }


    .container {
        margin: 0 20%;
        padding-left: 1.8em
    }

    table, th, td {
        border: 1px solid black;
    }
</style>
</html>
