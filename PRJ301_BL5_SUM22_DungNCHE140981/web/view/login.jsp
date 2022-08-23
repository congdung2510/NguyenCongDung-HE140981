<%-- 
    Document   : login
    Created on : Aug 10, 2022, 2:27:08 PM
    Author     : Ngo Tung Son
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body 
{
font-family: Roboto;
background: rgb(34,193,195);
}



input[type=text] {
  width: 50%;
  padding: 12px 20px;
 margin: 0 20%;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
  margin-bottom: 10px;
}


input[type=password] {
  width: 50%;
  padding: 12px 20px;
 margin: 0 20%;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
  
}

button {
  background-color: #04AA6D;
  color: white;
  padding: 14px 20px;
  margin: 0 20%;
  border: none;
  cursor: pointer;
  width: 50%;
  border-radius: 10px;
  margin-top: 10px;
}

button:hover {
  opacity: 0.8;
}


.container {
margin: 0 20%;
  padding: 16px;
}


span.psw {
  float: right;
  padding-top: 16px;
}

h2 {
 margin: 0 20%;
    padding-bottom: 10px;
}

</style>
</head>
<body>
<div class="container">
<h2>Login </h2>
<form action="/web/login" method="POST">
    Username:<input type="text" placeholder="Enter Username" name="username" >
    Password:<input type="password" placeholder="Enter Password" name="password" >
    <input type="submit" value="login"  class = "submit" /> 
</form>
</div>
</body>
</html>