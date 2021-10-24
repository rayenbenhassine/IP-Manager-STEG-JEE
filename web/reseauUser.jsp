<%
    if(session.getAttribute("user") == null)
    {
        request.getRequestDispatcher("/Authentification").forward(request, response);
    }
%>

<%@page import="com.java.mysql.ConnexionBD"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/ReseauUser.css"> 
    </head>
    <body onload="afficherpopup()">
        <script>
            function afficherpopup()
            {
                <% if (session.getAttribute("infosearch") != null) {%>
                    alert("<%= session.getAttribute("infosearch") %>");
                    <% session.removeAttribute("infosearch");     
                } %>                
            }
        </script>
        <jsp:include page="menuNavigationUser.jsp"></jsp:include>             
        <section id="reseau">
            <h1>Liste des adresses IP</h1>
                <div class="form-search">
                    <form method="post" action="RechercherSousReseau">
                        <label>Recherche par sous-réseau : </label>
                        <select name="select">
                            <%
                                ResultSet result1 = ConnexionBD.executeSelectQuery("select id_reseau, nomReseau from reseau");
                                while(result1.next())
                                {%>
                                    <option value="<%= result1.getInt("id_reseau") %>"><%= result1.getString("nomReseau") %></option>
                                <%} 
                            %>    
                        </select>
                        <input class="searchbtn" type="submit" name="submit" value=" ">
                    </form>
                    <form method="post" action="RechercherMatricule">
                        <label style="margin-right:15px">Recherche par matricule : </label>
                        <input style="width:190px;" type="text" name="matricule" placeholder="Saisir une matricule" required>
                        <input class="searchbtn" type="submit" name="submit" value=" ">
                    </form>
                    <form method="post" action="RechercherIP">
                        <label style="margin-right:7px">Recherche par adresse IP : </label>
                        <input style="width:190px;" type="text" name="adresse_ip" placeholder="Saisir une adresse IP" required>
                        <input style="margin-bottom:20px;" class="searchbtn" type="submit" name="submit" value=" ">
                    </form>
                </div>
                

          
            
            <table>
                <tr>
                    <th>Matricule</th>
                    <th>Nom et Prenom</th>
                    <th>Adresse IP</th>
                    <th>Nom du Sous-réseau</th>
                </tr>
        <%
                if(session.getAttribute("result") != null)
                {
                    ResultSet result = (ResultSet) session.getAttribute("result");
                    while(result.next())
                    {
                %>
                        <tr>
                            <td><%= result.getLong("matricule") %></td>
                            <td><%= result.getString("prenom") %></td>
                            <td><%= result.getString("adresse_ip") %></td>
                            <td><%= result.getString("nomReseau") %></td>
                        </tr>
                    <%}
                    session.removeAttribute("result");
                }
                else if(session.getAttribute("resultmatricule") != null)
                {
                    ResultSet resultmatricule = (ResultSet) session.getAttribute("resultmatricule");

                    if(resultmatricule.first())
                    {
                %>
                        <tr>
                            <td><%= resultmatricule.getLong("matricule") %></td>
                            <td><%= resultmatricule.getString("prenom") %></td>
                            <td><%= resultmatricule.getString("adresse_ip") %></td>
                            <td><%= resultmatricule.getString("nomReseau") %></td>
                        </tr>
                    <% }
                    session.removeAttribute("resultmatricule");
                }
                else if(session.getAttribute("resultIP") != null)
                {
                    ResultSet resultIP = (ResultSet) session.getAttribute("resultIP");

                    if(resultIP.first())
                    {
                %>
                        <tr>
                            <td><%= resultIP.getLong("matricule") %></td>
                            <td><%= resultIP.getString("prenom") %></td>
                            <td><%= resultIP.getString("adresse_ip") %></td>
                            <td><%= resultIP.getString("nomReseau") %></td>
                        </tr>
                    <% }
                    session.removeAttribute("resultIP");
                }
                else
                {
                    ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip.matricule,adresse_ip.prenom,adresse_ip.adresse_ip,reseau.nomReseau from adresse_ip, reseau where adresse_ip.id_reseau = reseau.id_reseau");
                    while(result.next())
                    {%>
                    <tr>
                        <td><%= result.getLong("matricule") %></td>
                        <td><%= result.getString("prenom") %></td>
                        <td><%= result.getString("adresse_ip") %></td>
                        <td><%= result.getString("nomReseau") %></td>
                    </tr>
                  <%}
                }
                %>
            </table>
        </section>
        
    </body>
</html>
