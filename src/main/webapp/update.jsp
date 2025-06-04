<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Model.Game, Model.GameDAO" %>
<%
    // Recupera l'ID del gioco dai parametri della richiesta
    String gameId = request.getParameter("id");
    Game game = null;

    if (gameId != null && !gameId.trim().isEmpty()) {
        GameDAO gameDAO = new GameDAO();
        try {
            game = gameDAO.getGameById(gameId);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Errore nel recupero dei dati del gioco: " + e.getMessage());
        }
    }

    // Se non è stato trovato il gioco, mostra un errore
    if (game == null && gameId != null) {
        request.setAttribute("errorMessage", "Gioco non trovato con ID: " + gameId);
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiorna Gioco | GameReviewHub</title>
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
            min-height: 100vh;
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
            padding: 60px 0;
            margin-bottom: 30px;
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
            font-size: 2.5rem;
            font-weight: 800;
            text-transform: uppercase;
            text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.6);
            margin-bottom: 10px;
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

        .form-container {
            background-color: rgba(38, 50, 56, 0.7);
            border-radius: 15px;
            padding: 40px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
            position: relative;
            overflow: hidden;
            margin-bottom: 30px;
        }

        .form-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            height: 4px;
            width: 100%;
            background: linear-gradient(90deg, var(--game-color), #9c27b0);
        }

        .form-title {
            display: flex;
            align-items: center;
            margin-bottom: 30px;
            font-weight: 700;
            font-size: 1.5rem;
        }

        .form-icon {
            font-size: 24px;
            margin-right: 15px;
            width: 48px;
            height: 48px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: white;
            background-color: var(--game-color);
        }

        .form-floating {
            margin-bottom: 20px;
        }

        .form-control {
            background-color: rgba(38, 50, 56, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: white;
            border-radius: 10px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            background-color: rgba(38, 50, 56, 0.8);
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(126, 87, 194, 0.25);
            color: white;
        }

        .form-control::placeholder {
            color: rgba(255, 255, 255, 0.5);
        }

        .form-floating > label {
            color: rgba(255, 255, 255, 0.7);
        }

        .form-floating > .form-control:focus ~ label,
        .form-floating > .form-control:not(:placeholder-shown) ~ label {
            color: var(--primary-color);
        }

        .btn-update {
            background: linear-gradient(45deg, var(--primary-color), #9c27b0);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 30px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(126, 87, 194, 0.4);
            width: 100%;
            margin-top: 20px;
        }

        .btn-update:hover {
            background: linear-gradient(45deg, #9c27b0, var(--primary-color));
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(126, 87, 194, 0.6);
            color: white;
        }

        .btn-back {
            background-color: transparent;
            color: rgba(255, 255, 255, 0.7);
            border: 1px solid rgba(255, 255, 255, 0.3);
            padding: 10px 25px;
            border-radius: 25px;
            text-decoration: none;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .btn-back:hover {
            background-color: rgba(255, 255, 255, 0.1);
            color: white;
            border-color: var(--primary-color);
            text-decoration: none;
        }

        .alert-custom {
            border-radius: 10px;
            border: none;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        .alert-danger {
            background-color: rgba(244, 67, 54, 0.1);
            color: #f44336;
            border-left: 4px solid #f44336;
        }

        .alert-success {
            background-color: rgba(76, 175, 80, 0.1);
            color: #4caf50;
            border-left: 4px solid #4caf50;
        }

        .required-field::after {
            content: ' *';
            color: #f44336;
        }

        @media (max-width: 768px) {
            .hero-title {
                font-size: 2rem;
            }
            .form-container {
                padding: 20px;
            }
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .fade-in {
            animation: fadeIn 0.5s ease forwards;
        }
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand fw-bold fs-4" href="#">
            <i class="fas fa-gamepad me-2"></i>GameReviewHub
        </a>
    </div>
</nav>

<!-- Hero Section -->
<div class="hero-section">
    <div class="hero-content">
        <h1 class="hero-title">Aggiorna Gioco</h1>
        <p class="lead">Modifica le informazioni del gioco selezionato</p>
    </div>
</div>

<!-- Main Content -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <!-- Back Button -->
            <a href="javascript:history.back()" class="btn-back">
                <i class="fas fa-arrow-left me-2"></i>Torna Indietro
            </a>

            <!-- Error/Success Messages -->
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
            <div class="alert alert-danger alert-custom fade-in">
                <i class="fas fa-exclamation-triangle me-2"></i>
                <%= errorMessage %>
            </div>
            <% } %>

            <% String successMessage = (String) request.getAttribute("successMessage"); %>
            <% if (successMessage != null) { %>
            <div class="alert alert-success alert-custom fade-in">
                <i class="fas fa-check-circle me-2"></i>
                <%= successMessage %>
            </div>
            <% } %>

            <!-- Update Form -->
            <div class="form-container fade-in">
                <div class="form-title">
                    <div class="form-icon">
                        <i class="fas fa-edit"></i>
                    </div>
                    Aggiorna Informazioni Gioco
                </div>

                <form action="Update" method="post">
                    <%
                        String idValueForHiddenField = "";
                        // 'game' is the Game object from the request (either fetched or from a previous submission attempt)
                        Model.Game gameFromRequest = (Model.Game) request.getAttribute("game");
                        // 'id_game_attr' is the String ID from the request, set by the servlet if 'gameFromRequest' is null but an ID was provided (e.g., game not found)
                        String idGameAttr = (String) request.getAttribute("idGame");

                        if (gameFromRequest != null && gameFromRequest.getIdGame() != null && !gameFromRequest.getIdGame().trim().isEmpty()) {
                            idValueForHiddenField = gameFromRequest.getIdGame();
                        } else if (idGameAttr != null && !idGameAttr.trim().isEmpty()) {
                            idValueForHiddenField = idGameAttr;
                        }
                        // If idValueForHiddenField is still empty, it means no valid ID was provided or found.
                    %>
                    <input type="hidden" name="idGame" value="<%= idValueForHiddenField %>">
                    <div class="row">
                        <div class="col-md-6">
                            <!-- Nome -->
                            <div class="form-floating">
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="Nome del gioco"
                                       value="<%= game != null && game.getName() != null ? game.getName() : "" %>">
                                <label for="name" class="required-field">Nome del Gioco</label>
                            </div>

                            <!-- Piattaforma -->
                            <div class="form-floating">
                                <input type="text" class="form-control" id="platform" name="platform"
                                       placeholder="Piattaforma"
                                       value="<%= game != null && game.getPlatform() != null ? game.getPlatform() : "" %>">
                                <label for="platform">Piattaforma</label>
                            </div>

                            <!-- Anno di Rilascio -->
                            <div class="form-floating">
                                <input type="number" class="form-control" id="year_of_release" name="year_of_release"
                                       placeholder="Anno di rilascio" min="1970" max="2030"
                                       value="<%= game != null && game.getYearOfRelease() != 0 ? game.getYearOfRelease() : "" %>">
                                <label for="year_of_release">Anno di Rilascio</label>
                            </div>

                            <!-- Genere -->
                            <div class="form-floating">
                                <input type="text" class="form-control" id="genre" name="genre"
                                       placeholder="Genere"
                                       value="<%= game != null && game.getGenre() != null ? game.getGenre() : "" %>">
                                <label for="genre">Genere</label>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <!-- Publisher -->
                            <div class="form-floating">
                                <input type="text" class="form-control" id="publisher" name="publisher"
                                       placeholder="Publisher"
                                       value="<%= game != null && game.getPublisher() != null ? game.getPublisher() : "" %>">
                                <label for="publisher">Publisher</label>
                            </div>

                            <!-- Developer -->
                            <div class="form-floating">
                                <input type="text" class="form-control" id="developer" name="developer"
                                       placeholder="Developer"
                                       value="<%= game != null && game.getDeveloper() != null ? game.getDeveloper() : "" %>">
                                <label for="developer">Developer</label>
                            </div>

                            <!-- Rating -->
                            <div class="form-floating">
                                <select class="form-control" id="rating" name="rating">
                                    <option value="">Seleziona Rating</option>
                                    <option value="E" <%= game != null && "E".equals(game.getRating()) ? "selected" : "" %>>E - Everyone</option>
                                    <option value="E10+" <%= game != null && "E10+".equals(game.getRating()) ? "selected" : "" %>>E10+ - Everyone 10+</option>
                                    <option value="T" <%= game != null && "T".equals(game.getRating()) ? "selected" : "" %>>T - Teen</option>
                                    <option value="M" <%= game != null && "M".equals(game.getRating()) ? "selected" : "" %>>M - Mature</option>
                                    <option value="AO" <%= game != null && "AO".equals(game.getRating()) ? "selected" : "" %>>AO - Adults Only</option>
                                    <option value="RP" <%= game != null && "RP".equals(game.getRating()) ? "selected" : "" %>>RP - Rating Pending</option>
                                </select>
                                <label for="rating">Rating</label>
                            </div>

                            <!-- Info aggiuntiva -->
                            <div class="mt-4">
                                <small class="text-muted">
                                    <i class="fas fa-info-circle me-1"></i>
                                    I campi contrassegnati con <span style="color: #f44336;">*</span> sono obbligatori
                                </small>
                            </div>
                        </div>
                    </div>

                    <!-- Submit Button -->
                    <button type="submit" class="btn btn-update">
                        <i class="fas fa-save me-2"></i>Aggiorna Gioco
                    </button>
                </form>
            </div>

            <!-- Game Info Display (se il gioco è stato trovato) -->
            <% if (game != null) { %>
            <div class="form-container fade-in" style="margin-top: 20px;">
                <div class="form-title">
                    <div class="form-icon">
                        <i class="fas fa-info-circle"></i>
                    </div>
                    Informazioni Attuali
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <p><strong>ID:</strong> <%= game.getIdGame() %></p>
                        <p><strong>Nome:</strong> <%= game.getName() != null ? game.getName() : "N/A" %></p>
                        <p><strong>Piattaforma:</strong> <%= game.getPlatform() != null ? game.getPlatform() : "N/A" %></p>
                        <p><strong>Anno:</strong> <%= game.getYearOfRelease() != 0 ? game.getYearOfRelease() : "N/A" %></p>
                    </div>
                    <div class="col-md-6">
                        <p><strong>Genere:</strong> <%= game.getGenre() != null ? game.getGenre() : "N/A" %></p>
                        <p><strong>Publisher:</strong> <%= game.getPublisher() != null ? game.getPublisher() : "N/A" %></p>
                        <p><strong>Developer:</strong> <%= game.getDeveloper() != null ? game.getDeveloper() : "N/A" %></p>
                        <p><strong>Rating:</strong> <%= game.getRating() != null ? game.getRating() : "N/A" %></p>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Animazioni fade-in
    document.addEventListener('DOMContentLoaded', function() {
        const elements = document.querySelectorAll('.fade-in');
        elements.forEach((el, index) => {
            el.style.animationDelay = (index * 0.1) + 's';
        });
    });

    // Validazione form lato client
    document.querySelector('form').addEventListener('submit', function(e) {
        const idGame = document.querySelector('input[name="idGame"]').value;
        if (!idGame || idGame.trim() === '') {
            e.preventDefault();
            alert('Errore: ID del gioco mancante. Impossibile procedere con l\'aggiornamento.');
            return false;
        }
    });
</script>
</body>
</html>