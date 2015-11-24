package stream;

import twitter4j.*;
import java.util.*;
/**
 * Created by Michael on 11/22/15.
 */

public class Stream {

    private final Object lock = new Object();

    private static final int TOTAL_TWEETS = 100;

    public List<Status> execute() throws TwitterException {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        final List<Status> statuses = new ArrayList();

        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                String lang = status.getLang();

                if (lang.equals("en")) {
                    statuses.add(status);

                    if (statuses.size() >= TOTAL_TWEETS) {
                        synchronized (lock) {
                            lock.notify();
                        }
                        System.out.println("unlocked");
                    }
                }

            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
//                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }


            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
//                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }


            public void onScrubGeo(long userId, long upToStatusId) {
//                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }


            public void onStallWarning(StallWarning warning) {
//                System.out.println("Got stall warning:" + warning);
            }


            public void onException(Exception ex) {
//                ex.printStackTrace();
            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();

        try {
            synchronized (lock) {
            lock.wait();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        twitterStream.shutdown();
        return statuses;
    }
}
