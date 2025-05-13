package Controller;

import java.io.*;
import java.util.ArrayList;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    private static final int LIMIT = 10;  // Limite per ogni pagina

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GameDAO gameDAO = new GameDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        SalesDAO salesDAO = new SalesDAO();

        // Recupera la pagina corrente (default 1 se non è impostata)
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1; // Se il parametro non è valido, si imposta la pagina a 1
            }
        }

        // Calcola lo skip per la paginazione
        int skip = (page - 1) * LIMIT;

        // Recupera i dati con la paginazione
        ArrayList<Game> games = gameDAO.getGamesPaginated(skip, LIMIT);
        ArrayList<Review> reviews = reviewDAO.getReviewsPaginated(skip, LIMIT);
        ArrayList<Sales> sales = salesDAO.getSalesPaginated(skip, LIMIT);

        // Calcola il numero totale di giochi, recensioni e vendite
        long totalGames = gameDAO.getTotalGamesCount();
        long totalReviews = reviewDAO.getTotalReviewsCount();
        long totalSales = salesDAO.getTotalSalesCount();

        // Calcola il numero di pagine totali
        int totalGamePages = (int) Math.ceil((double) totalGames / LIMIT);
        int totalReviewPages = (int) Math.ceil((double) totalReviews / LIMIT);
        int totalSalesPages = (int) Math.ceil((double) totalSales / LIMIT);

        // Imposta gli attributi per la richiesta
        request.setAttribute("games", games);
        request.setAttribute("reviews", reviews);
        request.setAttribute("sales", sales);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalGamePages", totalGamePages);
        request.setAttribute("totalReviewPages", totalReviewPages);
        request.setAttribute("totalSalesPages", totalSalesPages);

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
