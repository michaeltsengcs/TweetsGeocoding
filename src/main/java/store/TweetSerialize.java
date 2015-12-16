package store;

import org.bson.Document;
import twitter4j.Status;

import java.util.ArrayList;



/**
 * Created by zhipeng on 2015/12/13.
 */
// To serialize each field of a tweet status
public class TweetSerialize {
    public Document parseTweet(Status tweet) {
        Document item = new Document();
        item.append("tweet_id", tweet.getId())
                .append("text", tweet.getText())
                .append("geo_location_lat", tweet.getGeoLocation() != null ? tweet.getGeoLocation().getLatitude():null)
                .append("geo_location_long", tweet.getGeoLocation() != null ? tweet.getGeoLocation().getLongitude():null)
                .append("place_type", tweet.getPlace() != null ? tweet.getPlace().getPlaceType():null)
                .append("place_name", tweet.getPlace() != null ? tweet.getPlace().getName():null)
                .append("place_country", tweet.getPlace() != null ? tweet.getPlace().getCountry():null)
                .append("user_timezone", tweet.getUser().getTimeZone() != null ? tweet.getUser().getTimeZone():null)
                .append("user_location", tweet.getUser().getLocation() != null ? tweet.getUser().getLocation():null);
        return item;
    }

    public Document parseRecord(String record) {
        Document item = new Document();
        String [] fields = record.split("\t");
        item.append("tweet_id", fields[0])
                .append("text", fields[1])
                .append("geo_location_lat", fields[2])
                .append("geo_location_long", fields[3])
                .append("place_type", fields[4])
                .append("place_name", fields[5])
                .append("place_country_code", fields[6])
                .append("user_timezone", fields[7])
                .append("user_location", fields[8])
                .append("geo_tag", new ArrayList<String>().toString());
        return item;
    }
}
