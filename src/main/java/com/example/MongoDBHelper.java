package com.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

import org.bson.Document;

public class MongoDBHelper {
  
    private MongoDatabase database;

    public MongoDBHelper(MongoDatabase database) {
        this.database = database;
    }

    public void saveKontakt(Kontakt kontakt) {
        MongoCollection<Document> collection = database.getCollection("kontakte");

        List<Document> termineDocs = new ArrayList<>();

        Stream.of(kontakt.getTerminplaner().getAllTermine())
            .map(terminDoc -> new Document()
                .append("beschreibung", terminDoc.getBeschreibung())
                .append("datum", terminDoc.getDatum().format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .forEach(termineDocs::add);

        Document adressDoc = new Document();
        adressDoc.append("strasse", kontakt.getAdresse().getStrasse())
            .append("hausnummer", kontakt.getAdresse().getHausnummer())
            .append("postleitzahl", kontakt.getAdresse().getPLZ())
            .append("stadt", kontakt.getAdresse().getStadt());
        
        Document kontaktDoc = new Document();
        kontaktDoc.append("name", kontakt.getName())
            .append("telefonnummer", kontakt.getNummer())
            .append("email", kontakt.getEmail())
            .append("adresse", adressDoc)
            .append("terminplaner" , termineDocs);

            collection.insertOne(kontaktDoc);
    }

    public void deleteKontakt(Kontakt kontakt) {
        MongoCollection<Document> collection = database.getCollection("kontakte");
        collection.deleteOne(Filters.eq("name", kontakt.getName()));

    }

    public List<Kontakt> loadKontakte() {
        MongoCollection<Document> collection = database.getCollection("kontakte");
        List<Kontakt> kontakte = new ArrayList<>();
        
        for (Document doc : collection.find()) {
            kontakte.add(convertToKontakt(doc));
        }
    
        return kontakte;
    }

    private Kontakt convertToKontakt(Document doc) {
        String name = doc.getString("name");
        String nummer = doc.getString("telefonnummer");
        String email = doc.getString("email");

        Document adressDoc = (Document) doc.get("adresse");
        Adresse adresse = new Adresse(adressDoc.getString("strasse"),
            adressDoc.getString("hausnummer"),
            adressDoc.getString("postleitzahl"),
            adressDoc.getString("stadt"));

        Kontakt convertKontakt = new Kontakt(name, nummer, email, adresse);

        @SuppressWarnings("unchecked")
        List<Document> termineDocs = (List<Document>) doc.get("terminplaner");
        for(Document terminDoc : termineDocs) {
            String beschreibung = terminDoc.getString("beschreibung");
            LocalDate datum = LocalDate.parse(terminDoc.getString("datum"), DateTimeFormatter.ISO_LOCAL_DATE);
            convertKontakt.getTerminplaner().addTermin(datum, new Termin(beschreibung, datum));
            }
        return convertKontakt;
    }
}
