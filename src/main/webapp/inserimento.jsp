<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inserisci Gioco Completo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding: 40px;
            background-color: #f8f9fa;
        }
        h1 {
            margin-bottom: 30px;
        }
        .form-section {
            margin-bottom: 40px;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-section h2 {
            font-size: 20px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>➕ Inserisci Nuovo Gioco, Recensione e Vendita</h1>

    <form action="insert-all" method="post">

        <!-- Sezione Gioco -->
        <div class="form-section">
            <h2>🎮 Dati Gioco</h2>
            <div class="row g-3">
                <div class="col-md-4"><input name="idGame" class="form-control" placeholder="ID Gioco" required></div>
                <div class="col-md-4"><input name="name" class="form-control" placeholder="Nome" required></div>
                <div class="col-md-4"><input name="platform" class="form-control" placeholder="Piattaforma"></div>
                <div class="col-md-3"><input name="year" class="form-control" placeholder="Anno di Rilascio"></div>
                <div class="col-md-3"><input name="genre" class="form-control" placeholder="Genere"></div>
                <div class="col-md-3"><input name="publisher" class="form-control" placeholder="Publisher"></div>
                <div class="col-md-3"><input name="developer" class="form-control" placeholder="Developer"></div>
                <div class="col-md-3"><input name="rating" class="form-control" placeholder="Rating ESRB"></div>
            </div>
        </div>

        <!-- Sezione Recensione -->
        <div class="form-section">
            <h2>📝 Dati Recensione</h2>
            <div class="row g-3">
                <div class="col-md-3"><input name="idReview" class="form-control" placeholder="ID Recensione" required></div>
                <div class="col-md-3"><input name="criticScore" class="form-control" placeholder="Punteggio Critici (0-100)"></div>
                <div class="col-md-3"><input name="criticCount" class="form-control" placeholder="N. Critici"></div>
                <div class="col-md-3"><input name="userScore" class="form-control" placeholder="Punteggio Utenti (0-10)"></div>
                <div class="col-md-3"><input name="userCount" class="form-control" placeholder="N. Utenti"></div>
            </div>
        </div>

        <!-- Sezione Vendite -->
        <div class="form-section">
            <h2>📊 Dati Vendite</h2>
            <div class="row g-3">
                <div class="col-md-3"><input name="idSales" class="form-control" placeholder="ID Vendita" required></div>
                <div class="col-md-3"><input name="naSales" class="form-control" placeholder="Vendite NA (milioni)"></div>
                <div class="col-md-3"><input name="euSales" class="form-control" placeholder="Vendite EU (milioni)"></div>
                <div class="col-md-3"><input name="jpSales" class="form-control" placeholder="Vendite JP (milioni)"></div>
                <div class="col-md-3"><input name="otherSales" class="form-control" placeholder="Altre Vendite (milioni)"></div>
                <div class="col-md-3"><input name="globalSales" class="form-control" placeholder="Totale Globale (milioni)"></div>
            </div>
        </div>

        <!-- Bottone Submit -->
        <div class="text-center">
            <button type="submit" class="btn btn-primary btn-lg">Salva Tutto</button>
        </div>

    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
