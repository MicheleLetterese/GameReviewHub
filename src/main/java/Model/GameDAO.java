package Model;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

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


    public void deleteGame(String objectIdString) {
        try {
            System.out.println("idgame " + objectIdString);
            int numericId;
            try {
                numericId = Integer.parseInt(objectIdString);
            } catch (NumberFormatException e) {
                System.err.println("ID fornito '" + objectIdString + "non valido");
                throw new IllegalArgumentException("Id non valido: " + objectIdString, e);
            }

            //trova gioco tramite id

            Document gameToFind = collection.find(eq("id_game", numericId)).first();
            if (gameToFind != null) {
                System.out.println("INFO: Gioco trovato prima dell'eliminazione: " + gameToFind.toJson());
            } else {
                System.out.println("WARN: Gioco NON trovato con id_game (numerico) '" + numericId + "' prima del tentativo di eliminazione. Questo è inaspettato se il gioco dovrebbe esistere.");
            }

            //elimina

            DeleteResult result = collection.deleteOne(eq("id_game", numericId)); //
            if (result.getDeletedCount() == 0) {
                System.err.println("ERRORE DAO: Nessun gioco eliminato" + numericId + " non trovato o già eliminato.");
                throw new RuntimeException("Nessun game trovato con l'ID numerico specificato: " + numericId + " (originale stringa: " + objectIdString + ")");
            }
            System.out.println("Game eliminato con successo, ID " + numericId + " (originale stringa: " + objectIdString + ")");

        } catch (IllegalArgumentException e) { // conversione non numerico
            throw e;
        } catch (MongoException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore database durante l'eliminazione del game: " + e.getMessage(), e);
        }

    }

    public void updateGame(Game game) {
        if (game == null || game.getIdGame() == null || game.getIdGame().trim().isEmpty()) {
            System.err.println("ERRORE: Oggetto Game o ID del gioco non valido per l'aggiornamento.");
            throw new IllegalArgumentException("L'oggetto Game e il suo ID non possono essere null o vuoti per l'aggiornamento.");
        }

        String gameIdString = game.getIdGame();
        int gameIdNumeric;
        try {
            gameIdNumeric = Integer.parseInt(gameIdString);
        } catch (NumberFormatException e) {
            System.err.println("ERRORE: L'ID del gioco '" + gameIdString + "' non è un intero valido.");
            throw new IllegalArgumentException("L'ID del gioco fornito non è un intero valido: " + gameIdString, e);
        }

        try {
            Bson filter = Filters.eq("id_game", gameIdNumeric);

            System.out.println("ID " + game.getIdGame());

            Bson updateOperation = combine(
                    set("name", game.getName()),
                    set("platform", game.getPlatform()),
                    set("year_of_release", game.getYearOfRelease()),
                    set("genre", game.getGenre()),
                    set("publisher", game.getPublisher()),
                    set("developer", game.getDeveloper()),
                    set("rating", game.getRating())
            );

            UpdateResult updateResult = collection.updateOne(filter, updateOperation);

            System.out.println("id " + game.getIdGame() +
                    "Corrispondenze " + updateResult.getMatchedCount() +
                    ", Documenti " + updateResult.getModifiedCount());
            if (updateResult.getMatchedCount() == 0) {
                System.out.println("Nessun gioco trovato id " + game.getIdGame() + ".");

            } else if (updateResult.getModifiedCount() == 0) {
                System.out.println("INFO: Il gioco con ID: " + game.getIdGame() + " è stato trovato ma non necessitava di modifiche (i dati erano identici).");
            }

        } catch (Exception e) {
            e.printStackTrace();

            String gameIdInfo = (game != null && game.getIdGame() != null) ? " con ID: " + game.getIdGame() : "";
            throw new RuntimeException("Errore " + gameIdInfo, e);
        }
    }


    public Game getGameById(String idGameString) {
        Game game = null;
        try {
            int numericId;
            try {
                numericId = Integer.parseInt(idGameString);
            } catch (NumberFormatException e) {
                System.err.println("ERRORE DAO: ID del gioco '" + idGameString + "' non è un numero intero valido per la ricerca.");
                return null;
            }

            Document doc = collection.find(eq("id_game", numericId)).first();

            if (doc != null) {
                String idFromDb = String.valueOf(doc.getInteger("id_game"));
                String name = doc.getString("name");
                String platform = doc.getString("platform");

                int yearOfRelease = 0;
                Object yearObj = doc.get("year_of_release");
                if (yearObj instanceof Number) {
                    yearOfRelease = ((Number) yearObj).intValue();
                } else if (yearObj instanceof String) {
                    try {
                        yearOfRelease = Integer.parseInt((String) yearObj);
                    } catch (NumberFormatException ignored) {
                        System.err.println("y" + idFromDb + " non-numeric " + yearObj);
                    }
                } else if (yearObj == null) {
                    System.err.println("y" + idFromDb + " null.");
                }


                String genre = doc.getString("genre");
                String publisher = doc.getString("publisher");
                String developer = doc.getString("developer");
                String rating = doc.getString("rating");

                game = new Game(idFromDb, name, platform, yearOfRelease, genre, publisher, developer, rating);
                System.out.println("Gioco trovato " + numericId + ": " + name);
            } else {
                System.out.println("non trovato" + numericId + " (originale stringa: " + idGameString + ")");
            }
        } catch (MongoException e) {
            System.err.println("ERRORE DAO MongoDB " + idGameString);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("ERRORE DAO" + idGameString);
            e.printStackTrace();
        }
        return game;
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

    public ArrayList<Game> getGamesPaginated(int skip, int limit) {
        ArrayList<Game> games = new ArrayList<>();
        try {
            for (Document doc : collection.find().skip(skip).limit(limit)) {
                String idGame = String.valueOf(doc.get("id_game"));
                String name = doc.getString("name");
                String platform = doc.getString("platform");
                Object object = doc.get("year_of_release");
                int yearOfRelease = 0;
                if(object instanceof String) yearOfRelease = 0;
                else if(object instanceof Integer) yearOfRelease = doc.getInteger("year_of_release");
                //int yearOfRelease = doc.getInteger("year_of_release", 0);
                String genre = doc.getString("genre");
                String publisher = doc.getString("publisher");
                String developer = doc.getString("developer");
                String rating = doc.getString("rating");

                Game game = new Game(idGame, name, platform, yearOfRelease, genre, publisher, developer, rating);
                games.add(game);
            }
            System.out.println("Recuperati " + games.size() + " giochi (skip=" + skip + ", limit=" + limit + ")");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero dei giochi con paginazione");
        }
        return games;
    }

    public long getTotalGamesCount() {
        try {
            // Conta il numero totale di documenti nella collezione dei giochi
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il conteggio dei giochi");
        }
    }

    public List<Document> findGamesByPlatformAndScore(String platform, double minUserScore, int skip, int limit) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.lookup("review", "id_game", "id_game", "recensioni"),
                Aggregates.unwind("$recensioni"),
                Aggregates.match(Filters.and(
                        Filters.eq("platform", platform),
                        Filters.gte("recensioni.user_score", minUserScore)
                )),
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.include("name", "platform", "genre", "developer"),
                        Projections.computed("user_score", "$recensioni.user_score"),
                        Projections.computed("critic_score", "$recensioni.critic_score")
                )),
                Aggregates.skip(skip),
                Aggregates.limit(limit)
        );

        return collection.aggregate(pipeline).into(new ArrayList<>());
    }

    public long countGamesByPlatformAndScore(String platform, double minUserScore) {
        List<Bson> pipeline = Arrays.asList(
                Aggregates.lookup("review", "id_game", "id_game", "recensioni"),
                Aggregates.unwind("$recensioni"),
                Aggregates.match(Filters.and(
                        Filters.eq("platform", platform),
                        Filters.gte("recensioni.user_score", minUserScore)
                )),
                Aggregates.count("total")
        );

        Document result = collection.aggregate(pipeline).first();
        return result != null ? result.getInteger("total") : 0;
    }

}
