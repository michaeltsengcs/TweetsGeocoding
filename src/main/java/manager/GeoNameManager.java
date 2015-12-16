package manager;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.geonames.*;
import refiners.RefinerInterface;
import store.GeonamesGazetter;
import store.MongoDB;
import store.TweetSerialize;
import twitter4j.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhipeng on 2015/11/22.
 */

public class GeoNameManager {
    private GeonamesGazetter gazetter = new GeonamesGazetter();

    private MongoDB _mongoDB = new MongoDB();

    private RefinerManager _refinerManager;

    public GeoNameManager(List<RefineStrategy> refineStrategyList) {
        _refinerManager = new RefinerManager(refineStrategyList);
    }

    public void GeoTag(HashMap<Status, List<String>> locations) throws Exception {
        // Create search criteria
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setFuzzy(0.8);
        searchCriteria.setMaxRows(10);
        searchCriteria.setLanguage("en");

        for (Map.Entry<Status, List<String>> entry : locations.entrySet()) {
            List<String> list = new ArrayList<>();
            for (String word : entry.getValue()) {
                searchCriteria.setQ(word);
                ToponymSearchResult searchResult = gazetter.searchByToponymCriteria(searchCriteria);
                if (searchResult.getToponyms().size() == 0) {
                    continue;
                }
                String result = _refinerManager.refineLocation(searchResult, entry.getKey());
                list.add(result);
            }
            storeToMongoDB(entry.getKey(), list);
        }
    }

    private void storeToMongoDB(Status status, List<String> list) {
        MongoCollection collection = _mongoDB.getTweet_collection();
        if (status.getLang().equals("en") && (status.getGeoLocation() != null || status.getPlace() != null || !list.isEmpty())) {
            TweetSerialize serializer = new TweetSerialize();
            Document doc = serializer.parseTweet(status);
            doc.append("geo_tag", list.toString());
            collection.insertOne(doc);
            long count = collection.count();
            System.out.print(count);
        }
    }
}
