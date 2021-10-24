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
        <link rel="stylesheet" type="text/css" href="css/ListeSousReseau.css"> 
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
        <section id="liste-sous-reseaux">
            <h1>Liste des sous-réseaux</h1>
            <div class="clear-fix">
                <form method="post" action="RechercherIPSousReseau" class="formsearch">
                    <input type="search" name="adresse_ip" placeholder="Saisir l'adresse IP du sous-réseau" required>
                    <input class="searchbtn" type="submit" name="submit" value=" ">
                </form>
                <button id="ajouterbtn" onclick="openFormAjouter()">Ajouter un sous-réseau</button>
            </div>
                
            <table>
                <tr>
                    <th>Nom du sous-réseau</th>
                    <th>Adresse IP</th>
                    <th>Masque du sous-réseau</th>
                    <th>Passerelle</th>
                    <th>Adresses IP libres</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>                        
                </tr>
            <%
                if(session.getAttribute("result") == null)
                {
                    ResultSet result = ConnexionBD.executeSelectQuery("select * from reseau");
                    while(result.next())
                    {%>
                    <tr>
                        <td><%= result.getString("nomReseau") %></td>
                        <td><%= result.getString("ip_reseau") %></td>
                        <td><%= result.getString("masque_sous_reseau") %></td>
                        <td><%= result.getString("passerelle") %></td>                        
                        <td><a href="adressesIPLibres.jsp?id_reseau=<%= result.getInt("id_reseau") %>">Cliquer ici</a></td>
                        <td><button onclick="openFormModifier(<%= result.getInt("id_reseau") %>,'<%= result.getString("nomReseau") %>','<%= result.getString("ip_reseau") %>','<%= result.getString("masque_sous_reseau") %>','<%= result.getString("passerelle") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                        <td><button onclick="confirmpopup(<%= result.getInt("id_reseau") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
                    </tr>
                  <%}
                }
                else
                {
                    ResultSet result = (ResultSet) session.getAttribute("result");
                %>
                    <tr>
                        <td><%= result.getString("nomReseau") %></td>
                        <td><%= result.getString("ip_reseau") %></td>
                        <td><%= result.getString("masque_sous_reseau") %></td>
                        <td><%= result.getString("passerelle") %></td>                        
                        <td><a href="adressesIPLibres.jsp?id_reseau=<%= result.getInt("id_reseau") %>">Cliquer ici</a></td>
                        <td><button onclick="openFormModifier(<%= result.getInt("id_reseau") %>,'<%= result.getString("nomReseau") %>','<%= result.getString("ip_reseau") %>','<%= result.getString("masque_sous_reseau") %>','<%= result.getString("passerelle") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                        <td><button onclick="confirmpopup(<%= result.getInt("id_reseau") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
                    </tr> 
                <%
                session.removeAttribute("result");
            }
            %>
            </table>
            <div class="form-popup" id="formAjouter">
            <center>
                <form method="post" action="AjouterSousReseau" class="form-container">
                    <h1>Ajouter un sous-réseau</h1>                      
                    <input type="text" name="nomReseau" placeholder="Nom du sous-réseau" required><br>
                    <input type="text" name="ip_reseau" placeholder="Adresse IP" required><br>
                    <input type="text" name="masque_sous_reseau" placeholder="Masque du sous-réseau" required><br>                                                     
                    <input type="text" name="passerelle" placeholder="Passerelle" required><br>
                    <input type="submit" name="submit" value="Ajouter"><br>
                    <button type="button" onclick="closeFormAjouter()"><img class="closeicon" src="images/cancel.png" width="18px" height="auto"/></button>
                </form>
            </center>
        </div>
        <div class="form-popup" id="formModifier">
            <center>
                <form method="post" action="ModifierSousReseau" class="form-container">
                    <h1>Modifier un sous-réseau</h1>                      
                    <input type="text" id="hiddeninput" name="id_reseau" placeholder="Adresse IP" required><br>
                    <input type="text" id="nomReseau" name="nomReseau" placeholder="Nom du sous-réseau" required><br>
                    <input type="text" id="ip_reseau" name="ip_reseau" placeholder="Adresse IP" required><br>
                    <input type="text" id="hiddeninput2" name="old_ip_reseau" placeholder="Adresse IP" required>
                    <input type="text" id="masque_sous_reseau" name="masque_sous_reseau" placeholder="Masque du sous-réseau"><br>                                                     
                    <input type="text" id="passerelle" name="passerelle" placeholder="Passerelle" required><br>
                    <input type="submit" id="submit" name="submit" value="Modifier"><br>
                    <button type="button" onclick="closeFormModifier()"><img class="closeicon" src="images/cancel.png" width="18px" height="auto"/></button>
                </form>
            </center>
        </div>

                <script>
                    function openFormModifier(id_reseau, nomReseau, ip_reseau, masque_sous_reseau, passerelle) 
                    {
                        document.getElementById("formModifier").style.display = "block";
                        document.getElementById("hiddeninput").value = id_reseau;
                        document.getElementById("nomReseau").value = nomReseau;
                        document.getElementById("ip_reseau").value = ip_reseau;
                        document.getElementById("hiddeninput2").value = ip_reseau;
                        document.getElementById("masque_sous_reseau").value = masque_sous_reseau;
                        document.getElementById("passerelle").value = passerelle;

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
                    function confirmpopup(id_reseau)
                    {
                        let confirmAction = confirm("Vous ètes sur ?");
                        if (confirmAction) 
                        {      
                            window.location.href = "/ipmanager/SupprimerSousReseau?id_reseau=" + id_reseau;
                        } 
                    }
                </script>    
        </section>
    </body>
</html>
