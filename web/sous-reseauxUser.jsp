<%
    if(session.getAttribute("user") == null)
    {
        request.getRequestDispatcher("/Authentification").forward(request, response);
    }
%>

<%@page import="java.sql.ResultSet"%>
<%@page import="com.java.mysql.ConnexionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/sous-reseauxUser.css"> 
    </head>
    <body>
        <jsp:include page="menuNavigationUser.jsp"></jsp:include>             
        <section id="liste-sous-reseaux">
            <h1>Liste des sous-réseaux</h1>                
            <table>
                <tr>
                    <th>Nom du sous-réseau</th>
                    <th>Adresse IP</th>
                    <th>Masque du sous-réseau</th>
                    <th>Passerelle</th>
                    <th>Adresses IP libres<th>
                </tr>
                <%
                    ResultSet result = ConnexionBD.executeSelectQuery("select * from reseau");
                    while(result.next())
                    {%>
                        <tr>
                            <td><%= result.getString("nomReseau") %></td>
                            <td><%= result.getString("ip_reseau") %></td>
                            <td><%= result.getString("masque_sous_reseau") %></td>
                            <td><%= result.getString("passerelle") %></td>                        
                            <td><a href="adressesIPLibresUser.jsp?id_reseau=<%= result.getInt("id_reseau") %>">Cliquer ici</a></td>
                        </tr>
                  <%}             
                %>
            </table>
            
              
    </body>
</html>
