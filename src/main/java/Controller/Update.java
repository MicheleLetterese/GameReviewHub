package Controller;

import Model.Game;
import Model.GameDAO;
import Model.ReviewDAO;
import Model.SalesDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/Update")
public class Update extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("update.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String gameId = request.getParameter("id_game");

        if (gameId != null && !gameId.trim().isEmpty()) {
        try {
            // Recupera tutti i giochi e trova quello con l'ID corrispondente
            // (Dato che non esiste un metodo getGameById nel tuo GameDAO)
            GameDAO gameDAO = new GameDAO();
            ArrayList<Game> allGames = gameDAO.getAllGames();

            Game gameToEdit = null;
            for (Game game : allGames) {
                if (game.getIdGame().equals(gameId)) {
                    gameToEdit = game;
                    break;
                }
            }

            if (gameToEdit != null) {
                // Imposta l'oggetto Game come attributo della request
                request.setAttribute("game", gameToEdit);

                // Inoltra alla pagina di modifica
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit-game-form.jsp");
                dispatcher.forward(request, response);
            } else {

                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Gioco non trovato con ID: " + gameId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante il recupero del gioco: " + e.getMessage());
        }
    } else {
        // ID non fornito
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID del gioco non fornito");
    }


    String idGame = request.getParameter("idGame");
    String name = request.getParameter("name");
    String platform = request.getParameter("platform");
    String yearOfReleaseStr = request.getParameter("yearOfRelease");
    String genre = request.getParameter("genre");
    String publisher = request.getParameter("publisher");
    String developer = request.getParameter("developer");
    String rating = request.getParameter("rating");

    if (idGame == null || idGame.trim().isEmpty() ||
            name == null || name.trim().isEmpty()) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID gioco e nome sono campi obbligatori");
        return;
    }

    try {
        int yearOfRelease = 0;
        if (yearOfReleaseStr != null && !yearOfReleaseStr.trim().isEmpty()) {
            try {
                yearOfRelease = Integer.parseInt(yearOfReleaseStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        Game updatedGame = new Game(
                idGame,
                name,
                platform,
                yearOfRelease,
                genre,
                publisher,
                developer,
                rating
        );

        GameDAO gameDAO = new GameDAO();
        boolean success = gameDAO.updateGame(updatedGame);

        if (success) {
            response.sendRedirect("GameList");
        } else {
            request.setAttribute("errorMessage", "Aggiornamento non riuscito: gioco non trovato con ID " + idGame);
            request.getRequestDispatcher("update.jsp").forward(request, response);
        }

    } catch (Exception e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'aggiornamento del gioco: " + e.getMessage());
    }
}

}*/





