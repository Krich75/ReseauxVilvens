<%-- 
    Document   : JSPCaddie
    Created on : 10 nov. 2017, 16:24:28
    Author     : tibha
--%>

<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="database.utilities.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:h="http://java.sun.com/jsf/html" xmlns:f="http://java.sun.com/jsf/core">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Vols disponibles</h1>
<form action="http://localhost:8084/CaddieVirtuel/TrueFormServlet" method="POST">
<table width="59%" border="1" style="border-collapse: collapse; ">
    <%
        BeanBD o = new BeanBD();
        o.setTypeBD("MySql");
        int j=0;
        o.connect();
        ResultSet r;
        String q = "select * from vols;";
        r = o.getInstruc().executeQuery(q) ;
        ResultSetMetaData metaData = r.getMetaData();
        %>
        <tr>
            <%
            for(int i = 1; i<=metaData.getColumnCount();i++)
               { %>
                <th>
                <%= metaData.getColumnName(i)%>
                </th>
           <% 
                    if(i==metaData.getColumnCount())
                    {
                    %>
                        <th>
                            Réservations
                        </th>
                    <%
                    }
               }
        %>                   
        </tr>
        <% 
        while(r.next())
        {
        %>
        <tr>
            <%
            for(int i = 1; i<=metaData.getColumnCount();i++)
               { %>
                <td>
                <%= r.getString(i)%>
                </td>
                
           <% 
                    if(i==metaData.getColumnCount())
                    {
                    %>
                        <td>
                            <input type="submit" name="button" value="Reserve"</input> 
                            <input type="hidden" name="hiddenValue" value=<%= j %>>
                            <%=j++%>
                        </td>
                    <%
                    }
               }
        %>                   
        </tr>
        <% 
        }
    %>
</table>
</form>
    </body>
</html>


