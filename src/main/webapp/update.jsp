<%-- PASSO 1: Importa la libreria JSTL (fondamentale!) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aggiorna Gioco e Recensione | GameReviewHub</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* Il tuo CSS è perfetto, lo lascio invariato. */
        :root{--primary-color:#7e57c2;--secondary-color:#ff9800;--accent-color:#00bcd4;--dark-color:#263238;--light-color:#f5f5f5;--game-color:#673ab7;--review-color:#4caf50}body{font-family:'Segoe UI',Tahoma,Geneva,Verdana,sans-serif;background:linear-gradient(135deg,#121212 0,#2d2d2d 100%);color:#fff;padding:0;margin:0;min-height:100vh}.navbar{background-color:rgba(18,18,18,.9);backdrop-filter:blur(10px);padding:.8rem 1rem;box-shadow:0 4px 20px rgba(0,0,0,.3);position:sticky;top:0;z-index:1000}.hero-section{background:linear-gradient(135deg,#1a237e 0,#311b92 50%,#4a148c 100%);position:relative;padding:60px 0;margin-bottom:30px;text-align:center}.hero-section::before{content:'';position:absolute;top:0;left:0;width:100%;height:100%;background:linear-gradient(135deg,rgba(126,87,194,.8) 0,rgba(0,188,212,.8) 100%)}.hero-content{position:relative;z-index:2}.hero-title{font-size:2.5rem;font-weight:800;text-transform:uppercase;text-shadow:2px 2px 8px rgba(0,0,0,.6);margin-bottom:10px;position:relative;display:inline-block}.hero-title::after{content:'';position:absolute;bottom:-10px;left:50%;transform:translateX(-50%);width:100px;height:4px;background:var(--secondary-color);border-radius:2px}.form-container{background-color:rgba(38,50,56,.7);border-radius:15px;padding:40px;box-shadow:0 10px 30px rgba(0,0,0,.2);position:relative;overflow:hidden;margin-bottom:30px}.form-container.game-form::before{content:'';position:absolute;top:0;left:0;height:4px;width:100%;background:linear-gradient(90deg,var(--game-color),#9c27b0)}.form-container.review-form::before{content:'';position:absolute;top:0;left:0;height:4px;width:100%;background:linear-gradient(90deg,var(--review-color),#00bcd4)}.form-title{display:flex;align-items:center;margin-bottom:30px;font-weight:700;font-size:1.5rem}.form-icon{font-size:24px;margin-right:15px;width:48px;height:48px;display:flex;align-items:center;justify-content:center;border-radius:50%;color:#fff}.game-icon{background-color:var(--game-color)}.review-icon{background-color:var(--review-color)}.form-floating{margin-bottom:20px}.form-control{background-color:rgba(38,50,56,.5);border:1px solid rgba(255,255,255,.2);color:#fff;border-radius:10px;transition:all .3s ease}.form-control:focus{background-color:rgba(38,50,56,.8);border-color:var(--primary-color);box-shadow:0 0 0 .25rem rgba(126,87,194,.25);color:#fff}.form-control::placeholder{color:rgba(255,255,255,.5)}.form-floating>label{color:rgba(255,255,255,.7)}.form-floating>.form-control:focus~label,.form-floating>.form-control:not(:placeholder-shown)~label{color:var(--primary-color)}.btn-update{background:linear-gradient(45deg,var(--primary-color),#9c27b0);color:#fff;border:none;padding:12px 30px;border-radius:30px;font-weight:600;text-transform:uppercase;letter-spacing:1px;transition:all .3s ease;box-shadow:0 4px 15px rgba(126,87,194,.4);width:100%;margin-top:20px}.btn-update-review{background:linear-gradient(45deg,var(--review-color),#00acc1)}.btn-update-review:hover{background:linear-gradient(45deg,#00acc1,var(--review-color))}.btn-update:hover{background:linear-gradient(45deg,#9c27b0,var(--primary-color));transform:translateY(-3px);box-shadow:0 6px 20px rgba(126,87,194,.6);color:#fff}.btn-back{background-color:transparent;color:rgba(255,255,255,.7);border:1px solid rgba(255,255,255,.3);padding:10px 25px;border-radius:25px;text-decoration:none;transition:all .3s ease;display:inline-flex;align-items:center;margin-bottom:20px}.btn-back:hover{background-color:rgba(255,255,255,.1);color:#fff;border-color:var(--primary-color);text-decoration:none}.alert-custom{border-radius:10px;border:none;box-shadow:0 4px 15px rgba(0,0,0,.1)}.alert-danger{background-color:rgba(244,67,54,.1);color:#f44336;border-left:4px solid #f44336}.alert-success{background-color:rgba(76,175,80,.1);color:#4caf50;border-left:4px solid #4caf50}.required-field::after{content:' *';color:#f44336}.info-box{background-color:rgba(38,50,56,.5);border-radius:15px;padding:20px;border-left:4px solid var(--accent-color)}@media (max-width:768px){.hero-title{font-size:2rem}.form-container{padding:20px}}@keyframes fadeIn{from{opacity:0;transform:translateY(20px)}to{opacity:1;transform:translateY(0)}}.fade-in{animation:fadeIn .5s ease forwards}
    </style>
</head>
<body>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <a class="navbar-brand fw-bold fs-4" href="hello-servlet">
            <i class="fas fa-gamepad me-2"></i>GameReviewHub
        </a>
    </div>
</nav>

<!-- Hero Section -->
<div class="hero-section">
    <div class="hero-content">
        <h1 class="hero-title">Area Modifica</h1>
        <p class="lead">Modifica le informazioni del gioco e della sua recensione</p>
    </div>
</div>

<!-- Main Content -->
<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <!-- Back Button -->
            <a href="hello-servlet" class="btn-back">
                <i class="fas fa-arrow-left me-2"></i>Torna alla Lista
            </a>

            <!-- GESTIONE MESSAGGI (Errori e Successo) -->
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-custom fade-in">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    <c:out value="${errorMessage}"/>
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.flashSuccessMessage}">
                <div class="alert alert-success alert-custom fade-in">
                    <i class="fas fa-check-circle me-2"></i>
                    <c:out value="${sessionScope.flashSuccessMessage}"/>
                </div>
                <c:remove var="flashSuccessMessage" scope="session"/>
            </c:if>

            <%-- PASSO 2: Usa c:choose per mostrare solo un form alla volta --%>
            <c:choose>
                <%-- CASO A: L'utente vuole modificare la RECENSIONE --%>
                <c:when test="${currentView eq 'review'}">
                    <c:if test="${not empty game}">
                        <div class="form-container review-form fade-in">
                            <div class="form-title">
                                <div class="form-icon review-icon"><i class="fas fa-star-half-alt"></i></div>
                                    <%-- PASSO 3: Usa la sintassi JSTL corretta (es. ${game.name}) --%>
                                Aggiorna Recensione per: ${game.getName()}
                            </div>
                            <c:if test="${not empty review}">
                                <form action="Update" method="post">
                                    <input type="hidden" name="action" value="updateReview">
                                    <input type="hidden" name="idGame" value="${game.getIdGame()}">
                                    <input type="hidden" name="idReview" value="${review.getIdReview()}">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <input type="text" class="form-control" id="critic_score" name="critic_score" value="${review.getCriticScore()}">
                                                <label for="critic_score">Punteggio Critica</label>
                                            </div>
                                            <div class="form-floating">
                                                <input type="text" class="form-control" id="critic_count" name="critic_count" value="${review.getCriticCount()}">
                                                <label for="critic_count">Conteggio Critica</label>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="form-floating">
                                                <input type="text" class="form-control" id="user_score" name="user_score" value="${review.getUserScore()}">
                                                <label for="user_score">Punteggio Utenti</label>
                                            </div>
                                            <div class="form-floating">
                                                <input type="text" class="form-control" id="user_count" name="user_count" value="${review.getUserCount()}">
                                                <label for="user_count">Conteggio Utenti</label>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-update btn-update-review"><i class="fas fa-save me-2"></i>Aggiorna Recensione</button>
                                </form>
                            </c:if>
                            <c:if test="${empty review}">
                                <div class="info-box"><p class="mb-0"><i class="fas fa-info-circle me-2"></i>Non è ancora presente una recensione per questo gioco.</p></div>
                            </c:if>
                        </div>
                    </c:if>
                </c:when>

                <%-- CASO B: Altrimenti, mostra il form di default (quello del GIOCO) --%>
                <c:otherwise>
                    <c:if test="${not empty game}">
                        <div class="form-container game-form fade-in">
                            <div class="form-title">
                                <div class="form-icon game-icon"><i class="fas fa-gamepad"></i></div>
                                Aggiorna Informazioni Gioco
                            </div>
                            <form action="Update" method="post">
                                <input type="hidden" name="action" value="updateGame">
                                    <%-- PASSO 3: Usa la sintassi JSTL corretta (es. ${game.idGame}) --%>
                                <input type="hidden" name="idGame" value="${game.getIdGame()}">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="name" name="name" placeholder="Nome" value="${game.getName()}" required>
                                            <label for="name" class="required-field">Nome Gioco</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="platform" name="platform" placeholder="Piattaforma" value="${game.getPlatform()}">
                                            <label for="platform">Piattaforma</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="number" class="form-control" id="year_of_release" name="year_of_release" placeholder="Anno" min="1970" max="2030" value="${game.getYearOfRelease()}">
                                            <label for="year_of_release">Anno Rilascio</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="genre" name="genre" placeholder="Genere" value="${game.getGenre()}">
                                            <label for="genre">Genere</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="publisher" name="publisher" placeholder="Publisher" value="${game.getPublisher()}">
                                            <label for="publisher">Publisher</label>
                                        </div>
                                        <div class="form-floating">
                                            <input type="text" class="form-control" id="developer" name="developer" placeholder="Developer" value="${game.getDeveloper()}">
                                            <label for="developer">Developer</label>
                                        </div>
                                        <div class="form-floating">
                                            <select class="form-control" id="rating" name="rating">
                                                <option value="" ${game.getRating() == '' ? 'selected' : ''}>Seleziona Rating</option>
                                                <option value="E" ${game.getRating() == 'E' ? 'selected' : ''}>E - Everyone</option>
                                                <option value="E10+" ${game.getRating() == 'E10+' ? 'selected' : ''}>E10+ - Everyone 10+</option>
                                                <option value="T" ${game.getRating() == 'T' ? 'selected' : ''}>T - Teen</option>
                                                <option value="M" ${game.getRating() == 'M' ? 'selected' : ''}>M - Mature</option>
                                                <option value="AO" ${game.getRating() == 'AO' ? 'selected' : ''}>AO - Adults Only</option>
                                                <option value="RP" ${game.getRating() == 'RP' ? 'selected' : ''}>RP - Rating Pending</option>
                                            </select>
                                            <label for="rating">Rating</label>
                                        </div>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-update"><i class="fas fa-save me-2"></i>Aggiorna Gioco</button>
                            </form>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <%-- Messaggio se il gioco non è stato trovato --%>
            <c:if test="${empty game and not empty requestScope.errorMessage}">
                <div class="info-box text-center">
                    <p class="mb-0">${requestScope.errorMessage}</p>
                </div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const elements = document.querySelectorAll('.fade-in');
        elements.forEach((el, index) => {
            el.style.animationDelay = (index * 0.1) + 's';
        });
    });
</script>
</body>
</html>