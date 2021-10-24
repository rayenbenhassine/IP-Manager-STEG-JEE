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

<%@page import="com.java.mysql.ConnexionBD"%>
<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/Reseau2.css"> 
    </head>
    <body onload="afficherpopup()">
        <script>
            function afficherpopup()
            {
                <% if (session.getAttribute("infosearch") != null) {%>
                    alert("<%= session.getAttribute("infosearch") %>");
                    <% session.removeAttribute("infosearch");     
                } %>
                <% if (session.getAttribute("infoimport") != null) {%>
                    alert("<%= session.getAttribute("infoimport") %>");
                    <% session.removeAttribute("infoimport");     
                } %>  
                <% if (session.getAttribute("infoAjouterIP") != null) {%>
                    alert("<%= session.getAttribute("infoAjouterIP") %>");
                    <% session.removeAttribute("infoAjouterIP");     
                } %>  
                <% if (session.getAttribute("infoModifierIP") != null) {%>
                    alert("<%= session.getAttribute("infoModifierIP") %>");
                    <% session.removeAttribute("infoModifierIP");     
                } %> 
                <% if (request.getParameter("ip") != null) {%>
                    open_ajouter_ip("<%= request.getParameter("ip") %>");                         
                <% } %>
            }
        </script>
        <jsp:include page="menuNavigationAdmin.jsp"></jsp:include>             
        <section id="reseau">
            <h1>Liste des adresses IP</h1>
            <div class="clear-fix">
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
                <div class="excel">
                    <button class="ajouterbtn" onclick="openFormAjouter()" >Ajouter une adresse IP</button><br>
                    <button class="excelbtn" onclick="openFormImporter()" >Importer depuis un ficher excel</button>
                    <button class="excelbtn" onclick="telecharger()" >Exporter un ficher excel</button>                    
                </div>
            </div>
         
            
            <table>
                <tr>
                    <th>Matricule</th>
                    <th>Nom et Prenom</th>
                    <th>Adresse IP</th>
                    <th>Nom du Sous-réseau</th>
                    <th>Modifier</th>
                    <th>Supprimer</th>
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
                            <td><button onclick="openFormModifier(<%= result.getLong("matricule") %>,'<%= result.getString("prenom") %>','<%= result.getString("adresse_ip") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                            <td><button onclick="confirmpopup(<%= result.getLong("matricule") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
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
                            <td><button onclick="openFormModifier(<%= resultmatricule.getLong("matricule") %>,'<%= resultmatricule.getString("prenom") %>','<%= resultmatricule.getString("adresse_ip") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                            <td><button onclick="confirmpopup(<%= resultmatricule.getLong("matricule") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
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
                            <td><button onclick="openFormModifier(<%= resultIP.getLong("matricule") %>,'<%= resultIP.getString("prenom") %>','<%= resultIP.getString("adresse_ip") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                            <td><button onclick="confirmpopup(<%= resultIP.getLong("matricule") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
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
                        <td><button onclick="openFormModifier(<%= result.getLong("matricule") %>,'<%= result.getString("prenom") %>','<%= result.getString("adresse_ip") %>')"><img src="images/edit.png" width="25px" height="auto" alt="alt"/></button></td>
                        <td><button onclick="confirmpopup(<%= result.getLong("matricule") %>)"><img src="images/remove.png" width="15px" height="auto" alt="alt"/></button></td>
                    </tr>
                  <%}
                }
                %>
            </table>
        </section>
        <div class="form-popup" id="formImporter">
            <center>
                <form method="post" action="ImporterExcel" class="form-container" enctype="multipart/form-data">
                    <h1>Importer un ficher excel</h1>                      
                    <input type="file" accept=".xlsx" name="file" required><br>                                
                    <input type="submit" value="Impoter"><br>
                    <button type="button" onclick="closeFormImporter()"><img class="closeicon" src="images/cancel.png" width="18px" height="auto"/></button>
                </form>
            </center>
        </div>
        <div class="form-popup" id="formAjouter">
            <center>
                <form method="post" action="AjouterAdresseIP" class="form-container">
                    <h1>Ajouter une adresse IP</h1>                      
                    <input type="text" name="matricule" placeholder="Matricule" required><br>
                    <input type="text" name="prenom" placeholder="Prénom" required><br>
                    <input type="text" id="add" name="adresse_ip" placeholder="Adresse IP"><br>                                                     
                    <input type="submit" name="submit" value="Ajouter"><br>
                    <button type="button" onclick="closeFormAjouter()"><img class="closeiconajouter" src="images/cancel.png" width="18px" height="auto"/></button>
                </form>
            </center>
        </div>
        <div class="form-popup" id="formModifier">
            <center>
                <form method="post" action="ModifierAdresseIP" class="form-container">
                    <h1>Modifier une adresse IP</h1>                      
                    <input type="text" id="matricule" name="matricule" placeholder="Matricule" required><br>
                    <input type="text" id="hiddeninput" name="hiddeninput" placeholder="Matricule" required>
                    <input type="text" id="hiddeninput1" name="hiddeninput1" placeholder="adresse ip" required>
                    <input type="text" id="prenom" name="prenom" placeholder="Prénom" required><br>
                    <input type="text" id="adresse_ip" name="adresse_ip" placeholder="Adresse IP"><br>                                                     
                    <input type="submit" id="submit" name="submit" value="Modifier"><br>
                    <button type="button" onclick="closeFormModifier()"><img class="closeiconajouter" src="images/cancel.png" width="18px" height="auto"/></button>
                </form>
            </center>
        </div>
        <script>
            function openFormModifier(matricule, prenom,adresse_ip) 
            {
                document.getElementById("formModifier").style.display = "block";
                document.getElementById("matricule").value = matricule;
                document.getElementById("prenom").value = prenom;
                document.getElementById("adresse_ip").value = adresse_ip;
                document.getElementById("hiddeninput").value = matricule;
                document.getElementById("hiddeninput1").value = adresse_ip;
            }
            function openFormImporter() 
            {
                document.getElementById("formImporter").style.display = "block";
            }
            function closeFormImporter() 
            {
                
                document.getElementById("formImporter").style.display = "none";
                
            }
            function telecharger()
            {
                window.location.href = "/ipmanager/ExporterExcel";
            }
            function open_ajouter_ip(ip)
            {
                document.getElementById("formAjouter").style.display = "block";
                document.getElementById("add").value = ip;
            }
            function openFormAjouter()
            {
                document.getElementById("formAjouter").style.display = "block";
            }
            function closeFormAjouter() 
            {
                <% if (request.getParameter("ip") != null) {%>
                    window.location.href = "/ipmanager/reseau.jsp";                         
                <% } %>
              document.getElementById("formAjouter").style.display = "none";
            }
            function closeFormModifier() 
            {
              document.getElementById("formModifier").style.display = "none";
            }
            function confirmpopup(matricule)
            {
                let confirmAction = confirm("Vous ètes sur ?");
                if (confirmAction) 
                {
                    window.location.href = "/ipmanager/SupprimerAdresseIP?matricule=" + matricule;
                } 
            }
        </script>
    </body>
</html>
