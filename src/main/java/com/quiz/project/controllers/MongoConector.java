package com.quiz.project.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoConector {

    private static final MongoClientURI uri = new MongoClientURI(
            "mongodb://root:thisisarealyhardpassword@cluster0-sha"
                    + "rd-00-00.wnwbw.mongodb.net:27017,cluster0-shard-00-01.wnwbw.mo"
                    + "ngodb.net:27017,cluster0-shard-00-02.wnwbw.mongodb.net:27017/QuizPr"
                    + "oject?ssl=true&replicaSet=atlas-ogak8t-shard-0&authSource=admin&w=majority");

    private static  MongoClient mongoClient = null;

    private static MongoClient getClient() {
        if (mongoClient == null) {
            mongoClient = new MongoClient(uri);
        }
        return mongoClient;
    }

    private static MongoDatabase getQuizDatabase() {
        return getClient().getDatabase("QuizProject");
    }

    public static MongoIterable<String> getCollectionNames() {
        return getQuizDatabase().listCollectionNames();
    }

    public static MongoCollection<Document> getCollection(String colName) {
        return getQuizDatabase().getCollection(colName);
    }

    public static Integer getNextIndex(String collection) {

        MongoCollection<Document> counterCol = getCollection("counters");

        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("count", 1));
        Document doc =  counterCol.findOneAndUpdate(Filters.eq("collection", collection), update);

        return (Integer) doc.get("count");
    }

}
