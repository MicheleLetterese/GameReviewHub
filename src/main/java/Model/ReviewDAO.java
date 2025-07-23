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
            int numericId = Integer.parseInt(id_review);

            DeleteResult result = collection.deleteOne(Filters.eq("id_review", numericId));

            if(result.getDeletedCount() == 0){
                throw new RuntimeException("Nessuna review trovata con ID: " + id_review);
            }
            //System.out.println("DEBUG: Eliminazione del review con ID: " + id_review);
            System.out.println("DEBUG: Eliminazione review  ID: " + id_review + " ID numerico: " + numericId + ")");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eliminazione del review", e);
        }
    }

    public void deleteByGameId(String game_id){
        try{
            int numericGameId = Integer.parseInt(game_id);
            DeleteResult result = collection.deleteMany(Filters.eq("id_game", numericGameId)); // Use deleteMany and pass the game_id value
        System.out.println("DEBUG: Eliminazione completata per Review associate a Game ID: " + game_id + ", Conto: " + result.getDeletedCount());
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Errore durante l'eliminazione delle review per game ID", e);
    }
}

    public Review getReviewByGameId(String game_id) {

        if (game_id == null || game_id.trim().isEmpty()) {
            System.err.println("ERRORE: game_id fornito per la ricerca è nullo o vuoto.");
            return null;
        }

        try {
            int numericGameId = Integer.parseInt(game_id);

            Document doc = collection.find(Filters.eq("id_game", numericGameId)).first();
            //trovato
            if (doc != null) {

                String idReview = String.valueOf(doc.get("id_review"));
                String idGameFound = String.valueOf(doc.get("id_game"));

                Object object; // Variabile di appoggio per il valore letto dal DB

                // Gestione di critic_score
                object = doc.get("critic_score");
                double critic_score;
                if (object instanceof String) {
                    critic_score = 0; // Se è una stringa ("mixed", "tbd", ecc.), imposta a 0
                } else if (object instanceof Number) {
                    critic_score = ((Number) object).doubleValue(); // Converte qualsiasi tipo numerico a double
                } else {
                    critic_score = 0; // Default per null o altri tipi inattesi
                }

                // Gestione di critic_count
                object = doc.get("critic_count");
                double critic_count;
                if (object instanceof String) {
                    critic_count = 0;
                } else if (object instanceof Number) {
                    critic_count = ((Number) object).doubleValue();
                } else {
                    critic_count = 0;
                }

                // Gestione di user_score
                object = doc.get("user_score");
                double user_score;
                if (object instanceof String) {
                    user_score = 0;
                } else if (object instanceof Number) {
                    user_score = ((Number) object).doubleValue();
                } else {
                    user_score = 0;
                }

                // Gestione di user_count
                object = doc.get("user_count");
                double user_count;
                if (object instanceof String) {
                    user_count = 0;
                } else if (object instanceof Number) {
                    user_count = ((Number) object).doubleValue();
                } else {
                    user_count = 0;
                }

                System.out.println("DEBUG: Trovata review con ID: " + idReview + " per game ID: " + game_id);

                return new Review(idReview, idGameFound, critic_score, critic_count, user_score, user_count);
               // return new Review(idReview, idGameFound, criticScore, criticCount, userScore, userCount);

            } else {

                System.out.println("DEBUG: Nessuna review trovata per game ID: " + game_id);
                return null;
            }

        } catch (NumberFormatException e) {

            System.err.println("ERRORE: L'ID del gioco fornito non è un numero valido: " + game_id);

            throw new RuntimeException("Formato ID del gioco non valido: " + game_id, e);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero della review per game ID: " + game_id, e);
        }
    }


    public boolean updateReview(Review review) {
        if (review == null || review.getIdReview() == null || review.getIdReview().trim().isEmpty()) {
            System.err.println("ERRORE: Oggetto Review o ID della review non valido per l'aggiornamento.");
            return false;
        }
        try {
            int numericIdReview;
            try {
                numericIdReview = Integer.parseInt(review.getIdReview());
            } catch (NumberFormatException e) {
                System.err.println("ID non valido: " + review.getIdReview());
                return false;
            }
            Document updatedValues = new Document()
                    .append("critic_score", review.getCriticScore())
                    .append("critic_count", review.getCriticCount())
                    .append("user_score", review.getUserScore())
                    .append("user_count", review.getUserCount());

            UpdateResult result = collection.updateOne(
                    Filters.eq("id_review", numericIdReview),
                    new Document("$set", updatedValues)
            );
            return result.getModifiedCount() > 0 || (result.getMatchedCount() > 0 && result.getModifiedCount() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<Review> getReviewsPaginated(int skip, int limit) {
        ArrayList<Review> reviews = new ArrayList<>();

        try {
            for (Document doc : collection.find().skip(skip).limit(limit)) {
                Object object;

                String idReview = String.valueOf(doc.get("id_review"));
                String idGame = String.valueOf(doc.get("id_game"));

                object = doc.get("critic_score");
                double critic_score;
                if (object instanceof String) critic_score = 0;
                else if (object instanceof Integer) critic_score = doc.getInteger("critic_score");
                else if (object instanceof Double) critic_score = doc.getDouble("critic_score");
                else critic_score = 0;

                object = doc.get("critic_count");
                double critic_count;
                if (object instanceof String) critic_count = 0;
                else if (object instanceof Integer) critic_count = doc.getInteger("critic_count");
                else if (object instanceof Double) critic_count = doc.getDouble("critic_count");
                else critic_count = 0;

                object = doc.get("user_score");
                double user_score = 0;
                if (object instanceof String) user_score = 0;
                else if (object instanceof Integer) user_score = doc.getInteger("user_score");
                else if (object instanceof Double) user_score = doc.getDouble("user_score");
                else user_score = 0;

                object = doc.get("user_count");
                double user_count;
                if (object instanceof String) user_count = 0;
                else if (object instanceof Integer) user_count = doc.getInteger("user_count");
                else if (object instanceof Double) user_count = doc.getDouble("user_count");
                else user_count = 0;

                Review review = new Review(idReview, idGame, critic_score, critic_count, user_score, user_count);
                reviews.add(review);
            }

            System.out.println("Recuperate " + reviews.size() + " recensioni (skip=" + skip + ", limit=" + limit + ")");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero delle recensioni con paginazione");
        }

        return reviews;
    }

    public long getTotalReviewsCount() {
        try {
            // Conta il numero totale di documenti nella collezione delle recensioni
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il conteggio delle recensioni");
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
