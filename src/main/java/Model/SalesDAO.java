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

    public void insertSales(Sales sales, String id_game){
        try{
            Document doc = new Document("id_sales", sales.getIdSales())
                    .append("id_game", id_game)
                    .append("na_sales", sales.getNaSales())
                    .append("eu_sales", sales.getEuSales())
                    .append("jp_sales", sales.getJpSales())
                    .append("other_sales", sales.getOtherSales())
                    .append("global_sales", sales.getGlobalSales());

            System.out.println("DEBUG: Salvataggio sales con ID: " + sales.getIdSales());
            collection.insertOne(doc);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'inserimento del sales");
        }
    }

    public ArrayList<Sales> getAllSales() {
        ArrayList<Sales> salesList = new ArrayList<>();

        try {
            for (Document doc : collection.find().limit(10)) {
                Object object;

                String idSales = String.valueOf(doc.get("id_sales"));
                String idGame = String.valueOf(doc.get("id_game"));

                object = doc.get("na_sales");
                double na_sales;
                if(object instanceof Double) na_sales = doc.getDouble("na_sales");
                else if(object instanceof Integer) na_sales = doc.getInteger("na_sales");
                else na_sales = 0;

                object = doc.get("eu_sales");
                double eu_sales;
                if(object instanceof Double) eu_sales = doc.getDouble("eu_sales");
                else if(object instanceof Integer) eu_sales = doc.getInteger("eu_sales");
                else eu_sales = 0;

                object = doc.get("jp_sales");
                double jp_sales;
                if(object instanceof Double) jp_sales = doc.getDouble("jp_sales");
                else if(object instanceof Integer) jp_sales = doc.getInteger("jp_sales");
                else jp_sales = 0;

                object = doc.get("other_sales");
                double other_sales;
                if(object instanceof Double) other_sales = doc.getDouble("other_sales");
                else if(object instanceof Integer) other_sales = doc.getInteger("other_sales");
                else other_sales = 0;

                object = doc.get("global_sales");
                double global_sales;
                if(object instanceof Double) global_sales = doc.getDouble("global_sales");
                else if(object instanceof Integer) global_sales = doc.getInteger("global_sales");
                else global_sales = 0;

                Sales s = new Sales(idSales, idGame, na_sales, eu_sales, jp_sales, other_sales, global_sales);
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
