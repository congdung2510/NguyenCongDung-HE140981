<%-- 
    Document   : on_leave
    Created on : Aug 22, 2022, 8:49:05 PM
    Author     : admin
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="/web/on-leave", method="POST">
            Eid: <input type="text" name="eid"/> <br/>
            From: <input type="datetime-local" name="offFrom"/> <br/>
            To: <input type="datetime-local" name="offTo"/> <br/>
            Reason: <input type="text" name="reason"/> <br/>
            Submit <input type="submit"> 
        </form>
    </body>
</html>
