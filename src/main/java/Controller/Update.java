package Controller;

import Model.Game;
import Model.GameDAO;
import Model.Review;
import Model.ReviewDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



@WebServlet("/Update")
public class Update extends HttpServlet {
    private GameDAO gameDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        gameDAO = new GameDAO();
        this.reviewDAO = new ReviewDAO();
        System.out.println("[INIT] GameDAO e ReviewDAO inizializzati");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);


        String viewMode = request.getParameter("view");

        //  Se 'view' è 'review', form recensione.
        if ("review".equals(viewMode)) {
            request.setAttribute("currentView", "review");
            System.out.println("[GET] Richiesta vista specifica per la recensione.");
        } else {
            request.setAttribute("currentView", "game"); // Default view
            System.out.println("[GET] Richiesta vista standard (gioco).");
        }



        String idGameToEdit = request.getParameter("idGame");
        System.out.println("[GET] id_game ricevuto: " + idGameToEdit);

        if (idGameToEdit == null || idGameToEdit.trim().isEmpty()) {
            System.out.println("[GET] ID gioco non specificato");
            request.setAttribute("errorMessage", "ID del gioco non specificato per la modifica.");
        } else {
            try {
                Game game = gameDAO.getGameById(idGameToEdit);
                Review review = reviewDAO.getReviewByGameId(idGameToEdit);

                if (game != null) {
                    System.out.println("[GET] Gioco trovato: " + game.getName());
                    request.setAttribute("game", game);
                    if (review != null) {
                        System.out.println("[GET] Recensione trovata");
                        request.setAttribute("review", review);
                    } else {
                        System.out.println("[GET] ATTENZIONE: Nessuna review trovata per il gioco ID: " + idGameToEdit);
                        request.setAttribute("review", null); // Imposta a null se non trovata
                    }
                } else {
                    request.setAttribute("errorMessage", "Gioco con ID " + idGameToEdit + " non trovato.");
                    System.out.println("[GET] Gioco con ID " + idGameToEdit + " non trovato");
                }
            } catch (Exception e) {
                System.out.println("[GET] Errore nel recupero dei dati: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("errorMessage", "Errore durante il caricamento dei dati: " + e.getMessage());
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");


        String action = request.getParameter("action");
        System.out.println("[POST] Azione richiesta: " + action);

        if ("updateGame".equals(action)) {
            handleUpdateGame(request, response);
        } else if ("updateReview".equals(action)) {
            handleUpdateReview(request, response);
        } else {
            // Caso di errore: nessuna azione specificata o azione non valida
            System.out.println("[POST] Azione non valida o non specificata.");
            request.setAttribute("errorMessage", "Azione richiesta non valida.");
            // Ricarica la pagina di modifica con un messaggio di errore
            doGet(request, response);
        }
    }

    private void handleUpdateGame(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("[POST-Game] Inizio aggiornamento gioco.");
        String idGame = request.getParameter("idGame");
        String name = request.getParameter("name");
        String platform = request.getParameter("platform");
        String yearOfReleaseStr = request.getParameter("year_of_release");
        String genre = request.getParameter("genre");
        String publisher = request.getParameter("publisher");
        String developer = request.getParameter("developer");
        String rating = request.getParameter("rating");

        if (idGame == null || idGame.trim().isEmpty()) {
            request.setAttribute("errorMessage", "L'ID del gioco è obbligatorio per l'aggiornamento.");
            doGet(request, response);
            return;
        }

        Integer yearOfRelease = null;
        if (yearOfReleaseStr != null && !yearOfReleaseStr.trim().isEmpty()) {
            try {
                yearOfRelease = Integer.parseInt(yearOfReleaseStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Anno di rilascio non valido.");
                doGet(request, response);
                return;
            }
        }

        Game gameToUpdate = new Game();
        gameToUpdate.setIdGame(idGame);
        gameToUpdate.setName(name);
        gameToUpdate.setPlatform(platform);
        gameToUpdate.setYearOfRelease(yearOfRelease);
        gameToUpdate.setGenre(genre);
        gameToUpdate.setPublisher(publisher);
        gameToUpdate.setDeveloper(developer);
        gameToUpdate.setRating(rating);

        try {
            gameDAO.updateGame(gameToUpdate);
            System.out.println("[POST-Game] Gioco con ID: " + idGame + " aggiornato con successo.");
            HttpSession session = request.getSession();
            session.setAttribute("flashSuccessMessage", "Gioco '" + gameToUpdate.getName() + "' aggiornato con successo!");
            response.sendRedirect(request.getContextPath() + "/hello-servlet");
        } catch (Exception e) {
            System.out.println("[POST-Game] Errore durante l'aggiornamento del gioco: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore durante l'aggiornamento del gioco: " + e.getMessage());
            request.setAttribute("game", gameToUpdate); // Ripassa i dati del form
            doGet(request, response); // Usa doGet per ricaricare tutto
        }
    }

    private void handleUpdateReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("[POST-Review] Inizio aggiornamento recensione.");
        String idReview = request.getParameter("idReview");
        String idGame = request.getParameter("idGame");

        //recensione
        String criticScoreStr = request.getParameter("critic_score");
        String criticCountStr = request.getParameter("critic_count");
        String userScoreStr = request.getParameter("user_score");
        String userCountStr = request.getParameter("user_count");

        if (idReview == null || idReview.trim().isEmpty() || idGame == null || idGame.trim().isEmpty()) {
            request.setAttribute("errorMessage", "ID Recensione o ID Gioco mancanti. Impossibile aggiornare.");
            doGet(request, response);
            return;
        }

        Review reviewToUpdate = new Review();
        reviewToUpdate.setIdReview(idReview);
        reviewToUpdate.setIdGame(idGame);

        try {

            reviewToUpdate.setCriticScore(Double.parseDouble(criticScoreStr));
            reviewToUpdate.setCriticCount(Double.parseDouble(criticCountStr));
            reviewToUpdate.setUserScore(Double.parseDouble(userScoreStr));
            reviewToUpdate.setUserCount(Double.parseDouble(userCountStr));


            boolean success = reviewDAO.updateReview(reviewToUpdate);

            if (success) {
                System.out.println("[POST-Review] Recensione con ID: " + idReview + " aggiornata con successo.");
                HttpSession session = request.getSession();
                session.setAttribute("flashSuccessMessage", "Recensione per il gioco aggiornata con successo!");
                // Reindirizza alla pagina di modifica per vedere i risultati, oppure alla lista principale
                response.sendRedirect(request.getContextPath() + "/Update?idGame=" + idGame);
            } else {
                System.out.println("[POST-Review] L'aggiornamento della recensione non ha modificato alcun documento.");
                request.setAttribute("errorMessage", "Nessuna modifica effettuata. I dati potrebbero essere già aggiornati.");
                doGet(request, response);
            }

        } catch (NumberFormatException e) {
            System.out.println("[POST-Review] Errore di formato numerico: " + e.getMessage());
            request.setAttribute("errorMessage", "Errore: i punteggi e i conteggi devono essere numeri validi.");
            request.setAttribute("review", reviewToUpdate);
            doGet(request, response);
        } catch (Exception e) {
            System.out.println("[POST-Review] Errore durante l'aggiornamento della recensione: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore critico durante l'aggiornamento della recensione: " + e.getMessage());
            request.setAttribute("review", reviewToUpdate);
            doGet(request, response);
        }
    }
}