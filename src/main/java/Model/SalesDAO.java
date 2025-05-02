package Model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

public class SalesDAO {
    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:UomoNegro22cm@games.vycnrmi.mongodb.net/?retryWrites=true&w=majority&appName=games";
    private static final String DATABASE_NAME = "GameReviewHub";
    private static final String COLLECTION_NAME = "sales";

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

    public ArrayList<Sales> getAllSales() {
        ArrayList<Sales> salesList = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                String idSales = doc.getString("id_sales");
                String idGame = doc.getString("id_game");

                double naSales = doc.getDouble("na_sales") != null ? doc.getDouble("na_sales") : 0.0;
                double euSales = doc.getDouble("eu_sales") != null ? doc.getDouble("eu_sales") : 0.0;
                double jpSales = doc.getDouble("jp_sales") != null ? doc.getDouble("jp_sales") : 0.0;
                double otherSales = doc.getDouble("other_sales") != null ? doc.getDouble("other_sales") : 0.0;
                double globalSales = doc.getDouble("global_sales") != null ? doc.getDouble("global_sales") : 0.0;

                Sales s = new Sales(idSales, idGame, naSales, euSales, jpSales, otherSales, globalSales);
                salesList.add(s);
            }
            System.out.println("Recuperati " + salesList.size() + "sales");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero delle vendite");
        }

        return salesList;
    }

}
