<%@page language="java" %>
<%@page contentType="text/html; charset=ISO-8859-1"%>
<%@page info="(c) Claude Vilvens - 8/2004" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<jsp:useBean id="msgGeneral" scope="application" class="JspServlets.message" />
<html>
<head><title>Data Center des r�f�rences</title></head>
<body>
<%! Date maintenant = new Date(); %>
<%! String laDate =
DateFormat.getDateTimeInstance(DateFormat.FULL,DateFormat.FULL,Locale.FRANCE).
format(maintenant); %>
<!-- Page demand�e le <%=laDate %> -->
<P>Vous �tes toujours sur LE site des r�f�rences scientifiques !<p>
Nous sommes le <%=laDate %> !!!<p>
<form method="POST" action=" http://localhost:8084/TestCodePdf/ControleDataCenter">
<P>Requ�te (vous �tes d�j� authentifi�) ou fin ?"<p>
<SELECT Name="action">
<OPTION>Requ�te
<OPTION>Terminer
</SELECT></P>
<P><input type="submit" value="action"></P>
</form>
<p><hr>
La pens�e du jour : <p><jsp:getProperty name="msgGeneral" property="texteMessage" />
</body>
</html>