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

<%@page import="java.sql.ResultSet"%>
<%@page import="com.java.mysql.ConnexionBD"%>
<%@page import="com.java.classes.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>IP manager</title>
        <link rel="stylesheet" type="text/css" href="css/TableauDeBord0.css"> 
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);
            <%
                ResultSet result = ConnexionBD.executeSelectQuery("select adresse_ip.id_reseau, reseau.nomReseau, count(*) as nb_ip from adresse_ip, reseau where adresse_ip.id_reseau = reseau.id_reseau group by adresse_ip.id_reseau");
            %>
            function drawChart() {

              var data = google.visualization.arrayToDataTable([
                ['Sous-reseau', 'Nombre d\'adresses IP'],
                <%
                while(result.next())
                {%>
                ['<%=result.getString("nomReseau")%>',<%=result.getInt("nb_ip")%>],
                <% } %>
              ]);

              var options = {
                title: 'Nombres d\'adresses IP par Sous reseau'
              };

              var chart = new google.visualization.PieChart(document.getElementById('piechart'));

              chart.draw(data, options);
            }
        </script>
    </head>
    <body> 
        <jsp:include page="menuNavigationAdmin.jsp"></jsp:include> 
        <div class="content">
            <section class="nb">
                <h1>Nombre d'utilisateurs</h1>
                <%
                    ResultSet resultusers = ConnexionBD.executeSelectQuery("select count(*) as nb_users from user where admin = 0");
                    if(resultusers.first())
                    {%>
                        <p><%= resultusers.getInt("nb_users") %></p>
                    <%}
                %>
            </section>
            <section class="nb">
                <h1>Nombre d'administrateurs</h1>
                <%
                    ResultSet resultadmins = ConnexionBD.executeSelectQuery("select count(*) as nb_admins from user where admin = 1");
                    if(resultadmins.first())
                    {%>
                        <p><%= resultadmins.getInt("nb_admins") %></p>
                    <%}
                %>
            </section>
            <section class="nb">
                <h1>Nombre de sous-reseaux</h1>
                <%
                    ResultSet resultreseau = ConnexionBD.executeSelectQuery("select count(*) as nb_reseaux from reseau");
                    if(resultreseau.first())
                    {%>
                        <p><%= resultreseau.getInt("nb_reseaux") %></p>
                    <%}
                %>
            </section>
            <section class="nb">
                <h1>Nombre d'adresses IP affectés</h1>
                <%
                    ResultSet resultIP = ConnexionBD.executeSelectQuery("select count(*) as nb_ip from adresse_ip");
                    if(resultIP.first())
                    {%>
                        <p><%= resultIP.getInt("nb_ip") %></p>
                    <%}
                %>
            </section>
                <div id="piechart" style="width: 900px; height: 500px;"></div>            
        </div>
    </body>
        
   
</html>
