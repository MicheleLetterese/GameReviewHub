package Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class ReviewDAO {
    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:UomoNegro22cm@games.vycnrmi.mongodb.net/?retryWrites=true&w=majority&appName=games";
    private static final String DATABASE_NAME = "GameReviewHub";
    private static final String COLLECTION_NAME = "review";

    private static MongoClient mongoClient;
    private static MongoCollection<Document> collection;

    // Blocco statico per l'inizializzazione del client una sola volta (Singleton)
    static {
        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);
            System.out.println("DEBUG: Connessione a MongoDB inizializzata.");
        } catch (Exception e) {
            throw new RuntimeException("Errore durante la connessione a MongoDB", e);
        }
    }

    public ArrayList<Review> getAllReview() {
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                String idReview = doc.getString("id_review");
                String idGame = doc.getString("id_game");
                //System.out.println(idGame);
                int criticScore = doc.getInteger("critic_score", 0);
                int criticCount = doc.getInteger("critic_count", 0);
                int userScore = doc.getInteger("user_score", 0);
                int userCount = doc.getInteger("user_count", 0);


                Review review = new Review(idReview, idGame, criticScore, criticCount, userScore, userCount);

                reviews.add(review);
            }
            System.out.println("Recuperati " + reviews.size() + "review");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero delle recensioni");
        }

        return reviews;
    }

}
