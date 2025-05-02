package Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class GameDAO {
    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:UomoNegro22cm@games.vycnrmi.mongodb.net/?retryWrites=true&w=majority&appName=games";
    private static final String DATABASE_NAME = "GameReviewHub";
    private static final String COLLECTION_NAME = "game";

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

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> games = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                String idGame = doc.getString("id_game");
                String name = doc.getString("name");
                System.out.println("Nome: " + name);
                String platform = doc.getString("platform");
                int yearOfRelease = doc.getInteger("year_of_release", 0);
                String genre = doc.getString("genre");
                String publisher = doc.getString("publisher");
                String developer = doc.getString("developer");
                String rating = doc.getString("rating");

                Game game = new Game(idGame, name, platform, yearOfRelease, genre, publisher, developer, rating);

                games.add(game);
            }
            System.out.println("Recuperati " + games.size() + "games");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero dei giochi");
        }
        return games;
    }
}
