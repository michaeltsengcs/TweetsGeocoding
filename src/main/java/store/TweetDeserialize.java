package store;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by zhipeng on 2015/12/13.
 */
public class TweetDeserialize {
    private MongoCollection tweet_collection;

    public TweetDeserialize(MongoCollection collection) {
        tweet_collection = collection;
    }

    public Document getTweetById(long id) {
        FindIterable<Document> iter = tweet_collection.find(new Document("tweet_id", id));
        return iter.first();
    }

    public ArrayList<Document> getAllTweet() {
        FindIterable<Document> iter = tweet_collection.find();
        final ArrayList<Document> result = new ArrayList<>();
        iter.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                result.add(document);
            }
        });
        return result;
    }

    public String getId(Document d) {return d.get("tweet_id").toString();}
    public String getText(Document d) {return d.get("text").toString();}
    public String getGeoLatitude(Document d) {return d.get("geo_location_lat").toString();}
    public String getGeoLongitude(Document d) {return d.get("geo_location_long").toString();}
    public String getPlaceType(Document d) {return d.get("place_type").toString();}
    public String getPlaceName(Document d) {return d.get("place_name").toString();}
    public String getPlaceCountry(Document d) {return d.get("place_country").toString();}
    public String getUserTimezone(Document d) {return d.get("user_timezone").toString();}
    public String getUserLocation(Document d) {return d.get("user_location").toString();}
    public ArrayList<String> getGeoTags(Document d) {
        String temp = d.get("geo_tag").toString();
        String [] tags = temp.split(",");
        ArrayList<String> result = new ArrayList<>();
        for (String s:tags) {
            result.add(s);
        }
        return result;
    }
}
