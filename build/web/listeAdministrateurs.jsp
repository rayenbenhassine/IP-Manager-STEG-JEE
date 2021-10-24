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

<%@page import="com.java.classes.User"%>
<%@page import="com.java.mysql.ConnexionBD"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/Liste-Utilisateurs.css"> 
    </head>
    <body onload="afficherpopup()">
        <script>
            function afficherpopup()
            {
                <% if (session.getAttribute("infoAjouter") != null) {%>
                    alert("<%= session.getAttribute("infoAjouter") %>");
                    <% session.removeAttribute("infoAjouter");     
                } %>  
                <% if (session.getAttribute("modifinfo") != null) {%>
                    alert("<%= session.getAttribute("modifinfo") %>");
                    <% session.removeAttribute("modifinfo");     
                } %>
                <% if (session.getAttribute("infosearch") != null) {%>
                    alert("<%= session.getAttribute("infosearch") %>");
                    <% session.removeAttribute("infosearch");     
                } %>
            }
        </script>    
        <jsp:include page="menuNavigationAdmin.jsp"></jsp:include>             
        <section id="liste-utilisateurs">
            <h1>Liste des administrateurs</h1>
            <div class="clear-fix">
                <form method="post" action="RechercherAdministrateur" class="formsearch">
                    <input type="search" name="matricule" placeholder="Saisir Matricule" required>
                    <input class="searchbtn" type="submit" name="submit" value=" ">
                </form>
                <button id="ajouterbtn" onclick="openFormAjouter()">Ajouter un administrateur</button>
            </div>
                
            <table>
                <tr>
                    <th>Matricule</th>
                    <th>Prenom</th>
                    <th>Nom</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
                </tr>
            <%
                if(session.getAttribute("result") == null)
                {
                    ResultSet result = ConnexionBD.executeSelectQuery("select * from user where admin = 1");
                    while(result.next())
                    {%>
                    <tr>
                        <td><%= result.getLong("matricule") %></td>
                        <td><%= result.getString("prenom") %></td>
                        <td><%= result.getString("nom") %></td>
                        <td><button onclick="openFormModifier(<%= result.getLong("matricule") %>,'<%= result.getString("prenom")%>','<%= result.getString("nom")%>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                        <td><button onclick="confirmpopup(<%= result.getLong("matricule") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
                    </tr>
                  <%}
                }
                else
                {   
                    User user = (User) session.getAttribute("result");
                    session.removeAttribute("result");
                %>
                    <tr>
                        <td><%= user.getMatricule() %></td>
                        <td><%= user.getPrenom() %></td>
                        <td><%= user.getNom()%></td>
                        <td><button onclick="openFormModifier(<%= user.getMatricule() %>,'<%= user.getPrenom()%>','<%= user.getNom()%>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                        <td><button onclick="confirmpopup(<%= user.getMatricule() %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
                    </tr> 
                <%}
            %>
            </table>
            <div class="form-popup" id="formAjouter">
                <center>
                    <form method="post" action="AjouterAdministrateur" class="form-container">
                        <h1>Ajouter un utilisateur</h1>                      
                        <input type="text" name="matricule" placeholder="Matricule" required><br>
                        <input type="text" name="prenom" placeholder="Prénom" required><br>
                        <input type="text" name="nom" placeholder="Nom" required><br>
                        <input type="password" name="mdp" placeholder="Nouveau mot de passe" required><br>                               
                        <input type="password" name="confirmermdp" placeholder="confirmer votre mot de passe" required><br>                                                       
                        <input type="submit" name="submit" value="Ajouter"><br>
                        <button type="button" onclick="closeFormAjouter()"><img class="closeicon" src="images/cancel.png" width="18px" height="auto"/></button>
                    </form>
                </center>
            </div>
            <div class="form-popup" id="formModifier">
                <center>
                    <form method="post" action="ModifierAdministrateur" class="form-container">
                        <h1>Modifier un utilisateur</h1>                      
                        <input type="text" id="matricule" name="matricule" placeholder="Matricule" required><br>
                        <input type="text" id="hiddeninput" name="hiddeninput" placeholder="Matricule" required>
                        <input type="text"  id="prenom" name="prenom" placeholder="Prénom" required><br>
                        <input type="text"  id="nom" name="nom" placeholder="Nom" required><br>
                        <input type="password" id="mdp"  name="mdp" placeholder="Nouveau mot de passe" required><br>                               
                        <input type="password" id="confirmermdp"  name="confirmermdp" placeholder="confirmer votre mot de passe" required><br>                                                       
                        <input type="submit" id="submit" value="modifier"><br>
                        <button type="button" onclick="closeFormModifier()"><img class="closeicon" src="images/cancel.png" width="18px" height="auto"/></button>
                    </form>
                </center>

                <script>
                    function openFormModifier(matricule, prenom,nom) 
                    {
                        document.getElementById("formModifier").style.display = "block";
                        document.getElementById("matricule").value = matricule;
                        document.getElementById("prenom").value = prenom;
                        document.getElementById("nom").value = nom;
                        document.getElementById("hiddeninput").value = matricule;
                    }
                    function openFormAjouter()
                    {
                        document.getElementById("formAjouter").style.display = "block";
                    }
                    function closeFormModifier() 
                    {
                      document.getElementById("formModifier").style.display = "none";
                    }
                    function closeFormAjouter() 
                    {
                      document.getElementById("formAjouter").style.display = "none";
                    }
                    function confirmpopup(matricule)
                    {
                        let confirmAction = confirm("Vous ètes sur ?");
                        if (confirmAction) 
                        {
      
                            window.location.href = "/ipmanager/SupprimerAdministrateur?matricule=" + matricule;
                        } 
                    }
                </script>    
            </div>
        </section>
    </body>
</html>
