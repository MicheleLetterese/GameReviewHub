<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, org.bson.Document" %>

<%
    List<Document> risultati = (List<Document>) request.getAttribute("risultati");
    int paginaCorrente = (int) request.getAttribute("page");
    long totaleRisultati = (long) request.getAttribute("totaleRisultati");
    String platform = (String) request.getAttribute("platform");
    String minUserScore = String.valueOf(request.getAttribute("minUserScore"));
%>

<% if (risultati != null && !risultati.isEmpty()) { %>
<div class="section fade-in-delay-3">
    <div class="section-title">
        <h2>Risultati Filtrati</h2>
    </div>
    <div class="table-responsive">
        <table class="table table-dark">
            <thead>
            <tr>
                <th>Nome</th>
                <th>Piattaforma</th>
                <th>Genere</th>
                <th>Developer</th>
                <th>User Score</th>
                <th>Critic Score</th>
            </tr>
            </thead>
            <tbody>
            <% for (Document doc : risultati) { %>
            <tr>
                <td><%= doc.getString("name") %></td>
                <td><%= doc.getString("platform") %></td>
                <td><%= doc.getString("genre") %></td>
                <td><%= doc.getString("developer") %></td>
                <td><%= doc.get("user_score") %></td>
                <td><%= doc.get("critic_score") %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
<% } %>

<% if (totaleRisultati > 15) {
    int totalPages = (int) Math.ceil(totaleRisultati / 15.0);
%>
<nav class="mt-4">
    <ul class="pagination justify-content-center">
        <%
            int maxVisible = 2;
            for (int i = 1; i <= totalPages; i++) {
                if (
                        i <= 2 || i > totalPages - 2 || // prime e ultime 2
                                Math.abs(i - paginaCorrente) <= 2 // due prima/dopo la corrente
                ) {
        %>
        <li class="page-item <%= (i == paginaCorrente) ? "active" : "" %>">
            <button class="page-link page-button"
                    data-page="<%= i %>"
                    data-platform="<%= platform %>"
                    data-score="<%= minUserScore %>"><%= i %></button>
        </li>
        <%
        } else if (
                i == 3 && paginaCorrente > 5 || // puntino dopo 2
                        i == totalPages - 2 && paginaCorrente < totalPages - 4 // puntino prima delle ultime
        ) {
        %>
        <li class="page-item disabled"><span class="page-link">...</span></li>
        <%
                }
            }
        %>

    </ul>
</nav>
<% } %>