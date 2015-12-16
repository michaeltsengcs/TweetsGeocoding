package com.csci8735;

import manager.RefineStrategy;
import twitter4j.*;
import store.TweetsNER;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import manager.GeoNameManager;

public class Main {

    public static void main(String[] args) throws TwitterException {
        //	 write your code here
        TweetsNER tweetLocations = new TweetsNER();
        List<RefineStrategy> strategiesList = new ArrayList<>();
        strategiesList.add(RefineStrategy.TIMEZONEBASED);
        GeoNameManager geoNameManager = new GeoNameManager(strategiesList);
        while (true) {
            HashMap<Status, List<String>> locations = tweetLocations.getTweetsNER();
            try {
                geoNameManager.GeoTag(locations);
            } catch (Exception e) {
                System.out.print(e.toString());
            }
        }
    }
}
