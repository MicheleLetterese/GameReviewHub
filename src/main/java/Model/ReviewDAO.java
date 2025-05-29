package Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ReviewDAO {
    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:progettoDB@games.vycnrmi.mongodb.net/";
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


    public void deleteReview(String id_review){
        try{
            DeleteResult result = collection.deleteOne(Filters.eq("id_review", id_review));
            if(result.getDeletedCount() == 0){
                throw new RuntimeException("Nessuna review trovata con ID: " + id_review);
            }
            System.out.println("DEBUG: Eliminazione del review con ID: " + id_review);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eliminazione del review", e);
        }
    }

    public void deleteByGameId(String game_id){
        try{
            DeleteResult result = collection.deleteOne(Filters.eq("id_game"));
            System.out.println("DEBUG: Eliminazione completata per Review associate a Game ID" + game_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eliminazione del review", e);
        }
    }



    public boolean updateReview(Review review) {
        if (review == null || review.getIdReview() == null || review.getIdReview().trim().isEmpty()) {
            System.err.println("ERRORE: Oggetto Review o ID della review non valido per l'aggiornamento.");
            return false;
        }
        try {
            Document updatedValues = new Document()
                    .append("critic_score", review.getCriticScore())
                    .append("critic_count", review.getCriticCount())
                    .append("user_score", review.getUserScore())
                    .append("user_count", review.getUserCount());

            UpdateResult result= collection.updateOne(
                    Filters.eq("id_review", review.getIdReview()), // Filtra per l'ID della review
                    new Document("$set", updatedValues)
            );
            return result.getModifiedCount() > 0 || (result.getMatchedCount() > 0 && result.getModifiedCount() == 0); // Successo se modificato o se già aggiornato
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Review> getAllReview() {
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                Object object;
                String idReview = String.valueOf(doc.get("id_review"));
                String idGame = String.valueOf(doc.get("id_game"));

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
