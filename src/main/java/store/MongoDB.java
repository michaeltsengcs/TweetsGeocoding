package store;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Created by zhipeng on 2015/12/12.
 */
public class MongoDB {
    //private final static String outFileName = "G:\\study\\UMN\\15fall\\8735\\test2.txt";
    private MongoDatabase db;

    private MongoCollection tweet_collection;

    private final static String database_name = "tweetDB";

    private final static String collection_name = "locations";

    public MongoDB() {
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDatabase(database_name);
            System.out.println("Connect to database successfully");
            if (db.getCollection(collection_name) == null) {
                db.createCollection(collection_name);
                System.out.println("Collection created successfully");
            }
            tweet_collection = db.getCollection(collection_name);
            tweet_collection.createIndex(new Document("tweet_id", 1), new IndexOptions().unique(true));
        } catch(Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public MongoCollection getTweet_collection() {
        return tweet_collection;
    }
}
