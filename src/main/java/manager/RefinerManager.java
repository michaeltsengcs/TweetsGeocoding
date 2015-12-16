package manager;

import org.geonames.Toponym;
import org.geonames.ToponymSearchResult;
import refiners.BasicRefiner;
import refiners.RefinerInterface;
import refiners.TimeZoneBasedRefiner;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Michael on 12/14/15.
 */

public class RefinerManager {

    private  List<BasicRefiner> _refinerContainer = new ArrayList<>();

    private  HashMap<RefineStrategy, Double> _dict = new HashMap<>();

    private void setup() {
        _dict.put(RefineStrategy.TIMEZONEBASED, (double)1);
    }

    public RefinerManager(List<RefineStrategy> strategyList) {
        setup();
        for (RefineStrategy strategy : strategyList) {
            switch (strategy) {
                case TIMEZONEBASED:
                    _refinerContainer.add(new TimeZoneBasedRefiner());
                    break;

                case NETWORKBASED:
                    // TODO: Social network based refiner
                    break;

                case HISTORYBASED:
                    // TODO: History based refiner;
                    break;
            }
        }
    }

    public String refineLocation(ToponymSearchResult locations, Status status) {
        assert (locations != null || !locations.getToponyms().isEmpty());

        // If no strategy set return the first one which is the most relevant.
        if (_refinerContainer.isEmpty()) {
            Toponym topTopo = locations.getToponyms().get(0);
            String result = topTopo.getName() + "," + topTopo.getCountryName() + "," +
                    topTopo.getLatitude() + "," + topTopo.getLongitude();
            return result;
        }

        HashMap<Toponym, Double> hash = new HashMap<>();
        for (BasicRefiner refiner : _refinerContainer) {
            Toponym topo = refiner.refineGeoLocation(locations, status);
            if (topo != null) {
                if (hash.containsKey(topo)) {
                    hash.put(topo, hash.get(topo) + _dict.get(RefineStrategy.TIMEZONEBASED));
                } else {
                    hash.put(topo, _dict.get(RefineStrategy.TIMEZONEBASED));
                }
            }
        }

        HashMap.Entry<Toponym, Double> maxEntry = null;
        for (HashMap.Entry<Toponym, Double> entry : hash.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        Toponym topTopo = maxEntry.getKey();
        String result = topTopo.getName() + "," + topTopo.getCountryName() + "," +
                topTopo.getLatitude() + "," + topTopo.getLongitude();
        return result;
    }
}
