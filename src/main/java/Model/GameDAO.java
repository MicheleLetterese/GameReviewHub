package Model;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import static com.mongodb.client.model.Filters.eq;

public class GameDAO {

    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:progettoDB@games.vycnrmi.mongodb.net/";
    private static final String DATABASE_NAME = "GameReviewHub";
    private static final String COLLECTION_NAME = "game";

    private static MongoClient mongoClient;
    private static MongoCollection<Document> collection;



    static {
        System.out.println("log1: Tentativo di inizializzare la connessione a MongoDB...");
        // Potresti voler loggare la stringa di connessione, ma fai attenzione a non esporre password
        // System.out.println("DEBUG: Stringa di connessione: " + CONNECTION_STRING); // Attenzione alle password!
        System.out.println("log1: Database: " + DATABASE_NAME + ", Collezione: " + COLLECTION_NAME);

        try {
            mongoClient = MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            collection = database.getCollection(COLLECTION_NAME);

            System.out.println("logok: Connessione a MongoDB inizializzata con successo.");

        } catch (Exception e) {
            System.err.println("ERRORE: Fallimento durante l'inizializzazione della connessione a MongoDB.");
            System.err.println("Tipo di eccezione: " + e.getClass().getName());
            System.err.println("Messaggio di errore: " + e.getMessage());
            System.err.println("StackTrace completa:");
            e.printStackTrace(System.err); // Stampa la traccia dello stack per maggiori dettagli

            throw new RuntimeException("Errore critico durante la connessione a MongoDB. Controlla i log di errore per i dettagli.", e);
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

    public void deleteGame(String objectIdString){
        try{
            System.out.println("DEBUG: Ricevuto id_game (stringa) per l'eliminazione: " + objectIdString);
            int numericId;
            try {
                numericId = Integer.parseInt(objectIdString); // CONVERTE "1007" in 1007 (numero)
            } catch (NumberFormatException e) {
                System.err.println("ERRORE: L'ID fornito '" + objectIdString + "' non è un numero intero valido.");
                throw new IllegalArgumentException("ID del gioco non valido per l'eliminazione (deve essere numerico): " + objectIdString, e);
            }

            System.out.println("DEBUG: Tentativo di eliminare game con id_game (numerico): " + numericId);

            Document gameToFind = collection.find(eq("id_game", numericId)).first(); // USA numericId
            if (gameToFind != null) {
                System.out.println("INFO: Gioco trovato prima dell'eliminazione: " + gameToFind.toJson());
            } else {

                System.out.println("WARN: Gioco NON trovato con id_game (numerico) '" + numericId + "' prima del tentativo di eliminazione. Questo è inaspettato se il gioco dovrebbe esistere.");
            }

            DeleteResult result = collection.deleteOne(eq("id_game", numericId)); //
            if(result.getDeletedCount() == 0){

                System.err.println("ERRORE DAO: Nessun gioco eliminato. Documento con id_game (numerico) " + numericId + " non trovato o già eliminato.");
                throw new RuntimeException("Nessun game trovato con l'ID numerico specificato: " + numericId + " (originale stringa: " + objectIdString + ")");
            }
            System.out.println("INFO: Game eliminato con successo, ID numerico: " + numericId + " (originale stringa: " + objectIdString + ")");

        } catch (IllegalArgumentException e) { // Cattura l'eccezione dalla conversione se l'ID non è numerico
            System.err.println("ERRORE DAO: " + e.getMessage());
            throw e; // Rilancia per farla gestire dal servlet
        } catch (MongoException e) { // Cattura eccezioni specifiche di MongoDB
            System.err.println("ERRORE DAO MongoDB: Errore durante l'interazione con il database per l'eliminazione. ID: " + objectIdString);
            e.printStackTrace();
            throw new RuntimeException("Errore database durante l'eliminazione del game: " + e.getMessage(), e);
        }

    }

    public boolean updateGame(Game game) {
        if (game == null || game.getIdGame() == null || game.getIdGame().trim().isEmpty()) {
            System.err.println("ERRORE: Oggetto Game o ID del gioco non valido per l'aggiornamento.");
            throw new IllegalArgumentException("L'oggetto Game e il suo ID non possono essere null o vuoti per l'aggiornamento.");
        }
        try {
            System.out.println("DEBUG: Tentativo di aggiornare il game con ID: " + game.getIdGame());

            Document updatedDocument = new Document();
            updatedDocument.append("name", game.getName());
            updatedDocument.append("platform", game.getPlatform());
            updatedDocument.append("year_of_release", game.getYearOfRelease());
            updatedDocument.append("genre", game.getGenre());
            updatedDocument.append("publisher", game.getPublisher());
            updatedDocument.append("developer", game.getDeveloper());
            updatedDocument.append("rating", game.getRating());

            UpdateResult result = collection.updateOne(
                    eq("id_game", game.getIdGame()),
                    new Document("$set", updatedDocument)
            );

            if (result.getModifiedCount() > 0) {
                System.out.println("INFO: Game aggiornato con successo, ID: " + game.getIdGame());
                return true;
            } else if (result.getMatchedCount() > 0 && result.getModifiedCount() == 0) {
                System.out.println("INFO: Game trovato con ID: " + game.getIdGame() + " ma nessun campo è stato modificato (i dati erano identici).");
                return true;
            } else {
                System.out.println("WARN: Nessun game trovato con l'ID specificato per l'aggiornamento: " + game.getIdGame());
                return false;
            }
        } catch (Exception e) {
            System.err.println("ERRORE: Errore durante l'aggiornamento del game con ID: " + game.getIdGame());
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'aggiornamento del game: " + e.getMessage(), e);
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
