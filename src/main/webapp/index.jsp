<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Model.Game" %>
<%@ page import="Model.Review" %>
<%@ page import="Model.Sales" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GameReviewHub | La Tua Destinazione Gaming</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #7e57c2;
            --secondary-color: #ff9800;
            --accent-color: #00bcd4;
            --dark-color: #263238;
            --light-color: #f5f5f5;
            --game-color: #673ab7;
            --review-color: #4caf50;
            --sales-color: #ff9800;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #121212 0%, #2d2d2d 100%);
            color: #ffffff;
            padding: 0;
            margin: 0;
            overflow-x: hidden;
        }

        .navbar {
            background-color: rgba(18, 18, 18, 0.9);
            backdrop-filter: blur(10px);
            padding: .8rem 1rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .hero-section {
            background: linear-gradient(135deg, #1a237e 0%, #311b92 50%, #4a148c 100%);
            position: relative;
            padding: 100px 0;
            margin-bottom: 50px;
            text-align: center;
        }

        .hero-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(126, 87, 194, 0.8) 0%, rgba(0, 188, 212, 0.8) 100%);
        }

        .hero-content {
            position: relative;
            z-index: 2;
        }

        .hero-title {
            font-size: 3.5rem;
            font-weight: 800;
            text-transform: uppercase;
            text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.6);
            margin-bottom: 20px;
            position: relative;
            display: inline-block;
        }

        .hero-title::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 4px;
            background: var(--secondary-color);
            border-radius: 2px;
        }

        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 30px;
            font-weight: 300;
        }

        .section {
            background-color: rgba(38, 50, 56, 0.7);
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 50px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .section:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 40px rgba(0, 0, 0, 0.3);
        }

        .section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            height: 4px;
            width: 100%;
        }

        #games.section::before { background: linear-gradient(90deg, var(--game-color), #9c27b0); }
        #reviews.section::before { background: linear-gradient(90deg, var(--review-color), #8bc34a); }
        #sales.section::before { background: linear-gradient(90deg, var(--sales-color), #ff5722); }

        .section-title {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
            font-weight: 700;
            position: relative;
        }

        .section-icon {
            font-size: 24px;
            margin-right: 15px;
            width: 48px;
            height: 48px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: white;
        }

        #games .section-icon { background-color: var(--game-color); }
        #reviews .section-icon { background-color: var(--review-color); }
        #sales .section-icon { background-color: var(--sales-color); }

        .table-custom {
            border-radius: 10px;
            overflow: hidden;
            background-color: rgba(38, 50, 56, 0.5);
            color: white;
        }

        .table-custom thead {
            background-color: rgba(18, 18, 18, 0.8);
        }

        .table-custom th {
            font-weight: 600;
            border-bottom: none;
            text-transform: uppercase;
            letter-spacing: 1px;
            font-size: 0.9rem;
            padding: 15px;
        }

        .table-custom td {
            vertical-align: middle;
            padding: 12px 15px;
            border-color: rgba(255, 255, 255, 0.1);
        }

        .table-games tr:hover { background-color: rgba(126, 87, 194, 0.2); }
        .table-reviews tr:hover { background-color: rgba(76, 175, 80, 0.2); }
        .table-sales tr:hover { background-color: rgba(255, 152, 0, 0.2); }

        .badge-platform {
            background-color: var(--primary-color);
            color: white;
            font-size: 0.8rem;
            padding: 5px 10px;
            border-radius: 20px;
        }

        .badge-genre {
            background-color: var(--accent-color);
            color: white;
            font-size: 0.8rem;
            padding: 5px 10px;
            border-radius: 20px;
        }

        .badge-year {
            background-color: var(--dark-color);
            color: white;
            font-size: 0.8rem;
            padding: 5px 10px;
            border-radius: 20px;
        }

        .rating-stars {
            color: gold;
            letter-spacing: 2px;
        }

        .btn-action {
            margin: 2px;
            border-radius: 50%;
            width: 36px;
            height: 36px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
        }

        .btn-edit {
            background-color: var(--primary-color);
            color: white;
            border: none;
        }

        .btn-delete {
            background-color: #f44336;
            color: white;
            border: none;
        }

        .btn-edit:hover {
            background-color: #5e35b1;
            transform: rotate(15deg);
        }

        .btn-delete:hover {
            background-color: #d32f2f;
            transform: rotate(-15deg);
        }

        .btn-add-game {
            background: linear-gradient(45deg, var(--primary-color), #9c27b0);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 30px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(126, 87, 194, 0.4);
        }

        .btn-add-game:hover {
            background: linear-gradient(45deg, #9c27b0, var(--primary-color));
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(126, 87, 194, 0.6);
            color: white;
        }

        .debug-panel {
            background: rgba(0, 0, 0, 0.5);
            border-radius: 10px;
            padding: 15px;
        }

        .debug-item {
            display: inline-block;
            margin-right: 15px;
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 0.9rem;
            font-weight: 500;
        }

        .debug-games { background-color: rgba(126, 87, 194, 0.3); }
        .debug-reviews { background-color: rgba(76, 175, 80, 0.3); }
        .debug-sales { background-color: rgba(255, 152, 0, 0.3); }

        .nav-tabs {
            border-bottom: none;
            margin-bottom: 20px;
        }

        .nav-tabs .nav-link {
            background-color: transparent;
            border: none;
            color: rgba(255, 255, 255, 0.7);
            border-radius: 0;
            font-weight: 500;
            padding: 12px 20px;
            position: relative;
            transition: all 0.3s ease;
        }

        .nav-tabs .nav-link::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 0;
            height: 3px;
            background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
            transition: width 0.3s ease;
        }

        .nav-tabs .nav-link:hover {
            color: white;
        }

        .nav-tabs .nav-link:hover::after {
            width: 100%;
        }

        .nav-tabs .nav-link.active {
            color: white;
            background-color: transparent;
            font-weight: 700;
        }

        .nav-tabs .nav-link.active::after {
            width: 100%;
        }

        .search-container {
            position: relative;
            margin-bottom: 30px;
        }

        .search-input {
            width: 100%;
            background-color: rgba(38, 50, 56, 0.5);
            color: white;
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 30px;
            padding: 12px 20px 12px 45px;
            transition: all 0.3s ease;
        }

        .search-input:focus {
            background-color: rgba(38, 50, 56, 0.8);
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(126, 87, 194, 0.25);
            color: white;
        }

        .search-input::placeholder {
            color: rgba(255, 255, 255, 0.5);
        }

        .search-icon {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: rgba(255, 255, 255, 0.5);
        }

        .footer {
            background-color: rgba(18, 18, 18, 0.9);
            padding: 30px 0;
            margin-top: 50px;
            text-align: center;
        }

        .footer-logo {
            font-size: 1.8rem;
            font-weight: 800;
            color: white;
            margin-bottom: 20px;
        }

        .footer-links {
            margin-bottom: 20px;
        }

        .footer-link {
            color: rgba(255, 255, 255, 0.7);
            margin: 0 10px;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .footer-link:hover {
            color: var(--primary-color);
        }

        .footer-social {
            margin-bottom: 20px;
        }

        .social-icon {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.1);
            color: white;
            margin: 0 5px;
            font-size: 1.2rem;
            transition: all 0.3s ease;
        }

        .social-icon:hover {
            background-color: var(--primary-color);
            transform: translateY(-3px);
        }

        /* Responsive styles */
        @media (max-width: 992px) {
            .hero-title {
                font-size: 2.5rem;
            }
            .table-responsive {
                font-size: 0.9rem;
            }
        }

        @media (max-width: 768px) {
            .hero-title {
                font-size: 2rem;
            }
            .section {
                padding: 20px;
            }
            .table-responsive {
                font-size: 0.8rem;
            }
            .debug-item {
                display: block;
                margin-bottom: 10px;
            }
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .fade-in {
            animation: fadeIn 0.5s ease forwards;
        }

        .fade-in-delay-1 {
            opacity: 0;
            animation: fadeIn 0.5s ease forwards 0.2s;
        }

        .fade-in-delay-2 {
            opacity: 0;
            animation: fadeIn 0.5s ease forwards 0.4s;
        }

        .fade-in-delay-3 {
            opacity: 0;
            animation: fadeIn 0.5s ease forwards 0.6s;
        }

        /* Custom scrollbar */
        ::-webkit-scrollbar {
            width: 12px;
        }

        ::-webkit-scrollbar-track {
            background: rgba(18, 18, 18, 0.8);
        }

        ::-webkit-scrollbar-thumb {
            background: var(--primary-color);
            border-radius: 6px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #5e35b1;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="#">
            <i class="fas fa-gamepad me-2"></i>
            <span class="fw-bold">GameReviewHub</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="#games"><i class="fas fa-gamepad me-1"></i> Giochi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#reviews"><i class="fas fa-star me-1"></i> Recensioni</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#sales"><i class="fas fa-chart-line me-1"></i> Vendite</a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-add-game btn-sm ms-2" href="Inserimento">
                        <i class="fas fa-plus me-1"></i> Nuovo Gioco
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<div class="hero-section">
    <div class="container hero-content">
        <h1 class="hero-title fade-in">GameReviewHub</h1>
        <p class="hero-subtitle fade-in-delay-1">La tua destinazione definitiva per recensioni e dati sui videogiochi</p>
    </div>
</div>

<div class="container">
    <!-- Main Tabs Navigation -->
    <ul class="nav nav-tabs fade-in-delay-1" id="mainTabs" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="all-tab" data-bs-toggle="tab" data-bs-target="#all-content" type="button" role="tab">
                <i class="fas fa-border-all me-1"></i> Tutto
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="games-tab" data-bs-toggle="tab" data-bs-target="#games-tab-content" type="button" role="tab">
                <i class="fas fa-gamepad me-1"></i> Giochi
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="reviews-tab" data-bs-toggle="tab" data-bs-target="#reviews-tab-content" type="button" role="tab">
                <i class="fas fa-star me-1"></i> Recensioni
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="sales-tab" data-bs-toggle="tab" data-bs-target="#sales-tab-content" type="button" role="tab">
                <i class="fas fa-chart-line me-1"></i> Vendite
            </button>
        </li>
    </ul>

    <!-- Tab Content -->
    <div class="tab-content fade-in-delay-2" id="mainTabsContent">
        <!-- All Content Tab -->
        <div class="tab-pane fade show active" id="all-content" role="tabpanel">
            <!-- Games Section -->
            <div id="games" class="section fade-in-delay-1">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-gamepad"></i>
                    </div>
                    <h2>Elenco Giochi</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchGames" placeholder="Cerca per nome, genere, publisher...">
                </div>

                <div class="table-responsive">
                    <%
                        ArrayList<Game> games = (ArrayList<Game>) request.getAttribute("games");
                    %>
                    <table class="table table-custom table-games" id="gamesTable">
                        <thead>
                        <tr>
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
                                    String ratingStars = "";
                                    if (game.getRating() != null && !game.getRating().isEmpty()) {
                                        try {
                                            float rating = Float.parseFloat(game.getRating());
                                            int fullStars = (int) rating;
                                            boolean hasHalfStar = (rating - fullStars) >= 0.5;

                                            for (int i = 0; i < fullStars; i++) {
                                                ratingStars += "<i class='fas fa-star'></i>";
                                            }

                                            if (hasHalfStar) {
                                                ratingStars += "<i class='fas fa-star-half-alt'></i>";
                                            }

                                            for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) {
                                                ratingStars += "<i class='far fa-star'></i>";
                                            }
                                        } catch (NumberFormatException e) {
                                            ratingStars = game.getRating();
                                        }
                                    }
                        %>
                        <tr>
                            <td><strong><%= game.getName() %></strong></td>
                            <td><span class="badge badge-platform"><%= game.getPlatform() %></span></td>
                            <td><span class="badge badge-year"><%= game.getYearOfRelease() %></span></td>
                            <td><span class="badge badge-genre"><%= game.getGenre() %></span></td>
                            <td><%= game.getPublisher() %></td>
                            <td><%= game.getDeveloper() %></td>
                            <td><div class="rating-stars"><%= ratingStars %></div></td>
                            <td>
                                <div class="d-flex">
                                    <a href="GameDetails?id=<%= game.getIdGame() %>" class="btn btn-action" style="background-color: var(--accent-color); color: white;" data-bs-toggle="tooltip" title="Dettagli">
                                        <i class="fas fa-info-circle"></i>
                                    </a>
                                    <a href="edit-game-form.jsp?id=<%= game.getIdGame() %>" class="btn btn-action btn-edit" data-bs-toggle="tooltip" title="Modifica">
                                        <i class="fas fa-edit"></i>
                                    </a>
                                    <a href="Delete?id=<%= game.getIdGame() %>" class="btn btn-action btn-delete" onclick="return confirm('Sei sicuro di voler eliminare questo gioco?');" data-bs-toggle="tooltip" title="Elimina">
                                        <i class="fas fa-trash-alt"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr><td colspan="9" class="text-center py-4"><i class="fas fa-exclamation-circle me-2"></i> Nessun gioco disponibile</td></tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Reviews Section -->
            <div id="reviews" class="section fade-in-delay-2">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <h2>Recensioni</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchReviews" placeholder="Cerca per ID gioco...">
                </div>

                <div class="table-responsive">
                    <%
                        ArrayList<Review> reviews = (ArrayList<Review>) request.getAttribute("reviews");
                    %>
                    <table class="table table-custom table-reviews" id="reviewsTable">
                        <thead>
                        <tr>
                            <th>Nome Gioco</th>
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
                                    String gameName = "N/D";
                                    if (games != null) {
                                        for (Game game : games) {
                                            System.out.println("game: " + game.getName() + " con id: " + game.getIdGame() + " e idReview = " + review.getIdGame());
                                            if (game.getIdGame() == review.getIdGame()) {
                                                System.out.println("Entro nell'if. game.getIdGame() = " + game.getIdGame() + " review.getIdGame = " + review.getIdGame());
                                                gameName = game.getName();
                                                break;
                                            }
                                        }
                                    }

                                    String criticScoreClass = "";
                                    if (review.getCriticScore() >= 75) criticScoreClass = "text-success";
                                    else if (review.getCriticScore() >= 50) criticScoreClass = "text-warning";
                                    else criticScoreClass = "text-danger";

                                    String userScoreClass = "";
                                    if (review.getUserScore() >= 7.5) userScoreClass = "text-success";
                                    else if (review.getUserScore() >= 5.0) userScoreClass = "text-warning";
                                    else userScoreClass = "text-danger";
                        %>
                        <tr>
                            <td><strong><%= gameName %></strong></td>
                            <td class="<%= criticScoreClass %>"><strong><%= review.getCriticScore() %></strong>/100</td>
                            <td><%= review.getCriticCount() %> recensioni</td>
                            <td class="<%= userScoreClass %>"><strong><%= review.getUserScore() %></strong>/10</td>
                            <td><%= review.getUserCount() %> recensioni</td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr><td colspan="7" class="text-center py-4"><i class="fas fa-exclamation-circle me-2"></i> Nessuna recensione disponibile</td></tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Sales Section -->
            <div id="sales" class="section fade-in-delay-3">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <h2>Vendite</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchSales" placeholder="Cerca per ID gioco...">
                </div>

                <div class="table-responsive">
                    <%
                        ArrayList<Sales> sales = (ArrayList<Sales>) request.getAttribute("sales");
                    %>
                    <table class="table table-custom table-sales" id="salesTable">
                        <thead>
                        <tr>
                            <th>Nome Gioco</th>
                            <th>Nord America</th>
                            <th>Europa</th>
                            <th>Giappone</th>
                            <th>Altre Regioni</th>
                            <th>Vendite Globali</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (sales != null && !sales.isEmpty()) {
                                for (Sales s : sales) {
                                    String gameName = "N/D";
                                    if (games != null) {
                                        for (Game game : games) {
                                            if (game.getIdGame() == s.getIdGame()) {
                                                gameName = game.getName();
                                                break;
                                            }
                                        }
                                    }
                        %>
                        <tr>
                            <td><strong><%= gameName %></strong></td>
                            <td><i class="fas fa-dollar-sign me-1 text-success"></i> <%= s.getNaSales() %> mil</td>
                            <td><i class="fas fa-euro-sign me-1 text-primary"></i> <%= s.getEuSales() %> mil</td>
                            <td><i class="fas fa-yen-sign me-1 text-danger"></i> <%= s.getJpSales() %> mil</td>
                            <td><i class="fas fa-globe-asia me-1 text-info"></i> <%= s.getOtherSales() %> mil</td>
                            <td><strong><i class="fas fa-globe me-1 text-warning"></i> <%= s.getGlobalSales() %> mil</strong></td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr><td colspan="8" class="text-center py-4"><i class="fas fa-exclamation-circle me-2"></i> Nessun dato di vendita disponibile</td></tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <!-- Individual Tab Contents -->
        <div class="tab-pane fade" id="games-tab-content" role="tabpanel">
            <div class="section">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-gamepad"></i>
                    </div>
                    <h2>Elenco Giochi</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchGamesTab" placeholder="Cerca per nome, genere, publisher...">
                </div>

                <div class="table-responsive">
                    <table class="table table-custom table-games" id="gamesTableTab">
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
                        <!-- Stessi dati della tabella principale -->
                        </tbody>
                    </table>
                </div>

                <div class="mt-4">
                    <a href="Inserimento" class="btn btn-add-game">
                        <i class="fas fa-plus me-2"></i> Aggiungi Nuovo Gioco
                    </a>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="reviews-tab-content" role="tabpanel">
            <div class="section">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-star"></i>
                    </div>
                    <h2>Recensioni</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchReviewsTab" placeholder="Cerca per ID gioco...">
                </div>

                <div class="table-responsive">
                    <table class="table table-custom table-reviews" id="reviewsTableTab">
                        <thead>
                        <tr>
                            <th>ID Recensione</th>
                            <th>ID Gioco</th>
                            <th>Nome Gioco</th>
                            <th>Punteggio Critica</th>
                            <th>Conteggio Critica</th>
                            <th>Punteggio Utenti</th>
                            <th>Conteggio Utenti</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Stessi dati della tabella principale -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="sales-tab-content" role="tabpanel">
            <div class="section">
                <div class="section-title">
                    <div class="section-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <h2>Vendite</h2>
                </div>

                <div class="search-container">
                    <i class="fas fa-search search-icon"></i>
                    <input type="text" class="form-control search-input" id="searchSalesTab" placeholder="Cerca per ID gioco...">
                </div>

                <div class="table-responsive">
                    <table class="table table-custom table-sales" id="salesTableTab">
                        <thead>
                        <tr>
                            <th>ID Vendita</th>
                            <th>ID Gioco</th>
                            <th>Nome Gioco</th>
                            <th>Nord America</th>
                            <th>Europa</th>
                            <th>Giappone</th>
                            <th>Altre Regioni</th>
                            <th>Vendite Globali</th>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Stessi dati della tabella principale -->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Debug Section -->
    <div class="section fade-in-delay-3">
        <div class="section-title">
            <div class="section-icon" style="background-color: #424242;">
                <i class="fas fa-bug"></i>
            </div>
            <h2>Informazioni Debug</h2>
        </div>

        <div class="debug-panel">
            <div class="debug-item debug-games">
                <i class="fas fa-gamepad me-2"></i>
                Giochi: <strong><%= games != null ? games.size() : "null" %></strong>
            </div>
            <div class="debug-item debug-reviews">
                <i class="fas fa-star me-2"></i>
                Recensioni: <strong><%= reviews != null ? reviews.size() : "null" %></strong>
            </div>
            <div class="debug-item debug-sales">
                <i class="fas fa-chart-line me-2"></i>
                Vendite: <strong><%= sales != null ? sales.size() : "null" %></strong>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="footer-logo">
            <i class="fas fa-gamepad me-2"></i> GameReviewHub
        </div>
        <div class="footer-links">
            <a href="#" class="footer-link">Chi Siamo</a>
            <a href="#" class="footer-link">Contatti</a>
            <a href="#" class="footer-link">Privacy Policy</a>
            <a href="#" class="footer-link">Termini di Servizio</a>
        </div>
        <div class="footer-social">
            <a href="#" class="social-icon"><i class="fab fa-facebook-f"></i></a>
            <a href="#" class="social-icon"><i class="fab fa-twitter"></i></a>
            <a href="#" class="social-icon"><i class="fab fa-instagram"></i></a>
            <a href="#" class="social-icon"><i class="fab fa-discord"></i></a>
            <a href="#" class="social-icon"><i class="fab fa-youtube"></i></a>
        </div>
        <div class="footer-copyright">
            <small>&copy; <%= java.time.Year.now().getValue() %> GameReviewHub. Tutti i diritti riservati.</small>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Initialize tooltips
        const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        tooltipTriggerList.map(function (tooltipTriggerEl) {
            return new bootstrap.Tooltip(tooltipTriggerEl);
        });

        // Funzione di ricerca tabella
        function setupSearch(inputId, tableId, columnIndexes) {
            const searchInput = document.getElementById(inputId);
            if (!searchInput) return;

            searchInput.addEventListener('keyup', function() {
                const searchText = searchInput.value.toLowerCase();
                const table = document.getElementById(tableId);
                if (!table) return;

                const rows = table.getElementsByTagName('tr');

                for (let i = 1; i < rows.length; i++) { // Parte da 1 per saltare l'header
                    const row = rows[i];
                    let shouldShow = false;

                    for (let j = 0; j < columnIndexes.length; j++) {
                        const cell = row.getElementsByTagName('td')[columnIndexes[j]];
                        if (cell) {
                            const text = cell.textContent || cell.innerText;
                            if (text.toLowerCase().indexOf(searchText) > -1) {
                                shouldShow = true;
                                break;
                            }
                        }
                    }

                    row.style.display = shouldShow ? '' : 'none';
                }
            });
        }

        // Setup search for games tables
        setupSearch('searchGames', 'gamesTable', [1, 2, 3, 4, 5, 6]);
        setupSearch('searchGamesTab', 'gamesTableTab', [1, 2, 3, 4, 5, 6]);

        // Setup search for reviews tables
        setupSearch('searchReviews', 'reviewsTable', [1, 2]);
        setupSearch('searchReviewsTab', 'reviewsTableTab', [1, 2]);

        // Setup search for sales tables
        setupSearch('searchSales', 'salesTable', [1, 2]);
        setupSearch('searchSalesTab', 'salesTableTab', [1, 2]);

        // Sync tabs with main content
        const tabEl = document.querySelector('button[data-bs-toggle="tab"]');
        if (tabEl) {
            tabEl.addEventListener('shown.bs.tab', function (e) {
                if (e.target.id === 'games-tab') {
                    document.getElementById('gamesTableTab').innerHTML = document.getElementById('gamesTable').innerHTML;
                } else if (e.target.id === 'reviews-tab') {
                    document.getElementById('reviewsTableTab').innerHTML = document.getElementById('reviewsTable').innerHTML;
                } else if (e.target.id === 'sales-tab') {
                    document.getElementById('salesTableTab').innerHTML = document.getElementById('salesTable').innerHTML;
                }
            });
        }

        // Funzione per animare le sezioni al caricamento
        function animateSections() {
            const sections = document.querySelectorAll('.section');
            const options = {
                root: null,
                rootMargin: '0px',
                threshold: 0.1
            };

            const observer = new IntersectionObserver((entries, observer) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('fade-in');
                        observer.unobserve(entry.target);
                    }
                });
            }, options);

            sections.forEach(section => {
                observer.observe(section);
            });
        }

        // Inizializza animazioni
        animateSections();
    });
</script>
</body>
</html>