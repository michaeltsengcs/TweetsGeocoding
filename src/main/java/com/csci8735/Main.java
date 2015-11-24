package com.csci8735;

import twitter4j.*;
import store.TweetsNER;


import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws TwitterException {
        //	 write your code here
        TweetsNER tweetLocations = new TweetsNER();
        HashMap<String, List<String>> locations = tweetLocations.getTweetsNER();
        for (HashMap.Entry<String, List<String>> entry : locations.entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
            for (String str : entry.getValue()) {
                System.out.print(str + "\t");
            }
            System.out.println("");
        }
    }
}
