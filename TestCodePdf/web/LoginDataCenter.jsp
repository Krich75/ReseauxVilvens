<%@page language="java" %>
<%@page contentType="text/html; charset=ISO-8859-1"%>

<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<html>
<head><title>Login sur le Data Center des r�f�rences</title></head>
<body>
<%! Date maintenant = new Date(); %>
<%! String laDate =
DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,Locale.FRANCE).
format(maintenant); %>
<!-- Page demand�e le <%=laDate %> -->
<P>Bienvenue sur LE site des r�f�rences scientifiques !<p>
Nous sommes le <%=laDate %> !!!<p>
<% String msg= request.getParameter("msg");
if (msg!=null) out.println("<H2>Message du serveur : " + msg + "</H2><p>");
%>
Veuillez entrer votre nom et votre mot de passe ...<p>
<form method="POST" action=" http://localhost:8084/TestCodePdf/ControleDataCenter">
<P>Authentification pr�alable, requ�te (apr�s authentification obligatoire) ou fin ?"<p>
<SELECT Name="action">
<OPTION>Authentification
<OPTION>Requ�te
<OPTION>Terminer
</SELECT></P>
<P>Nom : <input type="text" name="nom" size=20></P>
<P>Mot de passe : <input type="password" name="motDePasse" size=20></P>
<P><input type="submit" value="action"></P>
</form>
</body>
</html>