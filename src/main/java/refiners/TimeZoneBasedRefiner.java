package refiners;

import org.geonames.Timezone;
import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import store.GeonamesGazetter;
import twitter4j.Status;

/**
 * Created by Michael on 12/14/15.
 */
public class TimeZoneBasedRefiner extends BasicRefiner implements RefinerInterface{
    public Toponym refineGeoLocation(ToponymSearchResult locations, Status status) {
        // Special case;
        if (locations == null) {
            return null;
        }

        // Compared estimate locations with user timezone;
        if (status.getUser().getTimeZone() != null) {
            GeonamesGazetter gazetter = new GeonamesGazetter();
            try {
                ToponymSearchResult result = gazetter.searchTimeZone(status.getUser().getTimeZone());
                if (!result.getToponyms().isEmpty()) {
                    Toponym tmpTopo = result.getToponyms().get(0);
                    if (tmpTopo.getTimezone() != null && tmpTopo.getTimezone().getTimezoneId() != null) {
                        Timezone timezone = tmpTopo.getTimezone();
                        for (Toponym topo : locations.getToponyms()) {
                            Timezone topoTimezone = topo.getTimezone();
                            if (topoTimezone != null && topoTimezone.getTimezoneId() != null && topoTimezone.getTimezoneId().equals(timezone.getTimezoneId())) {
                                return topo;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }

        // If cannot match, then user the top1 which is the most relervant.
        return locations.getToponyms().get(0);
    }
}
