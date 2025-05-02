package Controller;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Entro");
        GameDAO gameDAO = new GameDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        SalesDAO salesDAO = new SalesDAO();

        ArrayList<Game> games = gameDAO.getAllGames();
        ArrayList<Review> reviews = reviewDAO.getAllReview();
        ArrayList<Sales> sales = salesDAO.getAllSales();

        request.setAttribute("games", games);
        request.setAttribute("reviews", reviews);
        request.setAttribute("sales", sales);

        try {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    /*public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }*/

}