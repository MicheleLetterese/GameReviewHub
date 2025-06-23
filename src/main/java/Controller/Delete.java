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
            processRequest(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            processRequest(request, response); // Process POST requests the same way as GET
        }

        private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String actionType = request.getParameter("action_type");
            String redirectURL = request.getContextPath() + "/hello-servlet";
            boolean success = false;
            String operationMessage = null;

            System.out.println("Richiesta DELETE ricevuta " + actionType);

            if (actionType == null || actionType.trim().isEmpty()) {
                System.out.println("Action type non fornito.");
                request.getSession().setAttribute("errorMessage", "noeliminazione.");
                if (!response.isCommitted()) {
                    response.sendRedirect(redirectURL);
                }
                return;
            }

            ReviewDAO reviewDAO = new ReviewDAO(); // Common DAO, might be needed for both
            SalesDAO salesDAO = new SalesDAO();

            if ("game".equalsIgnoreCase(actionType)) {
                String idGameToDelete = request.getParameter("idGame");
                System.out.println("Richiesta eliminazione GIOCO per ID: " + idGameToDelete);

                if (idGameToDelete != null && !idGameToDelete.trim().isEmpty()) {
                    GameDAO gameDAO = new GameDAO();

                    try {
                        System.out.println("Tentativo di eliminare recensioni per gameId: " + idGameToDelete);
                        reviewDAO.deleteByGameId(idGameToDelete);
                        System.out.println("Recensioni eliminate per gameId: " + idGameToDelete);

                        System.out.println("Tentativo di eliminare vendite per gameId: " + idGameToDelete);
                        salesDAO.deleteByGameId(idGameToDelete);
                        System.out.println("Vendite eliminate per gameId: " + idGameToDelete);

                        System.out.println("Tentativo di eliminare gioco con gameId: " + idGameToDelete);
                        gameDAO.deleteGame(idGameToDelete);
                        System.out.println("Gioco eliminato con gameId: " + idGameToDelete);

                        success = true;
                        operationMessage = "Gioco e dati associati eliminati con successo!";
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        operationMessage = "ID del gioco fornito non è valido: " + e.getMessage();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        operationMessage = "Errore durante l'eliminazione del gioco: " + e.getMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                        operationMessage = "Errore generico durante l'eliminazione del gioco: " + e.getMessage();
                    }
                } else {
                    System.out.println("ID del gioco non fornito per l'eliminazione.");
                    operationMessage = "ID del gioco non fornito o non valido per l'eliminazione.";
                }


            } else if ("review".equalsIgnoreCase(actionType)) {
                String idReviewToDelete = request.getParameter("id_review");
                String gameIdForRedirect = request.getParameter("id_game");

                System.out.println("Richiesta eliminazione RECENSIONE per ID_REVIEW: " + idReviewToDelete + ", Game ID for redirect: " + gameIdForRedirect);

                if (idReviewToDelete != null && !idReviewToDelete.trim().isEmpty()) {
                    try {
                        System.out.println("Tentativo di eliminare recensione con id_review: " + idReviewToDelete);
                        reviewDAO.deleteReview(idReviewToDelete);
                        System.out.println("Recensione eliminata con id_review: " + idReviewToDelete);
                        success = true;
                        operationMessage = "Recensione eliminata con successo!";
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        operationMessage = "Errore durante l'eliminazione della recensione: " + e.getMessage();
                    }
                } else {
                    System.out.println("ID della recensione non fornito per l'eliminazione.");
                    operationMessage = "ID della recensione non fornito o non valido per l'eliminazione.";
                }


                if (gameIdForRedirect != null && !gameIdForRedirect.trim().isEmpty()) {
                    redirectURL = request.getContextPath() + "/hello-servlet";
                    System.out.println("Redirect URL per eliminazione recensione impostato a: " + redirectURL);
                } else {
                    System.out.println("Game ID per redirect non fornito dopo eliminazione recensione. Si userà il default.");

                }

            } else if ("sales".equalsIgnoreCase(actionType)){
                String idSalesToDelete = request.getParameter("id_sales");
                String gameIdForRedirect = request.getParameter("id_game");

                System.out.println("Richiesta eliminazione SALDO ID_SALES: " + idSalesToDelete + ", Game ID for redirect: " + gameIdForRedirect);

                if (idSalesToDelete != null && !idSalesToDelete.trim().isEmpty()) {
                    try {
                        System.out.println("saldo con id_sales: " + idSalesToDelete);
                        salesDAO.deleteSales(idSalesToDelete);
                        System.out.println("Saldo eliminato con id_sales: " + idSalesToDelete);
                        success = true;
                        operationMessage = "Saldo eliminato";
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        operationMessage = "Errore durante l'eliminazione" + e.getMessage();
                    }
                } else {
                    System.out.println("ID del saldo non fornito per l'eliminazione.");
                    operationMessage = "ID del saldo non fornito o non valido per l'eliminazione.";
                }


                if (gameIdForRedirect != null && !gameIdForRedirect.trim().isEmpty()) {
                    redirectURL = request.getContextPath() + "/hello-servlet";
                    System.out.println("Redirect URL per eliminazione saldo impostato a: " + redirectURL);
                } else {
                    System.out.println("Game ID per redirect non fornito dopo eliminazione saldo. Si userà il default.");

                }



            } else {
                System.out.println("Azione non valida: " + actionType);
                operationMessage = "Azione di eliminazione non riconosciuta.";
            }

            // Set messages and redirect
            if (success) {
                request.getSession().setAttribute("successMessage", operationMessage);
            } else {
                request.getSession().setAttribute("errorMessage", operationMessage);
            }

            System.out.println("INFO: Tentativo finale di redirect a: " + redirectURL);
            if (!response.isCommitted()) {
                response.sendRedirect(redirectURL);
            } else {
                System.err.println("ERRORE CRITICO NEL SERVLET Delete: La risposta è già commessa prima del redirect finale. Controllare i log per eccezioni o output prematuro.");
            }
        }
    }