<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GameReviewHub | Modifica Gioco</title>
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
            --update-color: #2196f3;
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
            background: linear-gradient(135deg, #0d47a1 0%, #1565c0 50%, #1976d2 100%);
            position: relative;
            padding: 70px 0;
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
            background: linear-gradient(135deg, rgba(33, 150, 243, 0.8) 0%, rgba(0, 188, 212, 0.8) 100%);
        }

        .hero-content {
            position: relative;
            z-index: 2;
        }

        .hero-title {
            font-size: 2.8rem;
            font-weight: 800;
            text-transform: uppercase;
            text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.6);
            margin-bottom: 15px;
            position: relative;
            display: inline-block;
        }

        .hero-title::after {
            content: '';
            position: absolute;
            bottom: -10px;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 4px;
            background: var(--update-color);
            border-radius: 2px;
        }

        .hero-subtitle {
            font-size: 1.3rem;
            margin-bottom: 20px;
            font-weight: 300;
        }

        .section {
            background-color: rgba(38, 50, 56, 0.7);
            border-radius: 15px;
            padding: 30px;
            margin-bottom: 30px;
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

        #game-section.section::before { background: linear-gradient(90deg, var(--game-color), #9c27b0); }
        #review-section.section::before { background: linear-gradient(90deg, var(--review-color), #8bc34a); }
        #sales-section.section::before { background: linear-gradient(90deg, var(--sales-color), #ff5722); }

        .section-title {
            display: flex;
            align-items: center;
            margin-bottom: 25px;
            font-weight: 700;
            position: relative;
        }

        .section-icon {
            font-size: 22px;
            margin-right: 15px;
            width: 45px;
            height: 45px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
            color: white;
        }

        #game-section .section-icon { background-color: var(--game-color); }
        #review-section .section-icon { background-color: var(--review-color); }
        #sales-section .section-icon { background-color: var(--sales-color); }

        .form-control {
            background-color: rgba(38, 50, 56, 0.5);
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: white;
            border-radius: 8px;
            padding: 12px 15px;
            margin-bottom: 15px;
            transition: all 0.3s ease;
        }

        .form-control:focus {
            background-color: rgba(38, 50, 56, 0.8);
            border-color: var(--update-color);
            box-shadow: 0 0 0 0.25rem rgba(33, 150, 243, 0.25);
            color: white;
        }

        .form-control::placeholder {
            color: rgba(255, 255, 255, 0.5);
        }

        .form-control[readonly] {
            background-color: rgba(38, 50, 56, 0.3);
            border-color: rgba(255, 255, 255, 0.1);
            opacity: 0.7;
        }

        .btn-update {
            background: linear-gradient(45deg, var(--update-color), #1976d2);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 30px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(33, 150, 243, 0.4);
            margin-top: 20px;
        }

        .btn-update:hover {
            background: linear-gradient(45deg, #1976d2, var(--update-color));
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.6);
            color: white;
        }

        .btn-delete {
            background: linear-gradient(45deg, #d32f2f, #f44336);
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 30px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(211, 47, 47, 0.4);
            margin-top: 20px;
        }

        .btn-delete:hover {
            background: linear-gradient(45deg, #f44336, #d32f2f);
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(211, 47, 47, 0.6);
            color: white;
        }

        .alert-info {
            background: linear-gradient(135deg, rgba(33, 150, 243, 0.1), rgba(0, 188, 212, 0.1));
            border: 1px solid rgba(33, 150, 243, 0.3);
            color: #81d4fa;
            border-radius: 10px;
            margin-bottom: 30px;
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
            color: var(--update-color);
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
            background-color: var(--update-color);
            transform: translateY(-3px);
        }

        .form-floating label {
            color: rgba(255, 255, 255, 0.7);
        }

        .form-floating>.form-control:focus~label,
        .form-floating>.form-control:not(:placeholder-shown)~label {
            color: rgba(255, 255, 255, 0.9);
            background-color: transparent;
        }

        .input-group-text {
            background-color: rgba(33, 150, 243, 0.3);
            border: 1px solid rgba(255, 255, 255, 0.2);
            color: white;
        }

        .readonly-indicator {
            position: absolute;
            top: -8px;
            right: 10px;
            background: var(--update-color);
            color: white;
            font-size: 10px;
            padding: 2px 8px;
            border-radius: 10px;
            text-transform: uppercase;
            font-weight: 600;
        }

        /* Responsive styles */
        @media (max-width: 992px) {
            .hero-title {
                font-size: 2.2rem;
            }
        }

        @media (max-width: 768px) {
            .hero-title {
                font-size: 1.8rem;
            }
            .section {
                padding: 20px;
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
            background: var(--update-color);
            border-radius: 6px;
        }

        ::-webkit-scrollbar-thumb:hover {
            background: #1976d2;
        }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="index.jsp">
            <i class="fas fa-gamepad me-2"></i>
            <span class="fw-bold">GameReviewHub</span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp#games"><i class="fas fa-gamepad me-1"></i> Giochi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp#reviews"><i class="fas fa-star me-1"></i> Recensioni</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="index.jsp#sales"><i class="fas fa-chart-line me-1"></i> Vendite</a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-add-game btn-sm ms-2" href="inserimento.jsp">
                        <i class="fas fa-plus me-1"></i> Nuovo Gioco
                    </a>
                </li>
                <li class="nav-item">
                    <a class="btn btn-add-game btn-sm ms-2 active" href="#">
                        <i class="fas fa-edit me-1"></i> Modifica Gioco
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<div class="hero-section">
    <div class="container hero-content">
        <h1 class="hero-title fade-in">Modifica Gioco</h1>
        <p class="hero-subtitle fade-in-delay-1">Aggiorna le informazioni del gioco selezionato</p>
    </div>
</div>

<div class="container">

    <!-- Alert Info -->
    <div class="alert alert-info fade-in">
        <i class="fas fa-info-circle me-2"></i>
        <strong>Nota:</strong> Gli ID non possono essere modificati per mantenere l'integrità del database.
        Modifica solo i campi necessari e salva le modifiche.
    </div>

    <form action="UpdateGame" method="post">

        <!-- Sezione Gioco -->
        <div id="game-section" class="section fade-in-delay-1">
            <div class="section-title">
                <div class="section-icon">
                    <i class="fas fa-gamepad"></i>
                </div>
                <h2>Dati Gioco</h2>
            </div>

            <div class="row g-3">
                <div class="col-md-4 position-relative">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-hashtag"></i></span>
                        <input name="idGame" class="form-control" value="${game.idGame}" readonly>
                        <div class="readonly-indicator">Read Only</div>
                    </div>
                </div>
                <div class="col-md-8">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-font"></i></span>
                        <input name="name" class="form-control" placeholder="Nome" value="${game.name}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-server"></i></span>
                        <input name="platform" class="form-control" placeholder="Piattaforma" value="${game.platform}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-calendar-alt"></i></span>
                        <input name="year" class="form-control" placeholder="Anno di Rilascio" value="${game.year}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-tags"></i></span>
                        <input name="genre" class="form-control" placeholder="Genere" value="${game.genre}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-building"></i></span>
                        <input name="publisher" class="form-control" placeholder="Publisher" value="${game.publisher}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-laptop-code"></i></span>
                        <input name="developer" class="form-control" placeholder="Developer" value="${game.developer}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-shield-alt"></i></span>
                        <input name="rating" class="form-control" placeholder="Rating ESRB" value="${game.rating}" required>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sezione Recensione -->
        <div id="review-section" class="section fade-in-delay-2">
            <div class="section-title">
                <div class="section-icon">
                    <i class="fas fa-star"></i>
                </div>
                <h2>Dati Recensione</h2>
            </div>

            <div class="row g-3">
                <div class="col-md-4 position-relative">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-hashtag"></i></span>
                        <input name="idReview" class="form-control"
                               value="<%= request.getAttribute("reviewId") != null ? request.getAttribute("reviewId") : "" %>" readonly>
                        <div class="readonly-indicator">Read Only</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-award"></i></span>
                        <input name="criticScore" class="form-control" placeholder="Punteggio Critici (0-100)"
                               value="<%= request.getAttribute("criticScore") != null ? request.getAttribute("criticScore") : "" %>" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-user-tie"></i></span>
                        <input name="criticCount" class="form-control" placeholder="N. Critici"
                               value="<%= request.getAttribute("criticCount") != null ? request.getAttribute("criticCount") : "" %>" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-thumbs-up"></i></span>
                        <input name="userScore" class="form-control" placeholder="Punteggio Utenti (0-10)"
                               value="<%= request.getAttribute("userScore") != null ? request.getAttribute("userScore") : "" %>" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-users"></i></span>
                        <input name="userCount" class="form-control" placeholder="N. Utenti"
                               value="<%= request.getAttribute("userCount") != null ? request.getAttribute("userCount") : "" %>" required>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sezione Vendite -->
        <div id="sales-section" class="section fade-in-delay-3">
            <div class="section-title">
                <div class="section-icon">
                    <i class="fas fa-chart-line"></i>
                </div>
                <h2>Dati Vendite</h2>
            </div>

            <div class="row g-3">
                <div class="col-md-4 position-relative">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-hashtag"></i></span>
                        <input name="idSales" class="form-control" value="${sales.idSales}" readonly>
                        <div class="readonly-indicator">Read Only</div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-dollar-sign"></i></span>
                        <input name="naSales" class="form-control" placeholder="Vendite NA (milioni)" value="${sales.naSales}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-euro-sign"></i></span>
                        <input name="euSales" class="form-control" placeholder="Vendite EU (milioni)" value="${sales.euSales}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-yen-sign"></i></span>
                        <input name="jpSales" class="form-control" placeholder="Vendite JP (milioni)" value="${sales.jpSales}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-globe-asia"></i></span>
                        <input name="otherSales" class="form-control" placeholder="Altre Vendite (milioni)" value="${sales.otherSales}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="input-group">
                        <span class="input-group-text"><i class="fas fa-globe"></i></span>
                        <input name="globalSales" class="form-control" placeholder="Totale Globale (milioni)" value="${sales.globalSales}" required>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bottoni Azione -->
        <div class="text-center fade-in-delay-3 mb-5">
            <button type="submit" class="btn btn-update">
                <i class="fas fa-save me-2"></i>Aggiorna Gioco
            </button>
            <button type="button" class="btn btn-delete ms-2" onclick="confirmDelete()">
                <i class="fas fa-trash me-2"></i>Elimina Gioco
            </button>
            <a href="hello-servlet" class="btn btn-outline-light ms-2">
                <i class="fas fa-arrow-left me-2"></i>Torna alla Home
            </a>
        </div>

    </form>
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

        // Form validation
        const form = document.querySelector('form');
        form.addEventListener('submit', function(event) {
            // Basic validation
            const inputs = form.querySelectorAll('input[required]:not([readonly])');
            let valid = true;

            inputs.forEach(input => {
                if (!input.value.trim()) {
                    input.classList.add('is-invalid');
                    valid = false;
                } else {
                    input.classList.remove('is-invalid');
                }
            });

            if (!valid) {
                event.preventDefault();
                alert('Per favore compila tutti i campi richiesti.');
            }
        });

        // Clear validation on input
        const inputs = form.querySelectorAll('input:not([readonly])');
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                if (input.value.trim()) {
                    input.classList.remove('is-invalid');
                }
            });
        });

        // Highlight changes
        const editableInputs = document.querySelectorAll('input:not([readonly])');
        editableInputs.forEach(input => {
            const originalValue = input.value;
            input.addEventListener('input', function() {
                if (input.value !== originalValue) {
                    input.style.borderColor = '#2196f3';
                    input.style.boxShadow = '0 0 0 0.25rem rgba(33, 150, 243, 0.25)';
                } else {
                    input.style.borderColor = '';
                    input.style.boxShadow = '';
                }
            });
        });
    });

    // Funzione per confermare l'eliminazione
    function confirmDelete() {
        if (confirm('Sei sicuro di voler eliminare questo gioco? Questa azione non può essere annullata.')) {
            // Reindirizza alla servlet di eliminazione
            const gameId = document.querySelector('input[name="idGame"]').value;
            window.location.href = 'DeleteGame?id=' + gameId;
        }
    }

    // Funzione per evidenziare i cambiamenti non salvati
    let hasUnsavedChanges = false;

    document.querySelectorAll('input:not([readonly])').forEach(input => {
        input.addEventListener('input', function() {
            hasUnsavedChanges = true;
        });
    });

    // Avviso per modifiche non salvate
    window.addEventListener('beforeunload', function(e) {
        if (hasUnsavedChanges) {
            const confirmationMessage = 'Hai modifiche non salvate. Sei sicuro di voler uscire?';
            e.returnValue = confirmationMessage;
            return confirmationMessage;
        }
    });

    // Reset flag quando il form viene inviato
    document.querySelector('form').addEventListener('submit', function() {
        hasUnsavedChanges = false;
    });
</script>
</body>
</html>