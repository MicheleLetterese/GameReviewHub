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

    public void insertReview(Review review, String id_game){
        try{
            Document doc = new Document("id_review", review.getIdReview())
                    .append("id_game", id_game)
                    .append("critic_score", review.getCriticScore())
                    .append("critic_count", review.getCriticCount())
                    .append("user_score", review.getUserScore())
                    .append("user_count", review.getUserCount());

            System.out.println("DEBUG: Salvataggio review con ID: " + review.getIdReview());
            collection.insertOne(doc);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'inserimento della review", e);
        }
    }

    public ArrayList<Review> getAllReview() {
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                Object object;
                String idReview = doc.getString("id_review");
                String idGame = doc.getString("id_game");

                object = doc.get("critic_score");
                double critic_score;
                if(object instanceof String) critic_score = 0;
                else if(object instanceof Integer) critic_score = doc.getInteger("critic_score");
                else if(object instanceof Double) critic_score = doc.getDouble("critic_score");
                else critic_score = 0;

                object = doc.get("critic_count");
                double critic_count;
                if(object instanceof String) critic_count = 0;
                else if(object instanceof Integer) critic_count = doc.getInteger("critic_count");
                else if(object instanceof Double) critic_count = doc.getDouble("critic_count");
                else critic_count = 0;

                object = doc.get("user_score");
                double user_score = 0;
                if(object instanceof String) user_score = 0;
                else if(object instanceof Integer) user_score = doc.getInteger("user_score");
                else if(object instanceof Double) user_score = doc.getDouble("user_score");
                else user_score = 0;

                object = doc.get("user_count");
                double user_count;
                if(object instanceof String) user_count = 0;
                else if(object instanceof Integer) user_count = doc.getInteger("user_count");
                else if(object instanceof Double) user_count = doc.getDouble("user_count");
                else user_count = 0;

                Review review = new Review(idReview, idGame, critic_score, critic_count, user_score, user_count);
                reviews.add(review);
            }

            System.out.println("Recuperati " + reviews.size() + " review");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero delle recensioni");
        }

        return reviews;
    }


}
