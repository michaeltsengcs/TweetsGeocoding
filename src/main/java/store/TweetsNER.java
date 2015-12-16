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

    static Stream _tweetStream;
    static UwNamedEntityTagger _tokenizer;

    public TweetsNER() {
        _tweetStream = new Stream();
        _tokenizer = new UwNamedEntityTagger.Builder().setKeepPunctuation(true).build();
    }

    /**
     * @return HashMap: key is tweet text, value is all name entity including person, location, company...
     * @throws TwitterException
     */
    public HashMap<Status, List<String>> getTweetsNER() throws TwitterException {
        List<Status> tweetStatus = _tweetStream.execute();
        HashMap<Status, List<String>> result = new HashMap<>();
        for (int i = 0; i < tweetStatus.size(); ++i) {
            Status status = tweetStatus.get(i);
            List<String> geoNames = getSingleTweetNER(status);
            result.put(status, geoNames);
        }

        return result;
    }


    public List<String> getSingleTweetNER(Status status) throws TwitterException {
        assert(status.getText() != null);

        // Eliminate emoji and \t \n \r which will terminate python progam.
        String tweet = status.getText();
        tweet = tweet.replaceAll("[^\\x00-\\x7f-\\x80-\\xad]", "");
        tweet = tweet.replaceAll("[\\t\\n\\r]"," ");

        List<NamedEntityTypeAttribute> tokSeq = _tokenizer.tokenize(tweet);
        List<String> geoNames = new ArrayList<String>();
        boolean isBegin = false;
        String oneName = "";
        for (NamedEntityTypeAttribute tok : tokSeq) {
            if ((tok.getType().name.contains("B-") || tok.getType().name.contains("I-")) && tok.getType().name.contains("geo")) {
                oneName += tok.getToken() + " ";
                isBegin = true;
            } else {
                if (isBegin) {
                    String tmpStr = oneName.substring(0, oneName.length() - 1);
                    oneName = "";
                    geoNames.add(tmpStr);
                    isBegin = false;
                }
            }
        }
        return geoNames;
    }
}
