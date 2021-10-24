<%
    if(session.getAttribute("user") == null)
    {
        request.getRequestDispatcher("/Authentification").forward(request, response);
    }
%>
<%@page import="com.java.classes.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    User user = (User) session.getAttribute("user");   
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/menuNavigationUser.css"> 

    </head>
    <body>      
        
        <nav class="sidebar">
            <ul>
                <li><center><img id="logosteg" src="images/logoIPmanager.png" width="160px" height="auto" alt=""/></center></li>
                <hr>
                <div class="links">
                    <li><span class="titre">Réseaux</span>
                        <ul class="sous-menu">
                            <li><a href="sous-reseauxUser.jsp">Sous-réseaux</a></li>
                            <li><a href="reseauUser.jsp">Liste des adresses IP</a></li>
                        </ul>
                    </li>
                </div>
            </ul>
        </nav>
        
        <nav class="menu-horizontal">
            <ul>
                <li id="username"><%=user.getPrenom() + " " + user.getNom() + " (Utilisateur)" %></li>
                <li id="deconnection"><a href="Authentification">Déconnexion</a></li>
            </ul>
        </nav>
    </body>
</html>
