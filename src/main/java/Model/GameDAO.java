package Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

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

    public void insertGame(Game game){
        try{
            Document doc = new Document("id_game", game.getIdGame())
                    .append("name", game.getName())
                    .append("platform", game.getPlatform())
                    .append("year_of_release", game.getYearOfRelease())
                    .append("genre", game.getGenre())
                    .append("publisher", game.getPublisher())
                    .append("developer", game.getDeveloper())
                    .append("rating", game.getRating());

            System.out.println("DEBUG: Salvataggio game con ID: " + game.getIdGame());
            collection.insertOne(doc);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'inserimento del gioco");
        }
    }

    public void deleteGame(ObjectId objectId){
        try{
            DeleteResult result = collection.deleteOne(eq("id_game", objectId));
            if(result.getDeletedCount() == 0){
                throw new RuntimeException("Nessun game trovatp con l'ID specificato: " + objectId);
            }
            System.out.println("DEBUG: Game eliminato con successo, ID: " + objectId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eiminazione del game", e);
        }
    }

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> games = new ArrayList<>();
        try {
            for (Document doc : collection.find().limit(10)) {
                String idGame = String.valueOf(doc.get("id_game"));
                String name = doc.getString("name");
                String platform = doc.getString("platform");
                int yearOfRelease = doc.getInteger("year_of_release", 0);
                String genre = doc.getString("genre");
                String publisher = doc.getString("publisher");
                String developer = doc.getString("developer");
                String rating = doc.getString("rating");

                Game game = new Game(idGame, name, platform, yearOfRelease, genre, publisher, developer, rating);

                games.add(game);
            }
            System.out.println("Recuperati " + games.size() + " games");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero dei giochi");
        }
        return games;
    }

}
