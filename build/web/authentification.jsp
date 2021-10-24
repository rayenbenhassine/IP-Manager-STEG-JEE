<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/Authentification.css"> 
    </head>
    <body>
        <div>
            <img id="logosteg" src="images/logosteg.png" width="220px" height="auto" alt=""/><br>
            <img src="images/utilisateur.png" width="80px" height="auto" alt=""/>
            <h1>S'authentifier</h1>
            <form method="post" action="Authentification">
                <input type="text" name="matricule" placeholder="Matricule" value="<%= request.getAttribute("matricule") %>" required><br>
                <input type="password" name="mdp" placeholder="Mot de passe" required><br>
                <% if (session.getAttribute("info") != null) {%>
                    <p><%= session.getAttribute("info") %></p>
                    <% session.removeAttribute("info");     
                } %>
                <input type="submit" name="seconnecter" value="Se connecter">
            </form>
        </div>
    </body>
</html>
