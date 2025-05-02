<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Game" %>
<%@ page import="Model.Review" %>
<%@ page import="Model.Sales" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GameReviewHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { padding: 20px; background-color: #f8f9fa; }
        h1, h2 { margin-bottom: 20px; }
        .section { margin-bottom: 40px; }
        .nav-link { margin-right: 20px; }
    </style>
</head>
<body>

<div class="container">

    <!-- Navbar semplificata -->
    <nav class="mb-4">
        <a class="btn btn-outline-primary nav-link" href="#games">🎮 Giochi</a>
        <a class="btn btn-outline-success nav-link" href="#reviews">📝 Recensioni</a>
        <a class="btn btn-outline-warning nav-link" href="#sales">📊 Vendite</a>
    </nav>

    <h1>GameReviewHub</h1>

    <!-- Games Section -->
    <div id="games" class="section">
        <h2>Elenco Giochi</h2>
        <%
            ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
        %>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Piattaforma</th>
                <th>Anno</th>
                <th>Genere</th>
                <th>Publisher</th>
                <th>Developer</th>
                <th>Rating</th>
                <th>Azioni</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (games != null && !games.isEmpty()) {
                    for (Game game : games) {
            %>
            <tr>
                <td><%= game.getIdGame() %></td>
                <td><%= game.getName() %></td>
                <td><%= game.getPlatform() %></td>
                <td><%= game.getYearOfRelease() %></td>
                <td><%= game.getGenre() %></td>
                <td><%= game.getPublisher() %></td>
                <td><%= game.getDeveloper() %></td>
                <td><%= game.getRating() %></td>
                <td>
                    <a href="edit-game-form.jsp?id=<%= game.getIdGame() %>" class="btn btn-sm btn-outline-secondary">✏️</a>
                    <a href="delete-game.jsp?id=<%= game.getIdGame() %>" class="btn btn-sm btn-outline-danger" onclick="return confirm('Sei sicuro di voler eliminare questo gioco?');">🗑️</a>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr><td colspan="9" class="text-center">Nessun gioco disponibile</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Reviews Section -->
    <div id="reviews" class="section">
        <h2>Recensioni</h2>
        <%
            ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
        %>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>ID Recensione</th>
                <th>ID Gioco</th>
                <th>Punteggio Critica</th>
                <th>Conteggio Critica</th>
                <th>Punteggio Utenti</th>
                <th>Conteggio Utenti</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (reviews != null && !reviews.isEmpty()) {
                    for (Review review : reviews) {
            %>
            <tr>
                <td><%= review.getIdReview() %></td>
                <td><%= review.getIdGame() %></td>
                <td><%= review.getCriticScore() %></td>
                <td><%= review.getCriticCount() %></td>
                <td><%= review.getUserScore() %></td>
                <td><%= review.getUserCount() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr><td colspan="6" class="text-center">Nessuna recensione disponibile</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Sales Section -->
    <div id="sales" class="section">
        <h2>Vendite</h2>
        <%
            ArrayList<Sales> sales = (ArrayList<Sales>) request.getAttribute("sales");
        %>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>ID Vendita</th>
                <th>ID Gioco</th>
                <th>NA</th>
                <th>EU</th>
                <th>JP</th>
                <th>Altre</th>
                <th>Globali</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (sales != null && !sales.isEmpty()) {
                    for (Sales s : sales) {
            %>
            <tr>
                <td><%= s.getIdSales() %></td>
                <td><%= s.getIdGame() %></td>
                <td><%= s.getNaSales() %></td>
                <td><%= s.getEuSales() %></td>
                <td><%= s.getJpSales() %></td>
                <td><%= s.getOtherSales() %></td>
                <td><%= s.getGlobalSales() %></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr><td colspan="7" class="text-center">Nessun dato di vendita disponibile</td></tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- CRUD Section -->
    <div class="section">
        <h2>Operazioni CRUD</h2>
        <div class="d-grid gap-2 d-md-flex justify-content-start mb-3">
            <a href="Inserimento" class="btn btn-success me-2">➕ Aggiungi Gioco</a>
        </div>
    </div>

    <!-- Debug Section -->
    <div class="section">
        <h2>Debug</h2>
        <div class="alert alert-secondary">
            Giochi: <%= games != null ? games.size() : "null" %> |
            Recensioni: <%= reviews != null ? reviews.size() : "null" %> |
            Vendite: <%= sales != null ? sales.size() : "null" %>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
