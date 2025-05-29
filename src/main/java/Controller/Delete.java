package Controller;

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

@WebServlet("/Delete")
public class Delete extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idGameToDelete = request.getParameter("idGame");
        String redirectURL = request.getContextPath() + "/hello-servlet";
        System.out.println("Richiesta DELETE (GET) ricevuta per ID: " + idGameToDelete);

        if (idGameToDelete != null && !idGameToDelete.trim().isEmpty()) {
            GameDAO gameDAO = new GameDAO();
            ReviewDAO reviewDAO = new ReviewDAO();
            SalesDAO salesDAO = new SalesDAO();
            boolean success = false;
            String operationMessage = null;
            try {
                System.out.println("Tentativo di eliminare recensioni per gameId: " + idGameToDelete);
                reviewDAO.deleteByGameId(idGameToDelete);
                System.out.println("Recensioni eliminate per gameId: " + idGameToDelete);

                System.out.println("Tentativo di eliminare vendite per gameId: " + idGameToDelete);
                salesDAO.deleteByGameId(idGameToDelete);
                System.out.println("Vendite eliminate per gameId: " + idGameToDelete);

                System.out.println("Tentativo di eliminare gioco con gameId: " + idGameToDelete);
                gameDAO.deleteGame(idGameToDelete); // Questa chiamata ora dovrebbe funzionare
                System.out.println("Gioco eliminato con gameId: " + idGameToDelete);

                success = true;
                operationMessage = "Gioco eliminato con successo!";

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                operationMessage = "ID del gioco fornito non è valido: " + e.getMessage();
                success = false;
            } catch (RuntimeException e) {
                e.printStackTrace();
                operationMessage = "Errore durante l'eliminazione: " + e.getMessage();
                success = false;
            } catch (Exception e) {
                e.printStackTrace();
                operationMessage = "Errore generico durante l'eliminazione del gioco: " + e.getMessage();
                success = false;
            }


            if (success) {
                request.getSession().setAttribute("successMessage", operationMessage);
            } else {
                request.getSession().setAttribute("errorMessage", operationMessage);
            }

        } else {
            System.out.println("ID del gioco non fornito per l'eliminazione.");
            request.getSession().setAttribute("errorMessage", "ID del gioco non fornito o non valido per l'eliminazione.");
        }

        System.out.println("INFO: Tentativo finale di redirect a: " + redirectURL);
        if (!response.isCommitted()) {
            response.sendRedirect(redirectURL);
        } else {
            System.err.println("ERRORE CRITICO NEL SERVLET: La risposta è già commessa prima del redirect finale. Controllare i log per eccezioni o output prematuro.");

        }
    }
}
