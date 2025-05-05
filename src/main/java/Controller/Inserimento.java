package Controller;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Inserimento")
public class Inserimento extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/inserimento.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        Game game = new Game(
                request.getParameter("idGame"),
                request.getParameter("name"),
                request.getParameter("platform"),
                Integer.parseInt(request.getParameter("year")),
                request.getParameter("genre"),
                request.getParameter("publisher"),
                request.getParameter("developer"),
                request.getParameter("rating")
        );

        Review review = new Review(
                request.getParameter("idReview"),
                request.getParameter("idGame"),
                Double.parseDouble(request.getParameter("criticScore")),
                Double.parseDouble(request.getParameter("criticCount")),
                Double.parseDouble(request.getParameter("userScore")),
                Double.parseDouble(request.getParameter("userCount"))
        );

        Sales sales = new Sales(
                request.getParameter("idSales"),
                request.getParameter("idGame"),
                Double.parseDouble(request.getParameter("naSales")),
                Double.parseDouble(request.getParameter("euSales")),
                Double.parseDouble(request.getParameter("jpSales")),
                Double.parseDouble(request.getParameter("otherSales")),
                Double.parseDouble(request.getParameter("globalSales"))
                );

        GameDAO gameDAO = new GameDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        SalesDAO salesDAO = new SalesDAO();

        gameDAO.insertGame(game);
        reviewDAO.insertReview(review, game.getIdGame());
        salesDAO.insertSales(sales, game.getIdGame());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
