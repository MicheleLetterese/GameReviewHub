package Controller;

import Model.Game;
import Model.GameDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.bson.types.ObjectId; // Se usi ObjectId direttamente nel modello Game

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/Update")
public class Update extends HttpServlet {
    private GameDAO gameDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        gameDAO = new GameDAO();
        System.out.println("[INIT] GameDAO inizializzato");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");


        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        String idGameToEdit = request.getParameter("idGame");
        System.out.println("[GET] id_game ricevuto: " + idGameToEdit);

        if (idGameToEdit == null || idGameToEdit.trim().isEmpty()) {
            System.out.println("[GET] ID gioco non specificato");
            request.setAttribute("errorMessage", "ID del gioco non specificato per la modifica.");

        } else {
            try {
                Game game = gameDAO.getGameById(idGameToEdit);
                if (game != null) {
                    System.out.println("[GET] Gioco trovato: " + game.getName());

                    request.setAttribute("game", game);

                } else {
                    request.setAttribute("errorMessage", "Gioco con ID " + idGameToEdit + " non trovato.");
                    System.out.println("[GET] Gioco con ID " + idGameToEdit + " non trovato");

                    request.setAttribute("idGame", idGameToEdit);
                }
            } catch (Exception e) {
                System.out.println("[GET] Errore nel recupero del gioco: " + e.getMessage());

                e.printStackTrace();
                request.setAttribute("errorMessage", "Errore durante il caricamento dei dati del gioco: " + e.getMessage());
                request.setAttribute("idGame", idGameToEdit);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update.jsp");
        dispatcher.forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");


        String idGame = request.getParameter("idGame");

        System.out.println("[POST] id_game ricevuto: " + idGame);

        String name = request.getParameter("name");
        String platform = request.getParameter("platform");
        String yearOfReleaseStr = request.getParameter("year_of_release");
        String genre = request.getParameter("genre");
        String publisher = request.getParameter("publisher");
        String developer = request.getParameter("developer");
        String rating = request.getParameter("rating");

        if (idGame == null || idGame.trim().isEmpty()) {
            System.out.println("[POST] L'ID del gioco è mancante");

            request.setAttribute("errorMessage", "L'ID del gioco è obbligatorio per l'aggiornamento.");

            request.getRequestDispatcher("/update.jsp").forward(request, response); // Assumendo che update.jsp sia il tuo form
            return;
        }

        Integer yearOfRelease = null;
        if (yearOfReleaseStr != null && !yearOfReleaseStr.trim().isEmpty()) {
            try {
                yearOfRelease = Integer.parseInt(yearOfReleaseStr);
            } catch (NumberFormatException e) {
                System.out.println("[POST] Anno di rilascio non valido: " + yearOfReleaseStr);

                request.setAttribute("errorMessage", "Anno di rilascio non valido.");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
                return;
            }
        } else {
            //caso in cui l'anno non è fornito, se permesso

        }


        Game gameToUpdate = new Game();
        gameToUpdate.setIdGame(idGame);
        gameToUpdate.setName(name);
        gameToUpdate.setPlatform(platform);
        gameToUpdate.setYearOfRelease(yearOfRelease); // Può essere null
        gameToUpdate.setGenre(genre);
        gameToUpdate.setPublisher(publisher);
        gameToUpdate.setDeveloper(developer);
        gameToUpdate.setRating(rating);

        if (idGame == null || idGame.trim().isEmpty()) {
            System.out.println("[POST /Update] L'ID del gioco è mancante");
            request.setAttribute("errorMessage", "L'ID del gioco è obbligatorio per l'aggiornamento.");
            // Non c'è un gioco da ripopolare se l'ID è mancante, quindi il form sarà vuoto
            request.getRequestDispatcher("/update.jsp").forward(request, response);
            return;
        }

        try {
            gameDAO.updateGame(gameToUpdate); // updateGame ora gestisce la conversione ID internamente
            System.out.println("[POST /Update] Gioco con ID: " + idGame + " aggiornato con successo nel DB.");

            HttpSession session = request.getSession();
            session.setAttribute("flashSuccessMessage", "Gioco '" + gameToUpdate.getName() + "' aggiornato con successo!");

            response.sendRedirect(request.getContextPath() + "/hello-servlet");

        } catch (IllegalArgumentException iae) {
            System.out.println("[POST /Update] Errore di validazione durante l'aggiornamento: " + iae.getMessage());
            iae.printStackTrace();
            request.setAttribute("errorMessage", "Errore di validazione: " + iae.getMessage());
            request.setAttribute("game", gameToUpdate); // Ripassa i dati del form per la correzione
            if (yearOfReleaseStr != null && !yearOfReleaseStr.isEmpty() && yearOfRelease == null) {
                request.setAttribute("yearOfReleaseStringError", yearOfReleaseStr); // Se c'era un errore di parsing sull'anno
            }
            request.getRequestDispatcher("/hello-servlet").forward(request, response);
        } catch (RuntimeException e) { // Per altri errori imprevisti dal DAO o DB
            System.out.println("[POST /Update] Errore runtime durante l'aggiornamento del gioco: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore durante l'aggiornamento del gioco: " + e.getMessage());
            request.setAttribute("game", gameToUpdate); // Ripassa i dati del form
            if (yearOfReleaseStr != null && !yearOfReleaseStr.isEmpty() && yearOfRelease == null) {
                request.setAttribute("yearOfReleaseStringError", yearOfReleaseStr);
            }
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        }
    }
}

        /*
        try {
            gameDAO.updateGame(gameToUpdate);
            System.out.println("Gioco con ID: " + idGame + " aggiornato con successo.");
            request.setAttribute("successMessage", "Gioco aggiornato con successo!");
            request.getRequestDispatcher("/update.jsp").forward(request, response); // Pagina per mostrare esito

        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("[POST] Errore durante l'aggiornamento del gioco: " + e.getMessage());

            request.setAttribute("errorMessage", "Errore durante l'aggiornamento del gioco: " + e.getMessage());
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        }
    }


}*/