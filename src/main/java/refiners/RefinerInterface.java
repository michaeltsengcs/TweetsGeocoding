package refiners;

import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import twitter4j.Status;

/**
 * Created by Michael on 12/14/15.
 */
public interface RefinerInterface {
    Toponym refineGeoLocation(ToponymSearchResult locations, Status status);
}