<%@page import="com.java.classes.User"%>
<%
    if(session.getAttribute("user") == null)
    {
        request.getRequestDispatcher("/Authentification").forward(request, response);
    }
    else
    {
        User user = (User) session.getAttribute("user");
        if(user.getAdmin() != 1)
            request.getRequestDispatcher("/Authentification").forward(request, response);
    }
%>
<%@page import="java.util.Vector"%>
<%@page import="com.java.classes.Reseau"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.java.mysql.ConnexionBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/adressesIP-Libres.css"> 
    </head>
    <body>
        <jsp:include page="menuNavigationAdmin.jsp"></jsp:include>             
        <section id="liste-ip-libres">
            <h1>Liste des Adresses IP libres</h1>                
            <table>
                <tr>
                    <th>Adresse IP</th>
                    <th>Affecter à un utilisateur</th>
                </tr>
                <%
                    ResultSet result = ConnexionBD.executeSelectQuery("select * from reseau where id_reseau = " + request.getParameter("id_reseau"));
                    if(result.first())
                    {
                        Reseau reseau = new Reseau(result.getInt("id_reseau"),result.getString("nomReseau"),result.getString("ip_reseau"),result.getString("masque_sous_reseau"),result.getString("passerelle"));
                        Vector<String> ip_libres = reseau.AdressesIPLibres();
                        for(String ip : ip_libres)
                        {%>
                            <tr>
                                <td><%= ip %></td>
                                <td><a href="reseau.jsp?ip=<%= ip %>">Affecter</button></td>
                            </tr>
                        <%}
                    }
                    
                %>
            </table>
    </body>
</html>
