package store;
import twitter4j.*;

import stream.Stream;
import twitter4j.Status;
import twitter_nlp_wrapper.NamedEntityTypeAttribute;
import twitter_nlp_wrapper.UwNamedEntityTagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael on 11/23/15.
 */

public class TweetsNER {

    Stream _tweetStream;

    public TweetsNER() {
        _tweetStream = new Stream();
    }

    /**
     * @return HashMap: key is tweet text, value is all name entity including person, location, company...
     * @throws TwitterException
     */
    public HashMap<String, List<String>> getTweetsNER() throws TwitterException {
        List<Status> tweetStatus = _tweetStream.execute();

        // Generate raw twitter text
        List<String> famousTweets = new ArrayList<String>();
        for (Status aStatus : tweetStatus) {
            // Eliminate emoji and \t \n \r which will terminate python progam.
            String str = new String(aStatus.getText());
            str = str.replaceAll("[^\\x00-\\x7f-\\x80-\\xad]", "");
            str = str.replaceAll("[\\t\\n\\r]"," ");
            famousTweets.add(str);
        }

        // Create a token stream.
        UwNamedEntityTagger tokenizer = new UwNamedEntityTagger.Builder().setKeepPunctuation(true).build();

        // Return all hash with key: tweet text, value: all kind of entity.
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();
        for (String tweet : famousTweets) {
            List<NamedEntityTypeAttribute> tokSeq = tokenizer.tokenize(tweet);
            List<String> geoNames = new ArrayList<String>();
            boolean isBegin = false;
            String oneName = new String();
            for (NamedEntityTypeAttribute tok : tokSeq) {
                if (tok.getType().name.contains("B-") || tok.getType().name.contains("I-")) {
                    oneName += tok.getToken() + " ";
                    isBegin = true;
                } else {
                    if (isBegin) {
                        geoNames.add(oneName);
                        isBegin = false;
                    }
                }
            }

            if (!geoNames.isEmpty()) {
                result.put(tweet, geoNames);
            }
        }
        return result;
    }
}
