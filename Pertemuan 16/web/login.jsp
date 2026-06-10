<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

String username=
request.getParameter("username");

String password=
request.getParameter("password");

if(username!=null){

if(

username.equals("admin")

&&

password.equals("admin123")

){

session.setAttribute(

"user",

username

);

response.sendRedirect(

"index.jsp"

);

return;

}else{

out.println(

"<script>"

+

"alert('Username atau Password Salah')"

+

"</script>"

);

}

}

%>

<!DOCTYPE html>

<html>

<head>

<title>

Login Administrator

</title>

<link
rel="stylesheet"
href="style.css">

</head>

<body class="loginbody">

<div class="logincontainer">

<div class="logincard">

<div class="iconlogin">

🔒

</div>

<h1>

Login Administrator

</h1>

<p>

Masuk untuk mengakses sistem

</p>

<hr>

<form method="post">

<label>

Username

</label>

<input

type="text"

name="username"

placeholder="Masukkan Username"

required>

<label>

Password

</label>

<input

type="password"

name="password"

placeholder="Masukkan Password"

required>

<div class="opsi">

<div>

<input
type="checkbox">

Ingat Saya

</div>

<a href="#">

Lupa Password?

</a>

</div>

<button
type="submit">

Login

</button>

</form>

<div class="bawah">

Default Login:

<br>

<b>

admin / admin123

</b>

</div>

</div>

</div>

<div class="footerlogin">

© 2026 Universitas Pamulang

</div>

</body>

</html>