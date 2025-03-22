
package com.example;

import com.mongodb.client.*;

public class MongoDBConnector {
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getDatabase(String dbname) {
        if(mongoClient == null) {
            mongoClient = MongoClients.create();
        }
        if (mongoDatabase == null) {
            mongoDatabase = mongoClient.getDatabase(dbname);
        }
        return mongoDatabase;
    }

    public static void closeConnection() {
        if(mongoClient != null) {
            mongoClient.close();
        }
        mongoClient = null;
        mongoDatabase = null;
    }
}
