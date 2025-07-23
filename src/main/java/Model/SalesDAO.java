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

public class SalesDAO {
    private static final String CONNECTION_STRING = "mongodb+srv://micheleletterese2:progettoDB@games.vycnrmi.mongodb.net/";
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


    public void deleteSales(String sales_id){
        try {
            int numId = Integer.parseInt(sales_id);

            DeleteResult result = collection.deleteOne(Filters.eq("id_sales", numId));

            if(result.getDeletedCount() == 0){
                throw new RuntimeException("Nessun saldo trovato con ID: " + sales_id);
            }

            System.out.println("DEBUG: Eliminazione saldo trovato: " + sales_id + "id num" + numId + "!!!");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eliminazione del sales");
        }
    }

    public void deleteByGameId(String game_id){
        try{
            DeleteResult deleteResult = collection.deleteOne(Filters.eq("id_game", game_id));
            System.out.println("DAO: Eliminazione completata per Sales associate a Game ID " + game_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante l'eliminazione del sales");
        }

    }



    public boolean updateSales(Sales sales) {
        if ( sales == null || sales.getIdSales() == null || sales.getIdSales().trim().isEmpty()) {
            System.err.println("ERRORE: Oggetto Saldo o ID saldo non valido per l'aggiornamento.");
            return false;
        }
        try {
            int numIdSaldo;
            try{
                numIdSaldo=Integer.parseInt(sales.getIdSales());
            }catch (NumberFormatException e){
                System.err.println("ERR, id non valido");
                return  false;
            }
            Document updatedValues = new Document()
                    .append("na_sales", sales.getNaSales())
                    .append("eu_sales", sales.getEuSales())
                    .append("jp_sales", sales.getJpSales())
                    .append("other_sales", sales.getOtherSales())
                    .append("global_sales", sales.getGlobalSales());

            UpdateResult result= collection.updateOne(
                    Filters.eq("id_sales", numIdSaldo),
                    new Document("$set", updatedValues)
            );
            return result.getModifiedCount() > 0 || (result.getMatchedCount() > 0 && result.getModifiedCount() == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public Sales getSaleByGameId(String game_id) {

        if (game_id == null || game_id.trim().isEmpty()) {
            System.err.println("ERRORE: game_id fornito per la ricerca è nullo o vuoto.");
            return null;
        }

        try {
            int numericGameId = Integer.parseInt(game_id);

            Document doc = collection.find(Filters.eq("id_game", numericGameId)).first();
            //trovato
            if (doc != null) {

                String idSale = String.valueOf(doc.get("id_sales"));
                String idGameFound = String.valueOf(doc.get("id_game"));

                Object object; // Variabile di appoggio per il valore letto dal DB

                object = doc.get("na_sales");
                double naSales;
                if (object instanceof String) {
                    naSales = 0;
                } else if (object instanceof Number) {
                    naSales = ((Number) object).doubleValue();
                } else {
                    naSales = 0;
                }

                // Gestione di euSales
                object = doc.get("eu_sales");
                double euSales;
                if (object instanceof String) {
                    euSales = 0;
                } else if (object instanceof Number) {
                    euSales = ((Number) object).doubleValue();
                } else {
                    euSales = 0;
                }

                // jp sales
                object = doc.get("jp_sales");
                double jpSale;
                if (object instanceof String) {
                    jpSale = 0;
                } else if (object instanceof Number) {
                    jpSale = ((Number) object).doubleValue();
                } else {
                    jpSale = 0;
                }



                object = doc.get("other_sales");
                double otherSale;
                if (object instanceof String) {
                    otherSale = 0;
                } else if (object instanceof Number) {
                    otherSale = ((Number) object).doubleValue();
                } else {
                    otherSale = 0;
                }


                object = doc.get("global_sales");
                double globalSale;
                if (object instanceof String) {
                    globalSale = 0;
                } else if (object instanceof Number) {
                    globalSale = ((Number) object).doubleValue();
                } else {
                    globalSale = 0;
                }

                System.out.println("DEBUG: Trovata review con ID: " + idSale + " per game ID: " + game_id);

                return new Sales(idSale, idGameFound, naSales, euSales, jpSale, otherSale, globalSale);

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



    public ArrayList<Sales> getSalesPaginated(int skip, int limit) {
        ArrayList<Sales> salesList = new ArrayList<>();

        try {
            for (Document doc : collection.find().skip(skip).limit(limit)) {
                Object object;

                String idSales = String.valueOf(doc.get("id_sales"));
                String idGame = String.valueOf(doc.get("id_game"));

                object = doc.get("na_sales");
                double na_sales;
                if (object instanceof Double) na_sales = doc.getDouble("na_sales");
                else if (object instanceof Integer) na_sales = doc.getInteger("na_sales");
                else na_sales = 0;

                object = doc.get("eu_sales");
                double eu_sales;
                if (object instanceof Double) eu_sales = doc.getDouble("eu_sales");
                else if (object instanceof Integer) eu_sales = doc.getInteger("eu_sales");
                else eu_sales = 0;

                object = doc.get("jp_sales");
                double jp_sales;
                if (object instanceof Double) jp_sales = doc.getDouble("jp_sales");
                else if (object instanceof Integer) jp_sales = doc.getInteger("jp_sales");
                else jp_sales = 0;

                object = doc.get("other_sales");
                double other_sales;
                if (object instanceof Double) other_sales = doc.getDouble("other_sales");
                else if (object instanceof Integer) other_sales = doc.getInteger("other_sales");
                else other_sales = 0;

                object = doc.get("global_sales");
                double global_sales;
                if (object instanceof Double) global_sales = doc.getDouble("global_sales");
                else if (object instanceof Integer) global_sales = doc.getInteger("global_sales");
                else global_sales = 0;

                Sales s = new Sales(idSales, idGame, na_sales, eu_sales, jp_sales, other_sales, global_sales);
                salesList.add(s);
            }
            System.out.println("Recuperati " + salesList.size() + " vendite (skip=" + skip + ", limit=" + limit + ")");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero delle vendite con paginazione");
        }

        return salesList;
    }

    public long getTotalSalesCount() {
        try {
            // Conta il numero totale di documenti nella collezione delle vendite
            return collection.countDocuments();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Errore durante il conteggio delle vendite");
        }
    }
/*
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
*/
}
