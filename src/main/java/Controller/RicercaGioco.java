package Controller;

import Model.GameDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

@WebServlet("/RicercaGioco")
public class RicercaGioco extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String platform = request.getParameter("platform");
        String minScoreStr = request.getParameter("minUserScore");
        double minUserScore = 0.0;
        if (minScoreStr != null && !minScoreStr.trim().isEmpty()) {
            minUserScore = Double.parseDouble(minScoreStr.trim());
        }

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException ignored) {}

        int limit = 15;
        int skip = (page - 1) * limit;

        GameDAO gameDAO = new GameDAO();
        List<Document> risultati = gameDAO.findGamesByPlatformAndScore(platform, minUserScore, skip, limit);
        long totaleRisultati = gameDAO.countGamesByPlatformAndScore(platform, minUserScore);

        request.setAttribute("platform", platform);
        request.setAttribute("minUserScore", minUserScore);
        request.setAttribute("page", page);
        request.setAttribute("risultati", risultati);
        request.setAttribute("totaleRisultati", totaleRisultati);

        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/sezione_risultati.jsp").forward(request, response);
    }
}