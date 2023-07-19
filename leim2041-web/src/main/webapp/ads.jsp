<%@ page import = "java.util.Collection" %>
<%@ page import = "edu.bbte.idde.leim2041.backend.model.RealEstate" %>
<%@ page import="edu.bbte.idde.leim2041.backend.dao.RealEstateDao" %>
<%@ page import="edu.bbte.idde.leim2041.backend.dao.AbstractDaoFactory" %>

<% RealEstateDao REAL_ESTATE_DAO = AbstractDaoFactory.getDaoFactory().getRealEstateDao(); %>
<html>
    <jsp:include page="./partials/head.jsp"/>
    <body>
        <% Collection<RealEstate> ads = REAL_ESTATE_DAO.findAll(); %>
        <% if (ads.isEmpty()) { %>
            <p>No advertisements available.</p>
        <% } else { %>

            <% for (RealEstate ad : ads) { %>
                <h2> Details of the property:</h2>
                <div class="lakasok formText">
                    <ul style="list-style-type:none;">
                        <li><strong>City: </strong> <%= ad.getCity()  %></li>
                        <li><strong>Neighbourhood: </strong> <%= ad.getNeighbourhood() %></li>
                        <li><strong>Price: </strong><%= ad.getPrice() %></li>
                        <li><strong>Number of rooms: </strong><%= ad.getNumberOfRooms() %></li>
                        <li><strong>Year built: </strong><%= ad.getHasElevator() %></li>
                    </ul>
                </div>
            <% } %>
        <% } %>
        <form action="/leim2041-web/logout" method="get">
            <input type="submit" name="logout" value="Logout" />
        </form>
    </body>
</html>