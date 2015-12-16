package store;

import org.geonames.ToponymSearchCriteria;
import org.geonames.ToponymSearchResult;
import org.geonames.WebService;

/**
 * Created by Michael on 12/14/15.
 */
public class GeonamesGazetter {
    private static String userName = "yang4335";

    private WebService webService;

    public GeonamesGazetter() {
        webService = new WebService();
        webService.setUserName(userName);
    }

    public ToponymSearchResult searchByToponymCriteria(ToponymSearchCriteria topo) throws Exception{
        return webService.search(topo);
    }

    public ToponymSearchResult searchTimeZone(String location) throws Exception{
        ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
        searchCriteria.setNameEquals(location);
        searchCriteria.setMaxRows(1);
        return webService.search(searchCriteria);
    }
}
