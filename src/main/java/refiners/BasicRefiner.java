package refiners;

import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import twitter4j.Status;

import java.lang.annotation.Inherited;

/**
 * Created by Michael on 12/14/15.
 */
public class BasicRefiner implements RefinerInterface {
    public Toponym refineGeoLocation(ToponymSearchResult locations, Status status) {
        assert(false);
        System.out.print("Please overide it");
        return null;
    }
}
